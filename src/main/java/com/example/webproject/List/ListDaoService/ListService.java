package com.example.webproject.List.ListDaoService;

import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.ListDTO.PostDto;
import com.example.webproject.List.ListRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ListService{

    private ListRepository listRepository;

    public Post LoadAllByTitle(String title) throws NotFoundException{

        return listRepository.findAllByTitle(title)
                .orElseThrow(() -> new NotFoundException((title)));

    }

    public Post LoadOneByTitle(String title) throws NotFoundException{

        return listRepository.findByTitle(title)
                .orElseThrow(() -> new NotFoundException((title)));

    }

    public Post save(PostDto postDto) {

        log.info("title {}", postDto.getTitle());

        return listRepository.save(Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .createPost(postDto.getCreatePost()).build());

    }

    public List<Post> postPage(String title){

        List<Post> postPage = listRepository.findByTitleContaining(title);



        return postPage;

    }



}