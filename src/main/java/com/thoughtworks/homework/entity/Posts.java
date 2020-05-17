package com.thoughtworks.homework.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "posts")
public class Posts implements Serializable {

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
    private String timestamp;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "users_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonIgnore
    private Users users;

    @Column(name = "users_id", updatable = false, insertable = false)
    private Integer usersId;

    public Posts(@NotNull String title, String content, String timestamp, @NotNull Users users, Integer usersId) {
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
        this.users = users;
        this.usersId = usersId;
    }
}
