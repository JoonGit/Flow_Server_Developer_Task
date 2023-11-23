package com.back_end.back_end.Vo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@Builder
public class CustomVo {
    List<String> customName;
    String error;

    public CustomVo(List<String> customName, String error) {
        this.customName = customName;
        this.error = error;
    }

}
