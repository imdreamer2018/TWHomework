package com.thoughtworks.homework.service;

import com.github.javafaker.Faker;
import com.thoughtworks.homework.dto.BaseResponse;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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

    public CommentResponse<Iterable<Comments>> getAllComments(
            Pageable pageable){
        CommentResponse<Iterable<Comments>> commentResponse = new CommentResponse<>();
        commentResponse.setCode(200);
        commentResponse.setMessage("评论数据获取成功！");
        commentResponse.setData(commentRepository.findAll(pageable));
        return commentResponse;
    }

    public CommentResponse<Iterable<Comments>> findComment(
            Integer postId, Pageable pageable) {
        CommentResponse<Iterable<Comments>> commentResponse = new CommentResponse<>();
        commentResponse.setCode(200);
        commentResponse.setMessage("评论数据获取成功！");
        commentResponse.setData(commentRepository.findByPostsId(postId, pageable));
        return commentResponse;
    }

    public CommentResponse<Comments> createComment(int postId,Comments comments) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String strStartTime = sdf.format(new Date());
        Users users = currentUserInfoService.getUserInfo();
        Posts posts = getPostInfo(postId);
        Comments p = new Comments(comments.getContent(),strStartTime, users, posts,users.getId(),posts.getId());
        commentRepository.save(p);
        return generatePostRes(201,"评论发表成功！",p);
    }

    public BaseResponse createCommentsByFaker(int commentsNumber) {
        Faker faker = new Faker(new Locale("zh-CN"));

        for (int i = 0; i <commentsNumber ; i++) {
            String content= "";
            if (i % 3 ==0){
                content = faker.gameOfThrones().quote();
            }
            else if(i % 3 == 1) {
                content = faker.hobbit().quote();
            }
            else {
                content = faker.lordOfTheRings().location();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            String strStartTime = sdf.format(faker.date().birthday());
            int userId = Integer.parseInt(faker.numerify("##"));
            int postId = Integer.parseInt(faker.numerify("###"));
            Optional<Users> users = userRepository.findById(userId);
            Optional<Posts> posts = postRepository.findById(postId);
            if (!users.isPresent() || !posts.isPresent()){
                continue;
            }
            Comments c = new Comments(content,strStartTime, users.get(),posts.get(), userId,postId);
            commentRepository.save(c);
        }
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(200);
        baseResponse.setMessage("创建随机博客评论成功");
        return baseResponse;
    }


    public CommentResponse<Comments> updateComment(Integer postId, Integer commentId, Comments comments) {
        Optional<Comments> c = commentRepository.findById(commentId);
        Optional<Posts> p = postRepository.findById(postId);
        if (!c.isPresent() || !p.isPresent() || !postId.equals(c.get().getPostsId())) {
            throw new BasePostException("该评论不存在！");
        }
        Users u = currentUserInfoService.getUserInfo();
        if (u.getId().equals(c.get().getUsersId()) || u.getId().equals(p.get().getUsersId()) || u.getRole().equals("ROLE_MODERATE") || u.getRole().equals("ROLE_ADMIN")) {
            c.get().setContent(comments.getContent());
            commentRepository.save(c.get());
            return generatePostRes(200, "评论更新成功！", c.get());
        }
        throw new AuthorizationException("您没有更新此评论的权限！");
    }

    public CommentResponse<Comments> deleteComment (Integer postId, Integer commentId){
        Optional<Comments> c = commentRepository.findById(commentId);
        Optional<Posts> p = postRepository.findById(postId);
        if (!c.isPresent() || !p.isPresent() || !postId.equals(c.get().getPostsId())) {
            throw new BasePostException("该评论不存在！");
        }
        Users u = currentUserInfoService.getUserInfo();
        if (u.getId().equals(c.get().getUsersId()) || u.getId().equals(p.get().getUsersId()) || u.getRole().equals("ROLE_MODERATE") || u.getRole().equals("ROLE_ADMIN")) {
            commentRepository.deleteById(commentId);
            return generatePostRes(200, "评论删除成功！", c.get());
        }
        throw new AuthorizationException("您没有删除此评论的权限！");
    }

}
