package com.back_end.back_end.dto;


import com.back_end.back_end.Entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonAutoDetect
@Getter
@NoArgsConstructor
public class UserSaveDto {
    private String userId;
    private String password;


    @Builder
    public UserSaveDto(
            String userId,
            String password
    ) {
        this.userId = userId;
        this.password = password;

    }

    public UserEntity toEntity(String encodingPassword) {
        return UserEntity.builder()
                .userId(userId)
                .password(encodingPassword)
                .build();
    }

}
