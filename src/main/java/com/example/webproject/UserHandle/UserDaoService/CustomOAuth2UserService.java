package com.example.webproject.UserHandle.UserDaoService;


import com.example.webproject.UserHandle.Entity.Auth;
import com.example.webproject.UserHandle.Entity.PrincipalDetails;
import com.example.webproject.UserHandle.Entity.UserInfo;
import com.example.webproject.UserHandle.UserRepository;
import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest,OAuth2User> {

    private final UserRepository userRepository;

    private final UserService userService;

    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {

        Assert.notNull(oAuth2UserRequest, "oAuthUserRequest cannot be null");

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(oAuth2UserRequest);

        String userAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(userAttributeName, oAuth2User.getAttributes());

        UserInfo user = userRepository.findByname(attributes.getEmail());

        if (user == null){

            user = save(attributes);

            log.info("save = {}",user);
        }

        log.info("login = {}",user);

        httpSession.setAttribute("loginUser",user);

        return new PrincipalDetails(user,oAuth2User);
    }
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
//
//        JSONObject response;
//
//        switch (oAuth2UserRequest.getClientRegistration().getRegistrationId()){
//            case "naver" :
//                response = getNaverUserInfo(oAuth2UserRequest.getAccessToken().getTokenValue());
//                break;
////            case "kakao" :
////                response = getKakaoUserInfo(oAuth2UserRequest.getAccessToken().getTokenValue());
////                break;
//
//            default:
//                throw new IllegalStateException("Unexpected value: " + oAuth2UserRequest.getClientRegistration().getRegistrationId());
//        }
//
//        Assert.notNull(response,"response can not be Null");
//
//        OAuthAttributes attributes = OAuthAttributes.of("Naver",response);
//
//        UserInfo user = userRepository.findByname(attributes.getEmail());
//
//        if (user == null){
//
//            user = save(attributes);
//
//            log.info("Save Oauth User = {}",user);
//
//        }
//
//        log.info("Login Oauth User = {}",user);
//
//        userService.loadUserByUsername(user.getName());
//
//        httpSession.setAttribute("loginUser",user.getName());
//
//        return new PrincipalDetails(user);
//    }

    private JSONObject getKakaoUserInfo(String tokenValue) {
        return new JSONObject();
    }

    public JSONObject getNaverUserInfo(String AccessToken){

        WebClient webclient = WebClient.builder()
                .baseUrl("https://openapi.naver.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
                .build();

        return webclient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/nid/me")
                        .build())
                .header("Authorization","Bearer " + AccessToken)
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();

    }

    private UserInfo save(OAuthAttributes attributes){


        return userRepository.save(UserInfo.oauth2Register()
                .name(attributes.getEmail())
                .auth(Auth.ROLE_USER)
                .provider(attributes.getRegistrationId())
                .build());
    }
}
