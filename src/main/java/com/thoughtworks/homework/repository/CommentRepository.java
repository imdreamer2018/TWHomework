package com.thoughtworks.homework.repository;

import com.thoughtworks.homework.entity.Comments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments,Integer> {


    Page<Comments> findByPostsId(Integer postsId, Pageable pageable);
}
