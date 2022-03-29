package com.example.webproject.List.ListController;

import com.example.webproject.List.ListDTO.PostDto;
import com.example.webproject.List.ListDaoService.ListService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class ListController {

    private final ListService listService;

    @PostMapping("/find")
    public void view(PostDto postDto){

        try {
            listService.LaodByTitle(postDto.getTitle());

        } catch (NotFoundException e) {

            e.printStackTrace();
        }

    }

    @PostMapping("/post")
    public String Postsave(PostDto postDto){

        listService.save(postDto);


        return "redirect:form/main";

    }



}
