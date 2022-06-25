package com.example.webproject.List.ListDaoService;

import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.ListDTO.PostDto;
import com.example.webproject.List.ListRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ListService{

    private final ListRepository listRepository;

    public ListService(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    public Post FindById(int id) throws NotFoundException{

        return listRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    public Post LoadOneByTitle(String title) throws NotFoundException{

        return listRepository.findByTitle(title)
                .orElseThrow(() -> new NotFoundException((title)));

    }

    public void save(PostDto postDto) throws NullPointerException {

        if (postDto.getTitle().isEmpty() || postDto.getContent().isEmpty()){

            throw new NullPointerException();

        }

        listRepository.save(Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .createTime(postDto.getCreateTime()).build());
    }

    public List<Post> postPage(PostDto postDto,Pageable pageable){

        return listRepository.findByTitleContainingOrderById(postDto.getTitle(),pageable);

    }

    public void delete(PostDto postDto){

        listRepository.deleteByTitle(postDto.getTitle());

    }

}
