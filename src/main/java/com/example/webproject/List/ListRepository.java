package com.example.webproject.List;

import com.example.webproject.List.Entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ListRepository extends JpaRepository<Post,String> {

    @Query("select p from Post p where p.title = ?1")
    Optional<Post> findAllByTitle(String title);

    @Query("select p from Post p where p.title = ?1")
    Optional<Post> findByTitle(String title);


    @Query("select p from Post p where p.title like concat('%', ?1, '%')")
    List<Post> findByTitleContaining(String title);



}
