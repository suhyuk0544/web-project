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
@Getter
@AllArgsConstructor
@Entity
public class UserInfo {

    @Id
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column
    @CreationTimestamp
    @JsonIgnore
    private Date JoinDate;


    @Column(nullable = false)
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Auth auth;

    @OneToMany(mappedBy = "userInfo")
    @JsonIgnore
    @Nullable
    private List<Post> posts = new ArrayList<>();

    private String email;

    private String provider;

    private String providerId;

    @Builder(builderClassName = "UserDetailRegister", builderMethodName = "userDetailRegister")
    public UserInfo(String name, String password, Date JoinDate, Auth auth) {

        this.name = name;

        this.password = password;

        this.JoinDate = JoinDate;

        this.auth = auth;
    }

    @Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")
    public UserInfo(String name, String password, String email,Auth auth) {

        this.name = name;

        this.password = password;

        this.email = email;

        this.auth = auth;
    }

    public UserInfo update(String name){
        this.name = name;

        return this;
    }

    public String getRoleKey(){
        return this.auth.getKey();
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", JoinDate=" + JoinDate +
                ", auth='" + auth + '\'' +
                ", posts=" + posts +
                '}';
    }

}



