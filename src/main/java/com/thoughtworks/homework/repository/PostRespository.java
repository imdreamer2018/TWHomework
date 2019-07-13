package com.thoughtworks.homework.repository;

import com.thoughtworks.homework.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRespository extends JpaRepository<Posts,Integer> {

    Optional<Posts> findPostById(Integer id);
    Optional<Posts> findPostsByUser(Integer id);
}
