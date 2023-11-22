package com.back_end.back_end.Controller;

import com.back_end.back_end.Service.CustomExtensionService;
import com.back_end.back_end.Vo.CustomExtensionVo;
import com.back_end.back_end.dto.CustomExtensionSaveDto;
import com.back_end.back_end.dto.CustomToUserDto;
import com.back_end.back_end.dto.FixExtensionSaveDto;
import com.back_end.back_end.dto.FixToUserDto;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/custom")
public class CustomExtensionController {
    private final CustomExtensionService customExtensionService;

    public CustomExtensionController(CustomExtensionService customExtensionService) {
        this.customExtensionService = customExtensionService;
    }

    @PostMapping("/save")
    public void Save(@RequestBody CustomExtensionSaveDto extensionDto) {
        customExtensionService.Save(extensionDto);
    }

    @PostMapping("/delete")
    public void Delete(@RequestBody CustomExtensionSaveDto extensionDto) {
        customExtensionService.Delete(extensionDto);
    }

    @GetMapping("/getcustom")
    public CustomExtensionVo Get(String userId) {
        return(customExtensionService.GetExtensionsForUser(userId));
    }


}
