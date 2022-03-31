package com.example.webproject.List.ListController;

import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.ListDTO.PostDto;
import com.example.webproject.List.ListDaoService.ListService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ListController {

    private final ListService listService;

    @GetMapping("/form/login")
    public String um(){

        return "redirect:/main";
    }

    @PostMapping("/find")
    public void view(PostDto postDto){

        try {
            listService.LoadOneByTitle(postDto.getTitle());

        } catch (NotFoundException e) {

            e.printStackTrace();
        }

    }
    @PostMapping("/findall")
    public void viewall(PostDto postDto) {



    }

    @PostMapping("/savePost")
    public String Postsave(PostDto postDto){

        log.info("save = {}", postDto.getTitle());

        listService.save(postDto);

        return "form/index";

    }



}
