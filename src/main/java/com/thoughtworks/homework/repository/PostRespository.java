package com.thoughtworks.homework.repository;

import com.thoughtworks.homework.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRespository extends JpaRepository<Posts,Integer> {

    Optional<Posts> findPostById(Integer id);
    Optional<Posts> findPostsById(Integer id);

    @Query(value = "select * from Posts order by timestamp desc",nativeQuery = true)
    Optional<Posts> findAllOderByDesc();
}
