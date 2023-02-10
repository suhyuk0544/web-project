package com.example.webproject.Controller;

import com.example.webproject.List.Entity.Post;
import com.example.webproject.UserHandle.UserDaoService.UserService;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class WebClientService {


//    @Autowired
//    private UserService userService;
//
//    WebClient webClient = WebClient.builder()
//            .baseUrl("http://localhost:8080")
//            .build();
//
//    public Flux<Post> Profile(String username){
//
//        List<Post> posts = userService.FindUser(username).getPosts();
//
//
//        log.info("Posts = {}", posts);
//
//
//        return webClient.post()
//                .uri("/main/Profile/{username}", username)
//                .accept(MediaType.APPLICATION_JSON)
//                .bodyValue(posts)
//                .retrieve()
//                .bodyToMono(DataApiResponse.class)
//                .flatMapIterable(DataApiResponse::getContent);
//    }
//
//    public Mono<String> hello(){
//
//        return webClient.post()
//                .uri("/main/hello")
//                .accept(MediaType.TEXT_HTML)
//                .body(Mono.just("hi"),String.class)
//                .retrieve()
//                .bodyToMono(String.class);
//    }
//
//    @Value
//    public static class DataApiResponse {
//        List<Post> content;
//    }
}
