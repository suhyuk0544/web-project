package com.example.webproject.List.Entity;


import com.example.webproject.UserHandle.Entity.UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String title;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private UserInfo userInfo;

    @Builder
    public Post(int id, String title,String content){

        this.id = id;

        this.title = title;

        this.content = content;
    }

}
