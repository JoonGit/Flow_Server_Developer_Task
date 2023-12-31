package com.back_end.back_end.Vo.Fixed;


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

    String error ;


    public FixedVo(List<String> fixedName, List<Boolean> fixedStatus, String error) {
        this.fixedName = fixedName;
        this.fixedStatus = fixedStatus;
        this.error = error;
    }
}
