package com.example.webproject.DaoService;

import com.example.webproject.DTO.UserInfoDto;

import com.example.webproject.Entity.UserInfo;
import com.example.webproject.Entity.UserRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public boolean login(String name,String password) throws UsernameNotFoundException {

        userRepository.findByNameEqualsAndPasswordEquals(name,password)
                .orElseThrow(() -> new UsernameNotFoundException((name)));
        log.info("login"+ name);
        return true;

    }

    @Override
    public UserInfo loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepository.findByname(name)
                .orElseThrow(() -> new UsernameNotFoundException((name)));
    }

    public UserInfo save(UserInfoDto infoDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        infoDto.setPassword(encoder.encode(infoDto.getPassword()));



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
