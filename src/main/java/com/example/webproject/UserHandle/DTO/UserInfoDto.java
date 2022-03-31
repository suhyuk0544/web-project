package com.example.webproject.UserHandle.DTO;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;
@Data
public class UserInfoDto implements Serializable {

    private String name;

    private String password;

    private Date joindate;

    private String auth;

    public String getAuth(){

        auth = "ROLE_USER";

        return auth;
    }

}
