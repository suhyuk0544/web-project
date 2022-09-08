package com.example.webproject.UserHandle.UserDaoService;


import com.example.webproject.UserHandle.DTO.Session;
import com.example.webproject.UserHandle.Entity.Auth;
import com.example.webproject.UserHandle.Entity.PrincipalDetails;
import com.example.webproject.UserHandle.Entity.UserInfo;
import com.example.webproject.UserHandle.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    private final HttpSession httpSession;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {


        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(oAuth2UserRequest);

        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();

        String userAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userAttributeName, oAuth2User.getAttributes());

        UserInfo user = userRepository.findByname(attributes.getEmail());

        if (user == null){
            user = save(attributes);

            log.info("save = {}",user);

        }

        log.info("login = {}",user);

        httpSession.setAttribute("loginUser",user.getName());

        return new PrincipalDetails(user,oAuth2User);
    }

    private UserInfo save(OAuthAttributes attributes){


        return userRepository.save(UserInfo.oauth2Register()
                    .name(attributes.getEmail())
                    .auth(Auth.ROLE_USER)
                    .provider(attributes.getRegistrationId())
                    .build());
    }

}
