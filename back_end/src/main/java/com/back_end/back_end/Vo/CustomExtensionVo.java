package com.back_end.back_end.Vo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@Builder
public class CustomExtensionVo {
    List<String> customExtensionName;

    public CustomExtensionVo(List<String> customExtensionName) {
        this.customExtensionName = customExtensionName;
    }

}
