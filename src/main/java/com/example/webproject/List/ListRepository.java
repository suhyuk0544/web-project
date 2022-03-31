package com.example.webproject.List;

import com.example.webproject.List.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ListRepository extends JpaRepository<Post,String> {

    Optional<Post> findAllByTitle(String title);

    Optional<Post> findByTitle(String title);


}
