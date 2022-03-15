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

//    public UserInfo login(String name, String password) throws UsernameNotFoundException {
//
//        UserInfoDto userInfoDto = new UserInfoDto();
//        UserInfo userInfo = new UserInfo();
//
//        if (userRepository.findByname(name)){
//                .orElseThrow(() -> new UsernameNotFoundException(name));
//
//            elif (userInfo.getPassword().equals(userRepository.getByPassword(password))) {
//
//            userInfo.setName(userInfo.getName());
//            userInfo.setPassword(userInfo.getPassword());
//
//            userInfo.getAuthorities();
//            userInfo.isAccountNonExpired();
//            userInfo.isEnabled();
//            userInfo.isAccountNonLocked();
//            userInfo.isCredentialsNonExpired();
//
//            log.info("login {} ",name);
//
//            return userInfo;
//        }
//        return userInfo;
//    }

    @Override
    public UserInfo loadUserByUsername(String name) throws UsernameNotFoundException {

        log.info("load {} ",name);

        return userRepository.findByname(name)
                .orElseThrow(() -> new UsernameNotFoundException((name)));
    }

    public UserInfo save(UserInfoDto infoDto) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        infoDto.setPassword(encoder.encode(infoDto.getPassword()));

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
