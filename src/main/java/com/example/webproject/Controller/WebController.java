package com.example.webproject.Controller;

import com.example.webproject.Config.WebSecurityConfig;
import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.ListDTO.PostDto;
import com.example.webproject.List.ListDaoService.ListService;
import com.example.webproject.UserHandle.DTO.UserInfoDto;
import com.example.webproject.UserHandle.Entity.PrincipalDetails;
import com.example.webproject.UserHandle.Entity.UserInfo;
import com.example.webproject.UserHandle.UserDaoService.CustomOAuth2UserService;
import com.example.webproject.UserHandle.UserDaoService.UserService;
import com.nimbusds.jose.shaded.json.JSONObject;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Controller
public class WebController {

    @Autowired
    private ListService listService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    private final InMemoryClientRegistrationRepository inMemoryClientRegistrationRepository;

    @GetMapping("/main")
    public String MainPage(@AuthenticationPrincipal PrincipalDetails principalDetails,@PageableDefault(size = 15,sort = "createTime",direction = Sort.Direction.DESC) Pageable pageable, Model model,HttpSession session){

        UserInfo userInfo = principalDetails.getUser();

        session.setAttribute("loginUser",userInfo);

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
    public String save(PostDto postDto,@AuthenticationPrincipal PrincipalDetails principalDetails){

        listService.save(postDto,principalDetails);

        log.info("Post = {}", postDto);

        return "redirect:/main";

    }

    @GetMapping("/naver/oauth2")
    public String Authorize(){

        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString(32);

        String url = "https://nid.naver.com" +
                "/oauth2.0/authorize?" +
                "client_id=NQj0cmSwEsYbB8ajFwfe" +
                "&response_type=code" +
                "&state=" + state +
                "&redirect_uri=http://localhost:8080/oauth2/code/naver";

        log.info(url);

        return "redirect:" + url;
    }

    @RequestMapping(value = "/oauth2/code/naver",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
    public String Oauth2Login (@RequestParam(value = "code") String code, @RequestParam(value = "state") String state){

        log.info("callback");

        ClientRegistration clientRegistrations = inMemoryClientRegistrationRepository.findByRegistrationId("naver");

        WebClient webclient = WebClient.builder()
                .baseUrl("https://nid.naver.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();


        JSONObject response = webclient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth2.0/token")
                        .queryParam("client_id",clientRegistrations.getClientId())
                        .queryParam("client_secret",clientRegistrations.getClientSecret())
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("state", state)
                        .queryParam("code", code)
                        .build())
                .retrieve().bodyToMono(JSONObject.class)
                .block();

        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, (String) Objects.requireNonNull(response).get("access_token"),null,null,null);

        log.info("{}",response.get("expires_in"));

        customOAuth2UserService.loadUser(new OAuth2UserRequest(clientRegistrations,accessToken,null));

        return "redirect:/main";
    }



//    @GetMapping("/user")
//    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
//
//        log.info("principalDetails = {}", principalDetails);
//
//        return "user";
//    }

//    @GetMapping("/main/**")
//    public String LoadLoginUserName(HttpServletRequest request){
//
//        UserInfo user = (UserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        String name = user.getName();
//
//        HttpSession session = request.getSession();
//
//        session.setAttribute("loginUser",name);
//
//        return "form/index";
//    }

    @GetMapping("/main/post")
    public String detail(@RequestParam(value = "id") int id,Model model){
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

        return "redirect:/login";
    }

    @PostMapping("/user")
    public String signup(UserInfoDto infoDto) {

        UserInfo user = userService.save(infoDto);

        log.info("save: {}",user);

        return "redirect:/login";
    }

    @GetMapping("/main/search")
    public String search(@PageableDefault(size = 20,sort = "id") Pageable pageable,@RequestParam String title,Model model){

        List<Post> postList = listService.postPage(title,pageable);

        model.addAttribute("postList",postList);

        return "form/index";
    }

//    @GetMapping("/find")
////    public Post view(@RequestParam(value = "title") String title){
//        try {
//
//            return listService.LoadOneByTitle(title);
//
//        } catch (NotFoundException e) {
//
//            e.printStackTrace();
//        }
//
//        return null;
//
//    }

    @GetMapping("/delete/{id}")
    public String delete(@AuthenticationPrincipal PrincipalDetails principalDetails,@PathVariable int id) throws NotFoundException{

        Post post = listService.FindById(id);

        if (Objects.equals(post.getUserInfo().getName(),principalDetails.getUsername())) {

            listService.delete(id);

            log.info("Delete = {}", post);
        }

        return "redirect:/main";
    }

    @GetMapping("/delete/form")
    public String deleteform(){

        return "/view";

    }
    @GetMapping("/")
    public String index(){

        return "redirect:/main";

    }

    @GetMapping("main/formpost")
    public String postform() {

        return "form/post";

    }

}
