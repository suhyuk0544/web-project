package com.example.webproject.List.ListDTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostDto implements Serializable {

    private final Integer id;

    private final String title;

    private final String content;

}
