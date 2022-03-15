package com.example.webproject.Entity;

import com.example.webproject.Entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserInfo implements UserDetails {

    @Id
    @Column(name = "name", unique = true)
    private String name;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(name = "joindate")
    @CreationTimestamp
    private Date joindate;


    @Column(name = "auth")
    private String auth;

    @OneToMany(mappedBy = "userInfo")
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    @Builder
    public UserInfo(String name, String password,Date joindate ,String auth) {
        this.name = name;
        this.password = password;
        this.joindate = joindate;
        this.auth = auth;

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split("ROLE_USER,ROLE_ADMIN")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
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



}



