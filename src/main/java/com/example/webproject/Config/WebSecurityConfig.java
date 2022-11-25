package com.example.webproject.Config;

import com.example.webproject.UserHandle.UserDaoService.CustomOAuth2UserService;
import com.example.webproject.UserHandle.UserDaoService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    private final CustomOAuth2UserService Oauth2UserService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**","/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/login", "/signup", "/user","/oauth2/**","https://nid.naver.com/**").permitAll()
                .antMatchers("/main/**","/savePost","/formpost").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
//                .antMatchers("/**").hasRole("ADMIN")
                .anyRequest()
                    .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .usernameParameter("name")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/main")
                    .failureForwardUrl("/login")
                .and()
                .csrf().ignoringAntMatchers("/main/**","/delete/**")
                .and()
                .logout()
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/main")
                .userInfoEndpoint()
                    .userService(Oauth2UserService)

        ;
    }

//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties){
//
//        List<ClientRegistration> registrations = new ArrayList<>();
//
////        registrations.add(getRegistration("NQj0cmSwEsYbB8ajFwfe","ZKobACSyRi","naver"));
//
//
//        return new InMemoryClientRegistrationRepository(registrations);
//    }

//    private ClientRegistration getRegistration(String clientId,String clientSecret,String client) {
//
//
//        if (clientId != null) {
//            switch (client) {
//                case "naver":
//                    return CustomOAuth2Provider.NAVER.getBuilder(client)
//                            .clientId(clientId)
//                            .clientSecret(clientSecret)
//                            .build();
//                case "kakao":
//                    return CustomOAuth2Provider.KAKAO.getBuilder(client)
//                            .clientId(clientId)
//                            .clientSecret(clientSecret)
//                            .build();
//            }
//        }
//        return null;
//    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userService)
                .passwordEncoder(new BCryptPasswordEncoder());

    }
}
