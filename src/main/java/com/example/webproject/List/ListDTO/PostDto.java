package com.example.webproject.List.ListDTO;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;

@Data
public class PostDto implements Serializable {

    private final String title;

    private final String content;

    @CreationTimestamp
    private final Date CreatePost;

}
