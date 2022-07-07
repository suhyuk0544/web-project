package com.example.webproject.UserHandle.UserDaoService;
import com.example.webproject.UserHandle.DTO.UserInfoDto;
import com.example.webproject.UserHandle.Entity.UserInfo;
import com.example.webproject.UserHandle.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

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


        Optional<UserInfo> userInfo = userRepository.findByname(infoDto.getName());

        if (userInfo.isEmpty()){

            log.info("sign up : {} time : {}",infoDto.getName(),infoDto.getJoindate());

            return userRepository.save(UserInfo.builder()
                    .name(infoDto.getName())
                    .auth(infoDto.getAuth())
                    .joindate(infoDto.getJoindate())
                    .password(infoDto.getPassword()).build());

        } else{

            throw new NullPointerException();

        }
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
