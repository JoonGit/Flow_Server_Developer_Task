package com.back_end.back_end.dto.Custom;

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
}
