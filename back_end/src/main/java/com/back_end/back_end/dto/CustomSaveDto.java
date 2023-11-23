package com.back_end.back_end.dto;

import com.back_end.back_end.Entity.CustomEntity;
import com.back_end.back_end.Entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonAutoDetect
@Getter
@NoArgsConstructor
public class CustomSaveDto {
    private String userId;
    private String name;


    @Builder
    public CustomSaveDto(String userId,
                         String name
            ) {
        this.userId = userId;
        this.name = name;

    }

    public CustomEntity toEntity(UserEntity user) {
        return CustomEntity.builder()
                .name(name)
                .user(user)
                .build();
    }


}
