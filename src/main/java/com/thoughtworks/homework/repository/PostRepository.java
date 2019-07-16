package com.thoughtworks.homework.repository;

import com.thoughtworks.homework.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Posts,Integer> {

    @Query(value = "select * from posts order by timestamp desc",nativeQuery = true)
    Iterable<Posts> findAllOderByDesc();
}
