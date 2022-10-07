package ru.itmo.kotikijava2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.itmo.kotikijava2.service.UserService;

@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/owner/**").hasRole("ADMIN")
                .antMatchers("/cat/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/hello/**").permitAll()
                .and()
                .formLogin()
        ;
    }

//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
//        authenticationProvider.setUserDetailsService(userService);
//        return authenticationProvider;
//    }

//    // In memory
////
////    @Bean
////    public UserDetailsService users() {
////        UserDetails user = User.builder()
////                .username("user")
////                .password("{bcrypt}$2a$12$ZUh2wzvPqzu/xxxQAVNVS.a8VxzbV8fysAJHtIO0IJ9coTGZByukm")
////                .roles("USER")
////                .build();
////        UserDetails admin = User.builder()
////                .username("admin")
////                .password("{bcrypt}$2a$12$ZUh2wzvPqzu/xxxQAVNVS.a8VxzbV8fysAJHtIO0IJ9coTGZByukm")
////                .roles("ADMIN")
////                .build();
////        return new InMemoryUserDetailsManager(user, admin);
////    }
}
