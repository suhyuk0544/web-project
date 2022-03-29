package com.example.webproject.UserHandle;

import com.example.webproject.UserHandle.Entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserInfo, String> {



    Optional<UserInfo> findByname(String name);

}



