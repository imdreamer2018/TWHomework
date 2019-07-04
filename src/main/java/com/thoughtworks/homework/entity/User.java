package com.thoughtworks.homework.entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private int age;

    private String gender;

    public User(){}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }

    public User(String username, int age, String gender) {
        this.username = username;
        this.age = age;
        this.gender = gender;
    }

}
