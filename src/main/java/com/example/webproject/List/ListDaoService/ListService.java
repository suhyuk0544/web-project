package com.example.webproject.List.ListDaoService;

import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.ListDTO.PostDto;
import com.example.webproject.List.ListRepository;
import com.example.webproject.UserHandle.Entity.UserInfo;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ListService {
    @Autowired
    private ListRepository listRepository;

    public Page<Post> postPage(Pageable pageable){

        return listRepository.findAll(pageable);

    }

    public Post setPost(PostDto postDto,int id) throws NotFoundException {

        Post post = FindById(id);

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        listRepository.save(post);

        return post;
    }

    public Post FindById(int id) throws NotFoundException {

        return listRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    public Post LoadOneByTitle(String title) throws NotFoundException{

        return listRepository.findByTitle(title)
                .orElseThrow(() -> new NotFoundException((title)));

    }

    public void save(PostDto postDto, UserInfo user) throws NullPointerException {


        if (postDto.getTitle().isEmpty() || postDto.getContent().isEmpty()){

            throw new NullPointerException();

        }else {
            listRepository.save(Post.builder()
                    .title(postDto.getTitle())
                    .content(postDto.getContent())
                    .createTime(postDto.getCreateTime())
                    .userInfo(user).build());
        }

    }

    public List<Post> postPage(PostDto postDto,Pageable pageable){

        return listRepository.findByTitleContainingOrderById(postDto.getTitle(),pageable);

    }

    public void delete(PostDto postDto){

        listRepository.deleteByTitle(postDto.getTitle());

    }

}
