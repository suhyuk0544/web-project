package com.example.webproject.Controller;


import com.example.webproject.DTO.UserInfoDto;
import com.example.webproject.DaoService.UserService;
import com.example.webproject.Entity.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public String login(String name,String password){
        if (userService.login(password, name)){

            log.info("login s");

            return "form/index";

        }
        log.info("login f");
        return "redirect:form/login";

    }

    @PostMapping("/user")
    public String signup(UserInfoDto infoDto) {// 회원 추가
        userService.save(infoDto);
        log.info("save complete");
        return "redirect:form/login";
    }


    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        log.info("logout complete");
        return "redirect:form/login";
    }

}

