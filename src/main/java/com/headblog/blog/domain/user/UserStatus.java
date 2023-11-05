package com.headblog.blog.domain.user;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserStatus {
    ACTIVE(1), INACTIVE(2), EXPIRED(3), LOCKED(4);

    private Integer code;
}
