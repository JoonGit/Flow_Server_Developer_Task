package com.back_end.back_end.Controller;
import com.back_end.back_end.Resource.ExtensionInfoVo;
import com.back_end.back_end.Service.ExtensionService;
import com.back_end.back_end.dto.ExtensionSaveDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/Extension")
public class ExtensionController {
    private final ExtensionService extensionService;

    public ExtensionController(ExtensionService extensionService) {
        this.extensionService = extensionService;
    }

    @PostMapping("/save")
    public void TbSave(@RequestBody ExtensionSaveDto extensionDto) {
        extensionService.TbSave(extensionDto);
    }

    @GetMapping("/getinfo")
    public ResponseEntity<Object> Findall() {
        List<ExtensionInfoVo> result = extensionService.find();
        return ResponseEntity.ok().body(
                result);
    }
}
