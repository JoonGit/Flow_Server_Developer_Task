package com.back_end.back_end.dto;

import com.back_end.back_end.Entity.ExtensionEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@JsonAutoDetect
@Getter
@NoArgsConstructor
public class ExtensionSaveDto {
    private String name;


    @Builder
    public ExtensionSaveDto(
            String name
            ) {
        this.name = name;

    }

    public ExtensionEntity toEntity() {
        return ExtensionEntity.builder()
                .name(name)
                .build();
    }


}
