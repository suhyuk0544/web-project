package com.example.webproject.Entity;

import com.example.webproject.Entity.UserInfo;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByname(String name);


    Optional<UserInfo> findByNameEqualsAndPasswordEquals(String name,String password);
}



