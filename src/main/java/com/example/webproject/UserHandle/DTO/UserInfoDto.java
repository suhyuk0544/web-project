package com.example.webproject.UserHandle.DTO;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;
@Getter
@Setter
public class UserInfoDto implements Serializable {

    private String name;

    private String password;

    @CreationTimestamp
    private Date joindate;

    private String auth;

    public String getAuth(){

        auth = "ROLE_USER";

        return auth;
    }

    @Override
    public String toString() {

        return "UserInfoDto{" +
                "name='" + name + '\'' +
                ", auth='" + auth + '\'' +
                '}';
    }
}
