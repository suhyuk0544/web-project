package com.example.webproject.List.ListController;

import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.ListDTO.PostDto;
import com.example.webproject.List.ListDaoService.ListService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ListController {

    private final ListService listService;

    private static final List<Post> posts = new ArrayList<>();

    @PostMapping("/main/savePost")
    public String Postsave(PostDto postDto){

        listService.save(postDto);

        log.info("Title = {} CreateTime = {}", postDto.getTitle(),postDto.getCreateTime());

        return "redirect:/main";

    }

    @GetMapping("/main/search")
    public String search(String title, Model model){

        List<Post> postList = listService.postPage(title);

        model.addAttribute("postList",postList);

        return "form/index";

    }

    @GetMapping("/find")
    public Post view(PostDto postDto){

        try {
            Post post = listService.LoadOneByTitle(postDto.getTitle());

            return post;

        } catch (NotFoundException e) {

            e.printStackTrace();
        }

        return null;
    }
    @PostMapping("/findall")
    public void viewall(PostDto postDto) {

//        listService.LoadAllByTitle(postDto.getTitle());



    }

    @GetMapping("main/formpost")
    public String postform() {

        return "form/post";

    }


    @GetMapping("/form/login")
    public String um(){

        return "redirect:/main";
    }



}
