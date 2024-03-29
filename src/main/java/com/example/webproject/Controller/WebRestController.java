package com.example.webproject.Controller;


import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.ListDaoService.ListService;
import com.example.webproject.UserHandle.UserDaoService.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class WebRestController {

    private final ListService listService;

    private final UserService userService;

    private final WebClientService webClientService;

//    @GetMapping("/main/Profile/{username}")
//    public String Profile(@PathVariable String username){
//
//
//
//        return "";
//    }

//    @GetMapping("/main/hello")
//    public String hello(){
//
//        return webClientService.hello();
//    }


}
