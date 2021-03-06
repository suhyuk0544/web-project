package com.example.webproject.Controller;

import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.ListDTO.PostDto;
import com.example.webproject.List.ListDaoService.ListService;
import com.example.webproject.UserHandle.DTO.UserInfoDto;
import com.example.webproject.UserHandle.Entity.UserInfo;
import com.example.webproject.UserHandle.UserDaoService.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@org.springframework.stereotype.Controller
public class WebController {
    private final ListService listService;

    private final UserService userService;

    @GetMapping("/main")
    public String page(@PageableDefault(size = 15,sort = "id",direction = Sort.Direction.DESC) Pageable pageable, Model model){

        Page<Post> postPage = listService.postPage(pageable);

        model.addAttribute("postList",postPage);

        return "form/index";
    }

    @PostMapping("/main/{id}/setPost")
    public String setPost(PostDto postDto,@PathVariable int id) throws NotFoundException {

        Post post = listService.setPost(postDto,id);

        log.info("Set Post = {}",post);

        return "redirect:/main";
    }

    @GetMapping("/main/{id}/setPost")
    public String setPostForm(Model model,@PathVariable int id) throws NotFoundException {

        Post post = listService.FindById(id);

        model.addAttribute("SetPost",post);

        return "form/setPost";
    }

    @PostMapping("/main/savePost")
    public String save(PostDto postDto){

        UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        listService.save(postDto,user);

        log.info("Post = {}", postDto);

        return "redirect:/main";

    }

    @GetMapping("/main/**")
    public String LoadLoginUserName(HttpServletRequest request){

        UserInfo user = (UserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String name = user.getName();

        HttpSession session = request.getSession();

        session.setAttribute("loginUser",name);

        return "form/index";
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

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {

        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        log.info("logout");
        return "redirect:form/login";
    }

    @PostMapping("/user")
    public String signup(UserInfoDto infoDto) {

        userService.save(infoDto);

        log.info("save: {}",infoDto);

        return "redirect:form/login";
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
    @GetMapping("/")
    public String index(){

        return "redirect:/main";

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
