package com.example.webproject.DaoService;

import com.example.webproject.DTO.UserInfoDto;

import com.example.webproject.Entity.UserInfo;
import com.example.webproject.Entity.UserRepository;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public void login(String name, String password) throws UsernameNotFoundException {

        UserInfo user = userRepository.findByname(name)
                .orElseThrow(() -> new UsernameNotFoundException(name));

        UserInfo userInfo = new UserInfo();

        userInfo.setName(userInfo.getName());
        userInfo.setPassword(userInfo.getPassword());

        Set<GrantedAuthority> roles = new HashSet<>();

//        roles = userInfo.setAuth();
        //                        .orElseThrow(() -> new UsernameNotFoundException((name)));
        log.info("login {} ",name);

//        return userInfo.setAuth(userInfo.getAuth());



    }

    @Override
    public UserInfo loadUserByUsername(String name) throws UsernameNotFoundException {

        log.info("load {} ",name);

        return userRepository.findByname(name)
                .orElseThrow(() -> new UsernameNotFoundException((name)));
    }

    public UserInfo save(UserInfoDto infoDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        infoDto.setPassword(encoder.encode(infoDto.getPassword()));

//        if (infoDto.getName() == null){
//
//        }

        log.info("sign up");
        return userRepository.save(UserInfo.builder()
                .name(infoDto.getName())
                .auth(infoDto.getAuth())
                .joindate(infoDto.getjoindate())
                .password(infoDto.getPassword()).build());
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
