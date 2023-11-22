package com.back_end.back_end.dto;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonAutoDetect
@Getter
@NoArgsConstructor
public class FixToUserDto {

    private String userId;
    private String extensionName;


    @Builder
    public FixToUserDto(
            String userId,
            String extensionName
    ) {
        this.userId = userId;
        this.extensionName = extensionName;
    }

//    public UserEntity toEntity(String encodingPassword) {
//        return UserEntity.builder()
//                .userId(userId)
//                .password(encodingPassword)
//                .build();
//    }
}
