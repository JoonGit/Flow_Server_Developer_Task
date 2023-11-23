package com.back_end.back_end.Controller;

import com.back_end.back_end.Service.Fixed.FixedService;
import com.back_end.back_end.Vo.Fixed.FixedVo;
import com.back_end.back_end.dto.Fixed.FixedSaveDto;
import com.back_end.back_end.dto.Fixed.FixToUserDto;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/fixed")
public class FixedController {
    private final FixedService fixedService;

    public FixedController(FixedService fixedService) {
        this.fixedService = fixedService;
    }

    @PostMapping("/save")
    public String Save(@RequestBody FixedSaveDto extensionDto) {
        return(fixedService.Save(extensionDto));
    }

    @PostMapping("/delete")
    public String Delete(@RequestBody FixedSaveDto extensionDto) {

        return(fixedService.Delete(extensionDto));
    }

    @PostMapping("/savefixtouser")
    public String FixedSave(@RequestBody FixToUserDto requestDto) {

        return(fixedService.SaveFixedToUser(requestDto));
    }

    @PostMapping("/deletefixtouser")
    public String FixedDelete(@RequestBody FixToUserDto requestDto) {
        return(fixedService.DeleteFixedToUser(requestDto));
    }

    @GetMapping("/getfixed")
    public FixedVo Get(String userId) {
        return (fixedService.GetFixedForUser(userId));
    }

}
