package com.headblog.blog.dto;

import com.headblog.blog.domain.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EditUserDto {
    @NotBlank
    private String name;
    @NotNull
    private Status status;
}
