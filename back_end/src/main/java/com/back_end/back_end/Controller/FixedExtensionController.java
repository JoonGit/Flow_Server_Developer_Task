package com.back_end.back_end.Controller;
import com.back_end.back_end.Service.FixedExtensionService;
import com.back_end.back_end.Vo.FixedExtensionVo;
import com.back_end.back_end.dto.FixExtensionSaveDto;
import com.back_end.back_end.dto.FixToUserDto;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/fixed")
public class FixedExtensionController {
    private final FixedExtensionService fixedExtensionService;

    public FixedExtensionController(FixedExtensionService fixedExtensionService) {
        this.fixedExtensionService = fixedExtensionService;
    }

    @PostMapping("/save")
    public void Save(@RequestBody FixExtensionSaveDto extensionDto) {
        fixedExtensionService.Save(extensionDto);
    }

    @PostMapping("/delete")
    public void Delete(@RequestBody FixExtensionSaveDto extensionDto) {
        fixedExtensionService.Delete(extensionDto);
    }

    @PostMapping("/savefixtouser")
    public void FixedSave(@RequestBody FixToUserDto requestDto) {
        fixedExtensionService.SaveFixedToUser(requestDto);
    }

    @PostMapping("/deletefixtouser")
    public void FixedDelete(@RequestBody FixToUserDto requestDto) {
        fixedExtensionService.DeleteFixedToUser(requestDto);
    }

    @GetMapping("/getfixed")
    public FixedExtensionVo Get(String userId) {
        return(fixedExtensionService.GetExtensionsForUser(userId));
    }

}
