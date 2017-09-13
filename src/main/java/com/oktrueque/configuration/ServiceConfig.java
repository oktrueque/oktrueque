package com.oktrueque.configuration;

import com.oktrueque.repository.*;
import com.oktrueque.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EntityScan(basePackageClasses = {Jsr310JpaConverters.class}, basePackages = "com.oktrueque.model")
public class ServiceConfig {

    @Autowired
    private ComplaintTypeRepository complaintTypeRepository;
    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private TruequeRepository truequeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTruequeRepository userTruequeRepository;
    @Autowired
    private ItemTruequeRepository itemTruequeRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ItemTagRepository itemTagRepository;
    @Autowired
    private UserTagRepository userTagRepository;


    @Bean
    public ComplaintTypeService complaintTypeService(){return new ComplaintTypeServiceImpl(complaintTypeRepository);}

    @Bean
    public ComplaintService complaintService(){
        return new ComplaintServiceImpl(complaintRepository,this.emailService());
    }


    @Bean
    public CategoryService categoryService(){
        return new CategoryServiceImpl(categoryRepository);
    }

    @Bean
    public CommentService commentService(){
        return new CommentServiceImpl(commentRepository);
    }

    @Bean
    public EmailService emailService(){
        return new EmailServiceImpl(javaMailSender);
    }

    @Bean
    public ItemService itemService(){
        return new ItemServiceImpl(itemRepository, itemTagRepository, tagRepository);
    }

    @Bean
    public TruequeService truequeService(){
        return new TruequeServiceImpl(truequeRepository,itemTruequeRepository,userTruequeRepository,this.emailService(),userRepository);
    }

    @Bean
    public UserService userService(){
        return new UserServiceImpl(userRepository,verificationTokenRepository
                ,bCryptPasswordEncoder,this.emailService());
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public TagService tagService(){
        return new TagServiceImpl(tagRepository);
    }

    @Bean
    public ItemTagService itemTagService(){
        return new ItemTagServiceImpl(itemTagRepository);
    }

    @Bean
    public UserTagService userTagService(){
        return new UserTagServiceImpl(userTagRepository,this.tagService());
    }

}
