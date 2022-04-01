package com.example.webproject.List.ListController;

import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.ListDTO.PostDto;
import com.example.webproject.List.ListDaoService.ListService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ListController {

    private final ListService listService;

    @GetMapping("/main")
    public String search(PostDto postDto, Model model){

        List<Post> postList = listService.postPage(postDto.getTitle());

        model.addAttribute("postliat",postList);

        return "";

    }

    @GetMapping("main/formpost")
    public String postform() {

        return "form/post";

    }


    @GetMapping("/form/login")
    public String um(){

        return "redirect:/main";
    }

    @PostMapping("/find")
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

    @PostMapping("/savePost")
    public String Postsave(PostDto postDto){

        log.info("save = {}", postDto.getTitle());

        listService.save(postDto);

        return "form/index";

    }



}
