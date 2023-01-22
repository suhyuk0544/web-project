package com.example.webproject.Controller;

import com.example.webproject.Config.ApiKey;
import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.Entity.Question;
import com.example.webproject.List.ListDTO.PostDto;
import com.example.webproject.List.ListDTO.QuestionDto;
import com.example.webproject.List.ListDaoService.ListService;
import com.example.webproject.UserHandle.DTO.UserInfoDto;
import com.example.webproject.UserHandle.Entity.PrincipalDetails;
import com.example.webproject.UserHandle.Entity.UserInfo;
import com.example.webproject.UserHandle.UserDaoService.ApiClient;
import com.example.webproject.UserHandle.UserDaoService.CustomOAuth2UserService;
import com.example.webproject.UserHandle.UserDaoService.UserService;
import com.nimbusds.jose.shaded.json.JSONObject;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.net.URI;
import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Controller
public class WebController {

    private final ListService listService;

    private final UserService userService;

    private final CustomOAuth2UserService customOAuth2UserService;

    private final ApiClient apiClient;

    private final InMemoryClientRegistrationRepository inMemoryClientRegistrationRepository;


    @GetMapping("/main")
    public String MainPage(@PageableDefault(size = 15,sort = "createTime",direction = Sort.Direction.DESC) Pageable pageable, Model model){

        Page<Post> postPage = listService.postPage(pageable);

        model.addAttribute("postList",postPage);

        return "form/index";
    }

    @GetMapping("/school")
    public String School(){

        WebClient webClient = WebClient.builder()
                .baseUrl("https://open.neis.go.kr")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.APPLICATION_JSON_UTF8))
                .build();

        net.minidev.json.JSONObject jsonObject = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/hub/mealServiceDietInfo")
                        .queryParam("Type","json")
                        .queryParam("KEY","73156fb2366246a2bd3456e038d04375")
                        .queryParam("pIndex","1")
                        .queryParam("pSize","30")
                        .queryParam("ATPT_OFCDC_SC_CODE","J10")
                        .queryParam("SD_SCHUL_CODE","7530581")
                        .queryParam("MLSV_YMD","20221212")
                        .build())
                .retrieve()
                .bodyToMono(net.minidev.json.JSONObject.class)
                .block();

        log.info(Objects.requireNonNull(jsonObject).toJSONString());

        return "/main";
    }

    @GetMapping("/ncp")
    public String NcpPush(){

        WebClient webClient = WebClient.builder()
                .baseUrl("https://sens.apigw.ntruss.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("x-ncp-apigw-timestamp",Long.toString(System.currentTimeMillis()))
                .defaultHeader("x-ncp-iam-access-key",ApiKey.AccessKey.getCode())
                .defaultHeader("x-ncp-apigw-signature-v2",userService.makeSignature(Long.toString(System.currentTimeMillis()),"GET","/push/v2"))
                .build();

        net.minidev.json.JSONObject jsonObject = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/push/v2")
                        .queryParam("Type","json")
                        .queryParam("KEY","73156fb2366246a2bd3456e038d04375")
                        .queryParam("pIndex","1")
                        .queryParam("pSize","30")
                        .queryParam("ATPT_OFCDC_SC_CODE","J10")
                        .queryParam("SD_SCHUL_CODE","7530581")
                        .queryParam("MLSV_YMD","20221212")
                        .build())
                .retrieve()
                .bodyToMono(net.minidev.json.JSONObject.class)
                .block();

        log.info(Objects.requireNonNull(jsonObject).toJSONString());

        return "/main";
    }


    @GetMapping("/main/geoLocation")
    public String GeoLocation(HttpServletRequest request){

        String ip = "";

        WebClient webClient = WebClient.builder()
                .baseUrl("https://geolocation.apigw.ntruss.com")
                .defaultHeader("x-ncp-apigw-timestamp",Long.toString(System.currentTimeMillis()))
                .defaultHeader("x-ncp-iam-access-key",ApiKey.AccessKey.getCode())
                .defaultHeader("x-ncp-apigw-signature-v2",userService.makeSignature(Long.toString(System.currentTimeMillis()),"GET","/geolocation/v2/geoLocation?ip="+ip+"&ext=t&responseFormatType=json"))
                .build();

        JSONObject jsonObject = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/geolocation/v2/geoLocation")
                        .queryParam("ip",ip)
                        .queryParam("ext","t")
                        .queryParam("responseFormatType","json")
                        .build()
                )
                .retrieve()
                .bodyToMono(JSONObject.class)
                .block();


        log.info((Objects.requireNonNull(jsonObject).toJSONString()));

        return "redirect:/main";
    }

    @GetMapping("/main/Profile")
    public String Profile(@RequestParam(value = "name") String username,@PageableDefault(size = 15,sort = "createTime",direction = Sort.Direction.DESC) Pageable pageable,Model model){

         Page<Post> posts = userService.FindUser(username,pageable);

         model.addAttribute("userInfoPosts",posts);

        return "form/UserInfo";
    }

    @PostMapping("/main/question/{id}")
    public String SaveQuestion(QuestionDto questionDto,@PathVariable int id,@AuthenticationPrincipal PrincipalDetails principalDetails){

        listService.SaveQuestion(id,questionDto,principalDetails.getUser());

        return "redirect:/main/post?id=" + id;
    }

    @PostMapping("/main/{id}/setPost")
    public String setPost(PostDto postDto, @PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) throws NotFoundException {


        listService.setPost(postDto,id,principalDetails.getUser());

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

    @GetMapping("/oauth2/{registrationId}")
    public String Authorize(@PathVariable String registrationId){

        SecureRandom random = new SecureRandom();
        String state = new BigInteger(130, random).toString(32);

        ClientRegistration clientRegistrations = inMemoryClientRegistrationRepository.findByRegistrationId(registrationId);

        String url = "https://nid.naver.com" +
                "/oauth2.0/authorize?" +
                "client_id=" + clientRegistrations.getClientId() +
                "&response_type=code" +
                "&state=" + state +
                "&redirect_uri=" + clientRegistrations.getRedirectUri();

        log.info(url);

        return "redirect:" + url;
    }

    @RequestMapping(value = "/oauth2/code/{registrationId}",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
    public String Oauth2Login(@PathVariable String registrationId,@RequestParam(value = "code") String code, @RequestParam(value = "state") String state){

        log.info("callback");

        ClientRegistration clientRegistrations = inMemoryClientRegistrationRepository.findByRegistrationId(registrationId);

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
                .retrieve()
                .bodyToMono(JSONObject.class)
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
    public String detail(@RequestParam(value = "id") int id, @PageableDefault(size = 500,sort = "date",direction = Sort.Direction.DESC) Pageable pageable, Model model){
        try {
            Post post = listService.FindById(id);

            Page<Question> questions = listService.questions(post,pageable);


            model.addAttribute("QuestionList",questions);
            model.addAttribute("Post",post);
        } catch (NotFoundException e) {

            return "redirect:/main";

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

        log.info(String.valueOf(infoDto));

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
