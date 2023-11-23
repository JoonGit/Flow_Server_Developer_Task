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
    private String fixedName;


    @Builder
    public FixToUserDto(
            String userId,
            String fixedName
    ) {
        this.userId = userId;
        this.fixedName = fixedName;
    }
}
