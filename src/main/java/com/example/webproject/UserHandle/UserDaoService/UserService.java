package com.example.webproject.UserHandle.UserDaoService;
import com.example.webproject.UserHandle.DTO.UserInfoDto;
import com.example.webproject.UserHandle.Entity.Auth;
import com.example.webproject.UserHandle.Entity.PrincipalDetails;
import com.example.webproject.UserHandle.Entity.UserInfo;
import com.example.webproject.UserHandle.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


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
    public UserInfo FindUser(String name) throws UsernameNotFoundException{

        return userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException((name)));
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        UserInfo userInfo = userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException((name)));

        log.info("login = {}",userInfo);

        return new PrincipalDetails(userInfo);
    }

    public UserInfo save(UserInfoDto infoDto) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        infoDto.setPassword(encoder.encode(infoDto.getPassword()));

        Optional<UserInfo> userInfo = userRepository.findByName(infoDto.getName());

        if (userInfo.isEmpty()){

            return userRepository.save(UserInfo.userDetailRegister()
                        .name(infoDto.getName())
                        .auth(Auth.ROLE_USER)
                        .JoinDate(infoDto.getJoinDate())
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
