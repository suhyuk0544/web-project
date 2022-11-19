package com.example.webproject.List;

import com.example.webproject.List.Entity.Post;
import com.example.webproject.List.Entity.Question;
import com.example.webproject.UserHandle.Entity.UserInfo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListRepository extends JpaRepository<Post,String> {

    @Query("select p from Post p where p.title = ?1")
    Optional<Post> findAllByTitle(String title);

    @Query("select p from Post p where p.title = ?1")
    Optional<Post> findByTitle(String title);

    @Query("select p from Post p where p.id = ?1")
    Optional<Post> findById(int id);

    @Query("select p from Post p where p.title like concat('%', ?1, '%')")
    List<Post> findByTitleContainingOrderById(String title, Pageable pageable);

    @Query("select p from Post p where p.userInfo = ?1 order by p.id")
    Page<Post> findPostsByUserInfoOrderById(UserInfo userInfo, Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from Post p where p.id = ?1")
    void deleteById(int Id);

    @Override
    @Query("select p from Post p")
    Page<Post> findAll(Pageable pageable);

}
