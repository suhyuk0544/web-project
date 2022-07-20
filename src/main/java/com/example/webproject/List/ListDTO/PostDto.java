package com.example.webproject.List.ListDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class PostDto implements Serializable {

    private int id;

    private String title;

    private String content;

    @CreationTimestamp
    private Date CreateTime;

    @Override
    public String toString() {
        return "PostDto{" +
                "title='" + title + '\'' +
                ", content='" + content + '\''+
                '}';
    }
}