package com.thoughtworks.homework.service;

import com.thoughtworks.homework.dto.CommentResponse;
import com.thoughtworks.homework.entity.Comments;
import com.thoughtworks.homework.entity.Posts;
import com.thoughtworks.homework.entity.Users;
import com.thoughtworks.homework.exception.AuthorizationException;
import com.thoughtworks.homework.exception.BasePostException;
import com.thoughtworks.homework.repository.CommentRepository;
import com.thoughtworks.homework.repository.PostRepository;
import com.thoughtworks.homework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    protected CurrentUserInfoService currentUserInfoService;

    private Posts getPostInfo(int postId) {
        Optional<Posts> posts = postRepository.findById(postId);
        return posts.orElseGet(Posts::new);
    }

    private CommentResponse<Comments> generatePostRes(int code, String message, Comments data){
        CommentResponse<Comments> postCommentResponse = new CommentResponse<>();
        postCommentResponse.setCode(code);
        postCommentResponse.setMessage(message);
        postCommentResponse.setData(data);
        return postCommentResponse;
    }

    public CommentResponse<Iterable<Comments>> getAllComments(){
        CommentResponse<Iterable<Comments>> commentResponse = new CommentResponse<>();
        commentResponse.setCode(200);
        commentResponse.setMessage("评论数据获取成功！");
        commentResponse.setData(commentRepository.findAllOderByDesc());
        return commentResponse;
    }

    public CommentResponse<Comments> newComment(int postId,Comments comments) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String strStartTime = sdf.format(new Date());
        Users users = currentUserInfoService.getUserInfo();
        Posts posts = getPostInfo(postId);
        Comments p = new Comments(comments.getTitle(), comments.getContent(),strStartTime, users, posts);
        commentRepository.save(p);
        return generatePostRes(200,"评论发表成功！",p);
    }

    public CommentResponse<Comments> findComment(Integer id) {
        Optional<Comments> p = commentRepository.findById(id);
        if (!p.isPresent()){
            throw new BasePostException("该评论不存在！");
        }
        commentRepository.save(p.get());
        return generatePostRes(200,"评论查找成功！",p.get());
    }

    public CommentResponse<Comments> updateComment(Comments comments) {
        Optional<Comments> p = commentRepository.findById(comments.getId());
        if (!p.isPresent()) {
            throw new BasePostException("该评论不存在！");
        }
        Users u = currentUserInfoService.getUserInfo();
        if (u.getId().equals(comments.getUsers().getId()) || u.getRole().equals("ROLE_MODERATE") || u.getRole().equals("ROLE_ADMIN")) {
            p.get().setTitle(comments.getTitle());
            p.get().setContent(comments.getContent());
            commentRepository.save(p.get());
            return generatePostRes(200, "评论更新成功！", p.get());
        }
        throw new AuthorizationException("您没有更新此评论的权限！");
    }

    public CommentResponse<Comments> deleteComment (Integer id){
        Optional<Comments> p = commentRepository.findById(id);
        if (!p.isPresent()) {
            throw new BasePostException("该评论不存在！");
        }
        Users u = currentUserInfoService.getUserInfo();
        if (u.getId().equals(p.get().getUsers().getId()) || u.getId().equals(p.get().getPosts().getId()) || u.getRole().equals("ROLE_ADMIN")) {
            commentRepository.deleteById(id);
            return generatePostRes(200, "评论删除成功！", p.get());
        }
        throw new AuthorizationException("您没有删除此评论的权限！");
    }

}
