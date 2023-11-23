package com.back_end.back_end.dto;

import com.back_end.back_end.Entity.FixedEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonAutoDetect
@Getter
@NoArgsConstructor
public class FixedSaveDto {
    private String name;

    @Builder
    public FixedSaveDto(
            String name
    ) {
        this.name = name;
    }

    public FixedEntity toEntity() {
        return FixedEntity.builder()
                .name(name)
                .build();
    }
}
