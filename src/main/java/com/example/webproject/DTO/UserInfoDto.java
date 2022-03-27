package com.example.webproject.DTO;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;
@Data
public class UserInfoDto implements Serializable {

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
