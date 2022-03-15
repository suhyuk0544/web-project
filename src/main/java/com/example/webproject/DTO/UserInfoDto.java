package com.example.webproject.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
public class UserInfoDto {

    private String name;

    private String password;

    @CreationTimestamp
    private Date joindate;

    private String auth;


    public Date getjoindate(){
        return joindate;
    }


    public String getAuth(){
        auth = "ROLE_USER";

        return auth;
    }

}
