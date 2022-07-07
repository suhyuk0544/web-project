package com.example.webproject.List.ListController;

import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.ListDTO.PostDto;
import com.example.webproject.List.ListDaoService.ListService;
import com.example.webproject.UserHandle.Entity.UserInfo;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ListController {
    private final ListService listService;

    @PostMapping("/main/savePost")
    public String Postsave(PostDto postDto){

        UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        listService.save(postDto,user);

        log.info("Post = {}", postDto);

        return "redirect:/main";

    }

    @GetMapping("/main/{id}")
    public String detail(@PathVariable int id,Model model){
        try {
            Post post = listService.FindById(id);

            model.addAttribute("Post",post);

        } catch (NotFoundException e) {

            e.printStackTrace();

        }
        return "form/detail";
    }

    @GetMapping("/main/search")
    public String search(@PageableDefault(size = 20,sort = "id") Pageable pageable,PostDto postDto, Model model){

        List<Post> postList = listService.postPage(postDto,pageable);

        model.addAttribute("postList",postList);

        return "form/index";

    }

    @GetMapping("/find")
    public Post view(PostDto postDto){
        try {

            return listService.LoadOneByTitle(postDto.getTitle());

        } catch (NotFoundException e) {

            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("/delete")
    public String delete(PostDto postDto) {

        listService.delete(postDto);

        log.info("title = {} delete",postDto.getTitle());

        return "redirect:/main";
    }

    @GetMapping("/delete/form")
    public String deleteform(){

        return "view";

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
