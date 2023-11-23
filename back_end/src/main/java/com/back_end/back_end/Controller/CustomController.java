package com.back_end.back_end.Controller;

import com.back_end.back_end.Service.CustomService;
import com.back_end.back_end.Vo.CustomVo;
import com.back_end.back_end.dto.CustomDeleteDto;
import com.back_end.back_end.dto.CustomSaveDto;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/custom")
public class CustomController {
    private final CustomService customService;

    public CustomController(CustomService customService) {
        this.customService = customService;
    }

    @PostMapping("/save")
    public String Save(@RequestBody CustomSaveDto extensionDto) {
        return (customService.Save(extensionDto));
    }

    @PostMapping("/delete")
    public String Delete(@RequestBody CustomDeleteDto extensionDto) {
        return (customService.Delete(extensionDto));
    }

    @GetMapping("/getcustom")
    public CustomVo Get(String userId) {
        return (customService.GetCustomForUser(userId));
    }


}
