package com.example.webproject.UserHandle.DTO;


import com.example.webproject.UserHandle.Entity.Auth;
import com.example.webproject.UserHandle.Entity.UserInfo;
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
    private Date JoinDate;

    @Override
    public String toString() {

        return "UserInfoDto{" +
                "name='" + name + '\'' +
                '}';
    }

}
