package com.headblog.blog.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UserRole {
    ADMIN("admin", 1),USER("user",2),ANONYMOUS("anonymous",3);

    @Getter
    private String role;

    @Getter
    private Integer id;
}
