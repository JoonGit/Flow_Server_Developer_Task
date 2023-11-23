package com.back_end.back_end.dto.Custom;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonAutoDetect
@Getter
@NoArgsConstructor
public class CustomToUserDto {

    private String userId;
    private String customName;


    @Builder
    public CustomToUserDto(
            String userId,
            String customName
    ) {
        this.userId = userId;
        this.customName = customName;
    }

}
