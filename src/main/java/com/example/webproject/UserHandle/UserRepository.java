package com.example.webproject.UserHandle;

import com.example.webproject.List.Entity.Post;
import com.example.webproject.UserHandle.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserInfo, String> {

    @Query("select u from UserInfo u where u.name = ?1")
    Optional<UserInfo> findByName(String name);

    @Query("select u from UserInfo u where u.name = ?1")
    UserInfo findByname(String name);


}



