package com.example.webproject.List.ListDaoService;

import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.ListDTO.PostDto;
import com.example.webproject.List.ListRepository;
import com.example.webproject.UserHandle.Entity.PrincipalDetails;
import com.example.webproject.UserHandle.Entity.UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
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

    public void save(PostDto postDto,PrincipalDetails principalDetails) throws NullPointerException {


        if (postDto.getTitle().isEmpty() || postDto.getContent().isEmpty()){

            throw new NullPointerException();

        }else {
            UserInfo user = principalDetails.getUser();
            if (user != null) {
                listRepository.save(Post.userRegister()
                        .title(postDto.getTitle())
                        .content(postDto.getContent())
                        .createTime(postDto.getCreateTime())
                        .userInfo(user).build());
            }
        }

    }

    public List<Post> postPage(String title,Pageable pageable){

        return listRepository.findByTitleContainingOrderById(title,pageable);

    }

    public void delete(int id){

        listRepository.deleteById(id);

    }

}
