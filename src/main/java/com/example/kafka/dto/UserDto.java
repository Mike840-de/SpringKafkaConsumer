package com.example.kafka.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long age;
    private String name;
}
