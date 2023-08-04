package com.headblog.blog.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Status {
    ACTIVE(1), INACTIVE(2);

    private Integer codigo;
}
