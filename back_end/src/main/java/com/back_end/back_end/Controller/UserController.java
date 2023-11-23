package com.back_end.back_end.Controller;

import com.back_end.back_end.Service.UserService;
import com.back_end.back_end.dto.UserSaveDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String Signup(@RequestBody UserSaveDto requestDto) {
        return (userService.Signup(requestDto));
    }

    @PostMapping("/login")
    public String Login(@RequestBody UserSaveDto requestDto) {
        return (userService.Login(requestDto));
    }


}
