package com.example.webproject.List.ListDaoService;

import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.ListDTO.PostDto;
import com.example.webproject.List.ListRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ListService {


    private ListRepository listRepository;

    public Post LaodByTitle(String title) throws NotFoundException{

        return listRepository.findAllByTitle(title)
                .orElseThrow(() -> new NotFoundException((title)));

    }

    public Post save(PostDto postDto){


        return listRepository.save(Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent()).build());
    }



}
