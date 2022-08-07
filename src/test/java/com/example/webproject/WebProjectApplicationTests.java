package com.example.webproject;

import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.ListRepository;
import com.example.webproject.UserHandle.Entity.UserInfo;
import com.example.webproject.UserHandle.UserRepository;
import org.hibernate.boot.archive.scan.spi.PackageInfoArchiveEntryHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class WebProjectApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ListRepository listRepository;



    @Test
    void contextLoads() {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String pw = "수혁";

        pw = encoder.encode(pw);

        UserInfo userInfo = new UserInfo();

        userInfo.setName("장수혁");
        userInfo.setPassword(pw);

        userRepository.save(userInfo);

        Post post = new Post();

        post.setTitle("하하");
        post.setContent("하하");

        userInfo.add(post);

        listRepository.save(post);
    }

}
