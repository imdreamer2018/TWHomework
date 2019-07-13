package com.thoughtworks.homework.service;

import com.thoughtworks.homework.dto.CommentResponse;
import com.thoughtworks.homework.entity.Comments;
import com.thoughtworks.homework.entity.Posts;
import com.thoughtworks.homework.entity.Users;
import com.thoughtworks.homework.exception.AuthorizationException;
import com.thoughtworks.homework.exception.BasePostException;
import com.thoughtworks.homework.repository.CommentRespository;
import com.thoughtworks.homework.repository.PostRespository;
import com.thoughtworks.homework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRespository postRespository;

    @Autowired
    private CommentRespository commentRespository;

    private Users getUserInfo() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Users> user = userRepository.findUserByEmail((String) principal);
        return user.get();
    }
    private Posts getPostInfo(int post_id) {
        Optional<Posts> posts = postRespository.findById(post_id);
        return posts.get();
    }

    private CommentResponse<Comments> generatePostRes(int code, String message, Comments data){
        CommentResponse<Comments> postCommentResponse = new CommentResponse<>();
        postCommentResponse.setCode(code);
        postCommentResponse.setMessage(message);
        postCommentResponse.setData(data);
        return postCommentResponse;
    }

    public CommentResponse<Comments> getAllComments(){
        CommentResponse<Comments> commentResponse = new CommentResponse<>();
        commentResponse.setCode(200);
        commentResponse.setMessage("评论数据获取成功！");
        commentResponse.setData(commentRespository.findAllOderByDesc().get());
        return commentResponse;
    }

    public CommentResponse<Comments> newComment(int post_id,Comments comments) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String strStartTime = sdf.format(new Date());
        Users users = getUserInfo();
        Posts posts = getPostInfo(post_id);
        Comments p = new Comments(comments.getTitle(), comments.getContent(),strStartTime, users, posts);
        commentRespository.save(p);
        return generatePostRes(200,"评论发表成功！",p);
    }

    public CommentResponse<Comments> findComment(Integer id) {
        Optional<Comments> p = commentRespository.findById(id);
        if (!p.isPresent()){
            throw new BasePostException("该评论不存在！");
        }
        commentRespository.save(p.get());
        return generatePostRes(200,"评论查找成功！",p.get());
    }

    public CommentResponse<Comments> updateComment(Comments comments) {
        Users u = getUserInfo();
        if (u.getId().equals(comments.getUsers().getId()) || u.getRole().equals("ROLE_ADMIN")) {
            Optional<Comments> p = commentRespository.findById(comments.getId());
            if (!p.isPresent()) {
                throw new BasePostException("该评论不存在！");
            }
            p.get().setTitle(comments.getTitle());
            p.get().setContent(comments.getContent());
            commentRespository.save(p.get());
            return generatePostRes(200, "评论更新成功！", p.get());
        }
        throw new AuthorizationException("您没有更新此评论的权限！");
    }

    public CommentResponse<Comments> deleteComment (Integer id){
        Users u = getUserInfo();
        if (u.getId().equals(id) || u.getRole().equals("ROLE_ADMIN")) {
            Optional<Comments> p = commentRespository.findById(id);
            if (!p.isPresent()) {
                throw new BasePostException("该评论不存在！");
            }
            commentRespository.deleteById(id);
            return generatePostRes(200, "评论删除成功！", p.get());
        }
        throw new AuthorizationException("您没有删除此评论的权限！");
    }

}
