package com.thoughtworks.homework.repository;

import com.thoughtworks.homework.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRespository extends JpaRepository<Post,Integer> {

    Optional<Post> findPostById(Integer id);
    Optional<Post> findPostsByUser(Integer id);
}
