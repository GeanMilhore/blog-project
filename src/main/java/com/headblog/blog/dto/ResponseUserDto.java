package com.headblog.blog.dto;

import com.headblog.blog.domain.Status;
import lombok.Data;

@Data
public class ResponseUserDto {
    private String name;
    private Status status;
}
