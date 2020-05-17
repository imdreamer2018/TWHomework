package com.thoughtworks.homework.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "comments")
public class Comments implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private String timestamp;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @JoinColumn(name = "users_id")
    private Users users;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @NotNull
    @JoinColumn(name = "posts_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Posts posts;

    @Column(name = "users_id", insertable = false, updatable = false)
    private Integer usersId;

    @Column(name = "posts_id", insertable = false, updatable = false)
    private Integer postsId;

    public Comments( String content, String timestamp, @NotNull Users users, @NotNull Posts posts, Integer usersId, Integer postsId) {
        this.content = content;
        this.timestamp = timestamp;
        this.users = users;
        this.posts = posts;
        this.usersId = usersId;
        this.postsId = postsId;
    }
}

