package com.example.webproject.UserHandle.UserDaoService;
import com.example.webproject.Config.ApiKey;
import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.ListRepository;
import com.example.webproject.UserHandle.DTO.UserInfoDto;
import com.example.webproject.UserHandle.Entity.Auth;
import com.example.webproject.UserHandle.Entity.PrincipalDetails;
import com.example.webproject.UserHandle.Entity.UserInfo;
import com.example.webproject.UserHandle.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private final ListRepository listRepository;

    private final HttpSession httpSession;

    public Page<Post> FindUser(String name, Pageable pageable) throws UsernameNotFoundException{

        UserInfo userInfo = userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException((name)));

        return listRepository.findPostsByUserInfoOrderById(userInfo,pageable);
    }

    public String makeSignature(String timeStamp, String method, String url){

        String encodeBase64String = null;

        try {
            String message = new StringBuilder()
                    .append(method)
                    .append(" ")
                    .append(url)
                    .append("\n")
                    .append(timeStamp)
                    .append("\n")
                    .append(ApiKey.AccessKey.getCode())
                    .toString();

            log.info(message);

            SecretKeySpec signingKey = new SecretKeySpec(ApiKey.SecretKey.getCode().getBytes(StandardCharsets.UTF_8),"HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));

            encodeBase64String = Base64.encodeBase64String(rawHmac);

            log.info(encodeBase64String);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return encodeBase64String;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        UserInfo userInfo = userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException((name)));

        log.info("login = {}",userInfo);

        httpSession.setAttribute("loginUser",userInfo);

        return new PrincipalDetails(userInfo);
    }

    public UserInfo save(UserInfoDto infoDto) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        infoDto.setPassword(encoder.encode(infoDto.getPassword()));

        Optional<UserInfo> userInfo = userRepository.findByName(infoDto.getName());



        return userRepository.save(UserInfo.userDetailRegister()
                .name(infoDto.getName())
                .auth(Auth.ROLE_USER)
                .JoinDate(infoDto.getJoinDate())
                .password(infoDto.getPassword())
                .build());



    }

//    private Connection conn;
//
//    private PreparedStatement pstmt;
//
//    private ResultSet rs;
//
//    public UserDAO(){
//        try {
//            String dbURL =
//        } catch (ExceptionConst e){
//            e.print
//        }
//
//    }

}
