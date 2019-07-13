package com.thoughtworks.homework.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.crypto.Data;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "posts")
public class Comments implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Data timestamp;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "users_id")
    private Users users;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "posts_id")
    private Posts posts;
}

