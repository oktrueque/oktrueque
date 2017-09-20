package com.oktrueque.configuration;

import com.oktrueque.repository.UserRepository;
import com.oktrueque.service.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);
    private static final String MESSAGE ="Problemas con la configuracion de seguridad";

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)  {
        try {
            auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(bCryptPasswordEncoder());
        } catch (Exception e) {
            LOGGER.info(MESSAGE, e);
        }

    }

    @Override
    public UserDetailsService userDetailsServiceBean() {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) {
        try {
            http
                    .authorizeRequests()
                    .antMatchers("/css/**", "/index","/trueques/*/confirm").permitAll()
                    .antMatchers("/profile/**", "/trueques/**").authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login").defaultSuccessUrl("/items").failureUrl("/login-error");
        } catch (Exception e) {
            LOGGER.info(MESSAGE, e);
        }
    }

}
