package com.back_end.back_end.Vo;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
@Builder
public class FixedVo {
    List<String> fixedName;
    List<Boolean> fixedStatus;

    public FixedVo(List<String> fixedName, List<Boolean> fixedStatus) {
        this.fixedName = fixedName;
        this.fixedStatus = fixedStatus;
    }
}
