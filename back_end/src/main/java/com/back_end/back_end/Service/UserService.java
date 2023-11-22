package com.back_end.back_end.Service;

import com.back_end.back_end.Entity.UserEntity;
import com.back_end.back_end.Repository.FixedExtensionRepository;
import com.back_end.back_end.Repository.UserRepository;
import com.back_end.back_end.dto.UserSaveDto;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final FixedExtensionRepository extensionRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder, FixedExtensionRepository extensionRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.extensionRepository = extensionRepository;
    }

    public String Signup(UserSaveDto requestDto) {
        try {
            Optional<UserEntity> user= userRepository.findByUserId(requestDto.getUserId());
            if (user.isPresent()) {
                return ("사용자가 이미 존재합니다");
            }
            userRepository.save(requestDto.toEntity(encoder.encode(requestDto.getPassword())));
            return "success";
        }  catch (Exception e) {
            // 그 외의 예외 처리
            return ("회원가입 동안 오류가 발생했습니다 다시 회원가입 해주세요" + e);
        }

    }
    public String Login(UserSaveDto requestDto) {
        try {
            // 유저 정보 조회
            Optional<UserEntity> user = userRepository.findByUserId(requestDto.getUserId());
            if (user == null) { return ("ID, Password를 확인 부탁드립니다"); }
            if (!encoder.matches(requestDto.getPassword(), user.get().getPassword())) { return ("ID, Password를 확인 부탁드립니다"); }
            return "success";
        }
        catch (Exception e) {
            return ("로그인하는 동안 오류가 발생했습니다 다시 로그인 해주세요" + e);
        }
    }


}
