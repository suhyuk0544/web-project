package com.example.webproject.UserHandle.Entity;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
@ToString
public class PrincipalDetails implements UserDetails, OAuth2User {

    private final UserInfo user;
    private Map<String, Object> attributes;

    private OAuth2User oAuth2User;

    public PrincipalDetails(UserInfo user) {
        this.user = user;
    }

    public PrincipalDetails(UserInfo user,OAuth2User oAuth2User) {
        this.user = user;
        this.oAuth2User = oAuth2User;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collect = new ArrayList<>();

        collect.add((GrantedAuthority) () -> user.getAuth().toString());

        return collect;
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }


    @Override
    public boolean isEnabled() {
        return true;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }



    @Override
    public boolean isAccountNonLocked() {
        return true;
    }



    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }


    @Override
    public String getName() {
        return attributes.get("sub").toString();
    }

}