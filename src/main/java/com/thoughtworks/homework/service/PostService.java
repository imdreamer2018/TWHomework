package com.thoughtworks.homework.service;

import com.github.javafaker.Faker;
import com.thoughtworks.homework.dto.BaseResponse;
import com.thoughtworks.homework.dto.PostResponse;
import com.thoughtworks.homework.entity.Posts;
import com.thoughtworks.homework.entity.Users;
import com.thoughtworks.homework.exception.AuthorizationException;
import com.thoughtworks.homework.exception.BasePostException;
import com.thoughtworks.homework.repository.PostRepository;
import com.thoughtworks.homework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CurrentUserInfoService currentUserInfoService;

    private PostResponse<Posts> generatePostRes(int code, String message, Posts data){
        PostResponse<Posts> postPostResponse = new PostResponse<>();
        postPostResponse.setCode(code);
        postPostResponse.setMessage(message);
        postPostResponse.setData(data);
        return postPostResponse;
    }

    public PostResponse<Iterable<Posts>> getAllPosts(Pageable pageable){
        PostResponse<Iterable<Posts>> postResponse= new PostResponse<>();
        postResponse.setCode(200);
        postResponse.setMessage("文章数据获取成功！");
        postResponse.setData(postRepository.findAll(pageable));
        return postResponse;
    }

    public PostResponse<Posts> createPost(Posts posts) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String strStartTime = sdf.format(new Date());
        Users users = currentUserInfoService.getUserInfo();
        Posts p = new Posts(posts.getTitle(), posts.getContent(),strStartTime, users, users.getId());
        postRepository.save(p);
        return generatePostRes(201,"文章发表成功！",p);
    }

    public BaseResponse createPostsByFaker(int postsNumber) {
        Faker faker = new Faker(new Locale("zh-CN"));

        for (int i = 0; i <postsNumber ; i++) {
            String title = "";
            String content= "";
            if (i % 3 ==0){
                title = faker.gameOfThrones().character();
                content = faker.gameOfThrones().quote();
            }
            else if(i % 3 == 1) {
                title = faker.hobbit().character();
                content = faker.hobbit().quote();
            }
            else {
                title = faker.lordOfTheRings().character();
                content = faker.lordOfTheRings().location();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            String strStartTime = sdf.format(faker.date().birthday());
            int userId = Integer.parseInt(faker.numerify("##"));
            Optional<Users> users = userRepository.findById(userId);
            if (!users.isPresent()){
                continue;
            }
            Posts p = new Posts(title, content,strStartTime, users.get(), userId);
            postRepository.save(p);
        }
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(200);
        baseResponse.setMessage("创建随机博客成功");
        return baseResponse;
    }

    public PostResponse<Posts> findPost(Integer id) {
        Optional<Posts> p = postRepository.findById(id);
        if (!p.isPresent()){
            throw new BasePostException("该文章不存在！");
        }
        postRepository.save(p.get());
        return generatePostRes(200,"文章查找成功！",p.get());
    }

    public PostResponse<Posts> updatePost(Integer postId, Posts posts) {
        Optional<Posts> p = postRepository.findById(postId);
        if (!p.isPresent()) {
            throw new BasePostException("该文章不存在！");
        }
        Users u = currentUserInfoService.getUserInfo();
        if (u.getId().equals(p.get().getUsers().getId()) || u.getRole().equals("ROLE_ADMIN")) {
            p.get().setTitle(posts.getTitle());
            p.get().setContent(posts.getContent());
            postRepository.save(p.get());
            return generatePostRes(200, "文章更新成功！", p.get());
        }
        throw new AuthorizationException("您没有修改此文章的权限！");
    }

    public PostResponse<Posts> deletePost(Integer postId) {
        Optional<Posts> p = postRepository.findById(postId);
        if (!p.isPresent()){
            throw new BasePostException("该文章不存在！");
        }
        Users u = currentUserInfoService.getUserInfo();
        if (u.getId().equals(p.get().getUsers().getId()) || u.getRole().equals("ROLE_ADMIN")){
            postRepository.deleteById(postId);
            return generatePostRes(200,"文章删除成功！",p.get());
        }
        throw new AuthorizationException("您没有删除此文章的权限！");
    }

}
