package com.example.webproject.Controller;


import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.ListDaoService.ListService;
import com.example.webproject.UserHandle.Entity.UserInfo;
import com.example.webproject.UserHandle.UserDaoService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class WebRestController {

    private final ListService listService;

    private final UserService userService;

    @GetMapping("/main/Profile/{username}")
    public List<Post> Profile(@PathVariable String username){

        UserInfo user = userService.FindUser(username);

        //        model.addAttribute("UserPost",postList);
        if (user.getPosts() == null) {
            return null;
        }else {
            return user.getPosts();
        }
    }
}
