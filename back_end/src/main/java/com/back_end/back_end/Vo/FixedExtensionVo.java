package com.back_end.back_end.Vo;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@Builder
public class FixedExtensionVo {
    List<String> fixedExtensionName;
    List<Boolean> fixedExtensionStatus;

    public FixedExtensionVo(List<String> fixedExtensionName, List<Boolean> fixedExtensionStatus) {
        this.fixedExtensionName = fixedExtensionName;
        this.fixedExtensionStatus = fixedExtensionStatus;
    }
}
