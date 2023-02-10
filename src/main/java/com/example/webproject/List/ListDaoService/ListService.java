package com.example.webproject.List.ListDaoService;

import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.Entity.Question;
import com.example.webproject.List.ListDTO.PostDto;
import com.example.webproject.List.ListDTO.QuestionDto;
import com.example.webproject.List.ListRepository;
import com.example.webproject.List.QuestionRepository;
import com.example.webproject.UserHandle.Entity.PrincipalDetails;
import com.example.webproject.UserHandle.Entity.UserInfo;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class ListService {

    private final ListRepository listRepository;

    private final QuestionRepository questionRepository;

    public Page<Post> postPage(Pageable pageable){

        return listRepository.findAll(pageable);
    }

    public void SaveQuestion(int id, QuestionDto questionDto, UserInfo userInfo){
        try {
            Post post = FindById(id);

            questionRepository.save(Question.builder()
                    .username(userInfo.getName())
                    .questionContent(questionDto.getQuestionContent())
                    .date(questionDto.getDate())
                    .post(post)
                    .build());

        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void setPost(PostDto postDto,int id,UserInfo userInfo) throws NotFoundException {

        log.info(userInfo.toString());
        Post post = FindById(id);

        if (Objects.equals(post.getUserInfo().getName(), userInfo.getName())) {
            post.setTitle(postDto.getTitle());
            post.setContent(postDto.getContent());
            log.info(post.toString());
        }

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Post FindById(int id) throws NotFoundException{

        return listRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.valueOf(id)));
    }

    public Page<Question> questions(Post post,Pageable pageable) throws NotFoundException{

        return questionRepository.findQuestionsByPostOrderByDate(post,pageable);
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
                        .userInfo(user)
                        .build());
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
