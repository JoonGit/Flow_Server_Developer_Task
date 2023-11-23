package com.back_end.back_end.dto.User;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonAutoDetect
@Getter
@NoArgsConstructor
public class UserLoginDto {
    private String userId;
    private String password;

    @Builder
    public UserLoginDto(
            String userId,
            String password
    ) {
        this.userId = userId;
        this.password = password;

    }
}
