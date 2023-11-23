package com.back_end.back_end.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonAutoDetect
@Getter
@NoArgsConstructor
public class CustomDeleteDto {
    private String userId;
    private String name;

    @Builder
    public CustomDeleteDto(String userId,
                           String name
    ) {
        this.userId = userId;
        this.name = name;

    }
}
