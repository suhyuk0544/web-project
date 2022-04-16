package com.example.webproject.UserHandle.UserController;


import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.ListDTO.PostDto;
import com.example.webproject.UserHandle.DTO.UserInfoDto;
import com.example.webproject.List.ListDaoService.ListService;
import com.example.webproject.UserHandle.UserDaoService.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {

    private final UserService userService;

//    @GetMapping("/form/login")
//    public String t(){
//
//        return "form/index";
//    }


////    @GetMapping("/main")
//    public String main(){
//
//        return "form/index";
//
//    }



    @PostMapping("/user")
    public String signup(UserInfoDto infoDto) {// 회원 추가

        userService.save(infoDto);

        log.info("save: name = {} date = {}",infoDto.getName(),infoDto.getJoindate());

        return "redirect:form/login";
    }

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        log.info("logout");
        return "redirect:form/login";
    }
//    @PostMapping("/user")
//    public String login(String name,String password){
//        userService.login(name,password);
//
//
//        if (userService.login(password, name)){
//
//            log.info("login s");
//
//            return "redirect:form/index";
//        }
//        log.info("login {}",name);
//        return "redirect:form/login";
//
//    }

}

