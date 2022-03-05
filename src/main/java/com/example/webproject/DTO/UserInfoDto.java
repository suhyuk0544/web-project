package com.example.webproject.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserInfoDto {

    private String name;

    private String password;

    private Date joindate;

    private String auth;

    public Date getjoindate(){
        joindate = new Date();

        return joindate;

    }

    public String getAuth(){
        auth = "ROLE_USER";

        return auth;
    }

}
