package com.back_end.back_end.Controller;
import com.back_end.back_end.Service.FixedService;
import com.back_end.back_end.Vo.FixedVo;
import com.back_end.back_end.dto.FixedSaveDto;
import com.back_end.back_end.dto.FixToUserDto;
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
    public void Save(@RequestBody FixedSaveDto extensionDto) {
        fixedService.Save(extensionDto);
    }

    @PostMapping("/delete")
    public void Delete(@RequestBody FixedSaveDto extensionDto) {
        fixedService.Delete(extensionDto);
    }

    @PostMapping("/savefixtouser")
    public void FixedSave(@RequestBody FixToUserDto requestDto) {
        fixedService.SaveFixedToUser(requestDto);
    }

    @PostMapping("/deletefixtouser")
    public void FixedDelete(@RequestBody FixToUserDto requestDto) {
        fixedService.DeleteFixedToUser(requestDto);
    }

    @GetMapping("/getfixed")
    public FixedVo Get(String userId) {
        return(fixedService.GetFixedForUser(userId));
    }

}
