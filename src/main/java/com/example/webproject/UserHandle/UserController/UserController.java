package com.example.webproject.UserHandle.UserController;

import com.example.webproject.UserHandle.DTO.UserInfoDto;
import com.example.webproject.UserHandle.Entity.UserInfo;
import com.example.webproject.UserHandle.UserDaoService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/main/**")
    public String LoadLoginUserName(HttpServletRequest request){

        UserInfo user = (UserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String name = user.getName();

        HttpSession session = request.getSession();

        session.setAttribute("loginUser",name);

        return "form/index";
    }

    @PostMapping("/user")
    public String signup(UserInfoDto infoDto) {

        userService.save(infoDto);

        log.info("save: {}",infoDto);

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

