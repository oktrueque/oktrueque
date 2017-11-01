package com.oktrueque.controller;

import com.oktrueque.model.*;
import com.oktrueque.service.*;
import com.oktrueque.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.*;


@Controller
public class ProfileController {

    private UserService userService;
    private CommentService commentService;
    private UserTagService userTagService;
    private ItemService itemService;
    private ItemTagService itemTagService;
    private CategoryService categoryService;
    private TruequeService truequeService;
    private AwsS3Service awsS3Service;
    @Value("${aws.s3.fileName.users}")
    private String fileNameUsers;
    @Value("${aws.s3.fileName.items}")
    private String fileNameItems;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ComplaintTypeService complaintTypeService;

    @Autowired
    public ProfileController(UserService userService, CommentService commentService, UserTagService userTagService,
                             ItemService itemService, ItemTagService itemTagService, CategoryService categoryService,
                             TruequeService truequeService, AwsS3Service awsS3Service, BCryptPasswordEncoder bCryptPasswordEncoder, ComplaintService complaintService, ComplaintTypeService complaintTypeService) {
        this.userService = userService;
        this.commentService = commentService;
        this.userTagService = userTagService;
        this.itemService = itemService;
        this.itemTagService = itemTagService;
        this.categoryService = categoryService;
        this.truequeService = truequeService;
        this.awsS3Service = awsS3Service;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.complaintTypeService = complaintTypeService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile")
    public String getProfile(Principal principal, Model model) {
        User user = userService.getUserByUsername(principal.getName());
        Page<Item> items = itemService.findByUser_UsernameAndStatusIsNotInOrderById(user.getUsername(), new int[]{2, 3}, new PageRequest(0,5));
        List<UserTag> tags = userTagService.getUserTagByUserId(user.getId());
        List<UserTrueque> userTrueques = truequeService.getUserTruequeById_UserId(user.getId());
        LinkedList<Trueque> trueques = new LinkedList<>();
        for (UserTrueque userTrueque : userTrueques) {
            trueques.add(userTrueque.getId().getTrueque());
        }
        Page<Comment> comments = commentService.getCommentsByUserTargetId(user.getId(), new PageRequest(0,5));
        List<ComplaintType> complaintTypes = complaintTypeService.getComplaintTypes();

        model.addAttribute("user", user);
        model.addAttribute("hasScore", user.getScore() != null ? true : false);
        model.addAttribute("hasItems", items.getContent().size() != 0 ? true : false);
        model.addAttribute("items", items.getContent());
        model.addAttribute("itemsCount", items.getTotalElements());
        model.addAttribute("hasTags", tags.size() != 0 ? true : false);
        model.addAttribute("tags", tags);
        model.addAttribute("item", new Item(0));
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("hasTrueques", trueques.size() != 0 ? true : false);
        model.addAttribute("trueques", trueques);
        model.addAttribute("comments", comments);
        model.addAttribute("hasComments", comments.getTotalElements() != 0 ? true : false);
        model.addAttribute("complaintTypes", complaintTypes);

        return "profile";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/edit")
    public String editProfile(Principal principal, Model model) {
        User user = userService.getUserByUsername(principal.getName());
        List<Tag> tags = userTagService.getTagByUserTags(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("hasTags", tags.size() != 0 ? true : false);
        model.addAttribute("tags", tags);
        return "updateProfile";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/profile/edit")
    public String updateProfile(@ModelAttribute User user, @ModelAttribute MultipartFile picture) {
        if (!picture.getOriginalFilename().equals("")) {
            String pictureUrl = awsS3Service.uploadFileToS3(picture, fileNameUsers, user.getId(), null, user.getPhoto1());
            user.setPhoto1(pictureUrl);
        }
        if (user.getNewPassword().equals("")) {
            userService.updateUser(user);
            return "redirect:/profile";
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getNewPassword());
        user.setNewPassword("");
        user.setPassword(encodedPassword);
        userService.updateUser(user);
        return "redirect:/profile";
    }



    @RequestMapping(method = RequestMethod.GET, value = "/profile/items/{id}")
    public String getItemById(@PathVariable Long id, Model model) {
        Item item = itemService.getItemById(id);
        List<ItemTag> tags = itemTagService.getItemTagByItemId(id);
        model.addAttribute("item", item);
        model.addAttribute("user", item.getUser());
        model.addAttribute("hasTags", tags.size() != 0 ? true : false);
        model.addAttribute("tags", tags);
        model.addAttribute("sugerencias", false);
        return "item";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/profile/items")
    public ResponseEntity<Item> newItem(@RequestBody Item item, Principal principal) {
        UserLite user = userService.getUserLiteByUsername(principal.getName());
        item.setStatus(0);
        item.setCreationDate(new Date());
        item.setUser(user);
        item.setPhoto1(Constants.IMG_PICTURE_DEFAULT);
        Item itemResponse = itemService.saveItem(item);
        return new ResponseEntity(itemResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/profile/items/images")
    public String setItemImages(@ModelAttribute Item item) {
        Item itemDB = itemService.getItemById(item.getId());
        int index = 1;
        if (!item.getPictures()[0].getOriginalFilename().equals("")) {
            String pictureUrl = awsS3Service.uploadFileToS3(item.getPictures()[0], fileNameItems, item.getId(), "1", item.getPhoto1());
            itemDB.setPhoto(index, pictureUrl);
            index++;
            itemDB.setStatus(0);
        }
        if (!item.getPictures()[1].getOriginalFilename().equals("")) {
            String pictureUrl = awsS3Service.uploadFileToS3(item.getPictures()[1], fileNameItems, item.getId(), "2", item.getPhoto1());
            itemDB.setPhoto(index, pictureUrl);
            index++;
            itemDB.setStatus(0);
        }
        if (!item.getPictures()[2].getOriginalFilename().equals("")) {
            String pictureUrl = awsS3Service.uploadFileToS3(item.getPictures()[2], fileNameItems, item.getId(), "3", item.getPhoto1());
            itemDB.setPhoto(index, pictureUrl);
            itemDB.setStatus(0);
        }
        itemService.updateItem(itemDB);
        return "redirect:/profile";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/items/{id}/edit")
    public String fillUpdateView(@PathVariable Long id, Model model) {
        Item item = itemService.getItemById(id);
        List<Tag> tags = itemTagService.getItemTags(id);
        List<Category> categories = categoryService.getCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("item", item);
        model.addAttribute("hasTags", tags.size() != 0 ? true : false);
        model.addAttribute("tags", tags);
        model.addAttribute("photo1", item.getPhoto1());
        model.addAttribute("photo2", item.getPhoto2());
        model.addAttribute("photo3", item.getPhoto3());
        return "updateItem";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/profile/items/{id}/edit")
    public String updateItemById(@ModelAttribute Item item, @ModelAttribute List<MultipartFile> pictures, Principal principal) {
        int index = 1;
        if (!pictures.get(0).getOriginalFilename().equals("")) {
            String pictureUrl = awsS3Service.uploadFileToS3(pictures.get(0), fileNameItems, item.getId(), "1", item.getPhoto1());
            item.setPhoto(index, pictureUrl);
            index++;
            item.setStatus(0);
        }
        if (!pictures.get(1).getOriginalFilename().equals("")) {
            String pictureUrl = awsS3Service.uploadFileToS3(pictures.get(1), fileNameItems, item.getId(), "2", item.getPhoto1());
            item.setPhoto(index, pictureUrl);
            index++;
            item.setStatus(0);
        }
        if (!pictures.get(2).getOriginalFilename().equals("")) {
            String pictureUrl = awsS3Service.uploadFileToS3(pictures.get(2), fileNameItems, item.getId(), "3", item.getPhoto1());
            item.setPhoto(index, pictureUrl);
            item.setStatus(0);
        }

        UserLite user = userService.getUserLiteByUsername(principal.getName());
        item.setUser(user);
        itemService.updateItem(item);
        return "redirect:/profile/items/" + item.getId();
    }


    @RequestMapping(method = RequestMethod.POST, value = "profile/trueques/{id}")
    public ResponseEntity<List<UserLite>> updateTrueque(@PathVariable Long id, Principal principal) {

        Trueque trueque = truequeService.getTruequeById(id);
        User user = userService.getUserByUsername(principal.getName());
        List<UserTrueque> userTrueques = truequeService.getUserTruequeById_TruequeId(id);
        List<UserLite> users = new ArrayList<>();
        for (UserTrueque ut : userTrueques) {
            if (ut.getId().getUser().getId() != user.getId()) {
                users.add(userService.getUserLiteById(ut.getId().getUser().getId()));
            }
        }
        // PENDIENTE A RECHAZADO
        if (trueque.getStatus().equals("Pendiente")) {
            trueque.setStatus(2);
            trueque.setRejectionDate(new Date());
            truequeService.updateTrueque(trueque);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        // ACTIVO A CANCELADO
        if (trueque.getStatus().equals("Activo")) {
            trueque.setStatus(4);
            trueque.setRejectionDate(new Date());
            truequeService.updateTrueque(trueque);
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST, value = "profile/trueques/{id}/confirm")
    public ResponseEntity<List<UserLite>> confirmTrueque(@PathVariable Long id, Principal principal) {
        List<UserLite> users = truequeService.confirmTrueque(id, principal.getName());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/profile/comment")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment, Principal principal) {
        comment.setDate(new Date());
        comment.setUserOrigin(userService.getUserLiteByUsername(principal.getName()));
        Comment commentResponse = commentService.saveComment(comment);
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/profile/items/{id}")
    public ResponseEntity<Item> deleteUserItem(@PathVariable Long id) {
        Item item = itemService.deleteIfPossible(id);
        return new ResponseEntity(item, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/trueques")
    public String getTruequesByUser(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        List<UserTrueque> userTrueques = truequeService.getUserTruequeById_UserId(user.getId());
        Trueque TruequeNuevo;
        LinkedList<Trueque> trueques = new LinkedList<>();
        for (UserTrueque trueque : userTrueques) {

            TruequeNuevo = truequeService.getTruequeById(trueque.getId().getTrueque().getId());
            trueques.add(TruequeNuevo);
        }
        model.addAttribute("trueques", trueques);
        return "loggedUserTrueques";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/profile/trueques/{id}")
    public String getTruequeById(Model model, @PathVariable Long id, Principal principal) {
        User userLogged = userService.getUserByUsername(principal.getName());
        Trueque trueque = truequeService.getTruequeById(id);
        List<UserTrueque> userTrueques = truequeService.getUserTruequeById_TruequeId(id);
        LinkedList<User> users = new LinkedList<>();
        User userNuevo;
        List<ItemTrueque> itemsTrueques = truequeService.getItemsTruequeById_TruequeId(id);
        LinkedList<Item> items = new LinkedList<>();
        Item itemNuevo;
        for (ItemTrueque itemTrueque : itemsTrueques) {
            itemNuevo = itemService.getItemById(itemTrueque.getId().getItem().getId());
            items.add(itemNuevo);
        }
        for (UserTrueque userTrueque : userTrueques) {
            if (userTrueque.getId().getUser().getId() != userLogged.getId()) {
                userNuevo = userService.getUserById(userTrueque.getId().getUser().getId());
                users.add(userNuevo);
            }
        }
        model.addAttribute("items", items);
        model.addAttribute("users", users);
        model.addAttribute("trueque", trueque);
        return "truequeDetail";
    }


    @RequestMapping(method = RequestMethod.GET, value = "profile/trueques/ask")
    public ResponseEntity<UserTrueque> askTrueques(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        List<UserTrueque> userTrueques = truequeService.getUserTruequeById_UserId(user.getId());
        List<UserTrueque> userTruequesToPass = new LinkedList<>();
        for (UserTrueque userTrueque : userTrueques) {
            if (truequeService.isTimeToAsk(userTrueque.getId().getTrueque())){
                userTruequesToPass.addAll(truequeService.getUserTruequeById_TruequeId(userTrueque
                        .getId().getTrueque().getId()));
            }
        }
        if (userTruequesToPass.size()==0){
            return new ResponseEntity(false, HttpStatus.OK);
        }
        return new ResponseEntity(userTruequesToPass, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "profile/delete-account")
    public String getDeleteAccountPage(Model model, Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "deleteAccount";
    }


}
