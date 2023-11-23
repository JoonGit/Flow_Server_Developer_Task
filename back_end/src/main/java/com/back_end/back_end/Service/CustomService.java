package com.back_end.back_end.Service;


import com.back_end.back_end.Entity.CustomEntity;
import com.back_end.back_end.Entity.UserEntity;
import com.back_end.back_end.Repository.CustomRepository;
import com.back_end.back_end.Repository.UserRepository;
import com.back_end.back_end.Vo.CustomVo;
import com.back_end.back_end.dto.CustomSaveDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomService {
    private final CustomRepository customRepository;
    private final UserRepository userRepository;

    public CustomService(CustomRepository customRepository, UserRepository userRepository) {
        this.customRepository = customRepository;
        this.userRepository = userRepository;
    }
    public void Save(CustomSaveDto requestDto) {
        Optional<UserEntity> user = userRepository.findByUserId(requestDto.getUserId());
        CustomEntity custom = CustomEntity.builder()
                .name(requestDto.getName())
                .user(user.get())
                .build();
        customRepository.save(custom);
    }
    public void Delete(CustomSaveDto requestDto) {
        UserEntity user = userRepository.findByUserId(requestDto.getUserId()).orElseThrow(()-> new RuntimeException("User not found"));
        CustomEntity customToDelete = user.getCustomExtensions()
                .stream()
                .filter(extension -> extension.getName().equals(requestDto.getName()))
                .findFirst()
                .orElse(null);
        user.getCustomExtensions().remove(customToDelete);
        userRepository.save(user);
    }
    public CustomVo GetCustomForUser(String userId) {
        Optional<UserEntity> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            List<String> customNameList = user.getCustomExtensions().stream()
                    .map(CustomEntity::getName).sorted()
                    .collect(Collectors.toList());

            return CustomVo.builder().customName(customNameList).build();
        } else {
            return null;
        }
    }
}