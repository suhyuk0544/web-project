package com.example.webproject.UserHandle.UserDaoService;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private final Map<String, Object> attributes;
    private final String name;
    private final String email;
    private final String registrationId;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String name, String email,String registrationId) {

        this.attributes = attributes;

        this.registrationId = registrationId;

        this.name = name;

        this.email = email;
    }

    public static OAuthAttributes of(String registrationId,Map<String, Object> attributes) throws OAuth2AuthenticationException{

        return ofNaver(attributes,registrationId);

    }

    private static OAuthAttributes ofNaver(Map<String, Object> attributes,String registrationId) {

        Map<String, Object> response = (Map<String, Object>)attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .attributes(response)
                .registrationId(registrationId)
                .build();
    }
}