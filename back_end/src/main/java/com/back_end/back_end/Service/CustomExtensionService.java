package com.back_end.back_end.Service;


import com.back_end.back_end.Entity.CustomExtensionEntity;
import com.back_end.back_end.Entity.UserEntity;
import com.back_end.back_end.Repository.CustomExtensionRepository;
import com.back_end.back_end.Repository.UserRepository;
import com.back_end.back_end.Vo.CustomExtensionVo;
import com.back_end.back_end.dto.CustomExtensionSaveDto;
import com.back_end.back_end.dto.CustomToUserDto;
import com.back_end.back_end.dto.FixExtensionSaveDto;
import com.back_end.back_end.dto.FixToUserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomExtensionService {
    private final CustomExtensionRepository customExtensionRepository;
    private final UserRepository userRepository;

    public CustomExtensionService(CustomExtensionRepository customExtensionRepository, UserRepository userRepository) {
        this.customExtensionRepository = customExtensionRepository;
        this.userRepository = userRepository;
    }
    public void Save(CustomExtensionSaveDto requestDto) {
        Optional<UserEntity> user = userRepository.findByUserId(requestDto.getUserId());
        CustomExtensionEntity customExtension = CustomExtensionEntity.builder()
                .name(requestDto.getName())
                .user(user.get())
                .build();
        customExtensionRepository.save(customExtension);
    }
    public void Delete(CustomExtensionSaveDto requestDto) {
        UserEntity user = userRepository.findByUserId(requestDto.getUserId()).orElseThrow(()-> new RuntimeException("User not found"));
        CustomExtensionEntity customExtensionToDelete = user.getCustomExtensions()
                .stream()
                .filter(extension -> extension.getName().equals(requestDto.getName()))
                .findFirst()
                .orElse(null);
        user.getCustomExtensions().remove(customExtensionToDelete);
        userRepository.save(user);
    }
    public CustomExtensionVo GetExtensionsForUser(String userId) {
        Optional<UserEntity> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            List<String> customExtensionNameList = user.getCustomExtensions().stream()
                    .map(CustomExtensionEntity::getName)
                    .collect(Collectors.toList());

            return CustomExtensionVo.builder().customExtensionName(customExtensionNameList).build();
        } else {
            return null;
        }
    }
}