package com.back_end.back_end.dto;

import com.back_end.back_end.Entity.CustomExtensionEntity;
import com.back_end.back_end.Entity.FixedExtensionEntity;
import com.back_end.back_end.Entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonAutoDetect
@Getter
@NoArgsConstructor
public class CustomExtensionSaveDto {
    private String userId;
    private String name;


    @Builder
    public CustomExtensionSaveDto(String userId,
            String name
            ) {
        this.userId = userId;
        this.name = name;

    }

    public CustomExtensionEntity toEntity(UserEntity user) {
        return CustomExtensionEntity.builder()
                .name(name)
                .user(user)
                .build();
    }


}
