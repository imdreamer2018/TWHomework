package com.thoughtworks.homework.repository;

import com.thoughtworks.homework.entity.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Posts,Integer> {


    Page<Posts> findByUsersId(Integer usersId, Pageable pageable);
}
