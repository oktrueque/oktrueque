package com.oktrueque.configuration;

import com.oktrueque.repository.*;
import com.oktrueque.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Facundo on 12/07/2017.
 */
@Configuration
public class ServiceConfig {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    TruequeRepository truequeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VerificationTokenRepository verificationTokenRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


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
        return new ItemServiceImpl(itemRepository);
    }

    @Bean
    public TruequeService truequeService(){
        return new TruequeServiceImpl(truequeRepository);
    }

    @Bean
    public UserService userService(){
        return new UserServiceImpl(userRepository,verificationTokenRepository,bCryptPasswordEncoder);
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return  new UserDetailsServiceImpl(userRepository);
    }

}
