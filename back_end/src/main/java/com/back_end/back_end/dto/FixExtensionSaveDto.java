package com.back_end.back_end.dto;

import com.back_end.back_end.Entity.FixedExtensionEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonAutoDetect
@Getter
@NoArgsConstructor
public class FixExtensionSaveDto {
    private String name;


    @Builder
    public FixExtensionSaveDto(
            String name
            ) {
        this.name = name;

    }

    public FixedExtensionEntity toEntity() {
        return FixedExtensionEntity.builder()
                .name(name)
                .build();
    }


}
