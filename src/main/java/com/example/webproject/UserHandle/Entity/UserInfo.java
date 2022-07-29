package com.example.webproject.UserHandle.Entity;
import com.example.webproject.List.Entity.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.core.lang.Nullable;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table
public class UserInfo implements UserDetails {

    @Id
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column
    @CreationTimestamp
    @JsonIgnore
    private Date joindate;


    @Column
    @JsonIgnore
    private String auth;

    @OneToMany(mappedBy = "userInfo")
    @JsonIgnore
    @Nullable
    private List<Post> posts = new ArrayList<>();

    @Builder
    public UserInfo(String name, String password,Date joindate ,String auth) {

        this.name = name;

        this.password = password;

        this.joindate = joindate;

        this.auth = auth;

    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", joindate=" + joindate +
                ", auth='" + auth + '\'' +
                ", posts=" + posts +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<GrantedAuthority> roles = new HashSet<>();

        for (String role : auth.split(",")) {
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



