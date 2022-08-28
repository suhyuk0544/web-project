package com.example.webproject.UserHandle.DTO;

import com.example.webproject.UserHandle.Entity.Auth;
import com.example.webproject.UserHandle.Entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class Session implements Serializable {

    private String name;

    private Auth auth;

    private String provider;

    public Session(UserInfo userInfo) {

        this.name = userInfo.getName();

        this.auth = userInfo.getAuth();

        this.provider = userInfo.getProvider();
    }
}
