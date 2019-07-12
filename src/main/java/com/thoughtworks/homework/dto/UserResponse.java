package com.thoughtworks.homework.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse<T> {
    private Integer code;
    private String message;
    private T data;
}
