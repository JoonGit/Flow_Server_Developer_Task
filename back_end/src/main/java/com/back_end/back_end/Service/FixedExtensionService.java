package com.back_end.back_end.Service;


import com.back_end.back_end.Entity.FixedExtensionEntity;
import com.back_end.back_end.Entity.UserEntity;
import com.back_end.back_end.Repository.FixedExtensionRepository;
import com.back_end.back_end.Repository.UserRepository;
import com.back_end.back_end.Vo.FixedExtensionVo;
import com.back_end.back_end.dto.FixExtensionSaveDto;
import com.back_end.back_end.dto.FixToUserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FixedExtensionService {
    private final UserRepository userRepository;

    private final FixedExtensionRepository fixedExtensionRepository;

    public FixedExtensionService(UserRepository userRepository, FixedExtensionRepository fixedExtensionRepository) {
        this.userRepository = userRepository;
        this.fixedExtensionRepository = fixedExtensionRepository;
    }
    public void Save(FixExtensionSaveDto requestDto) {
        fixedExtensionRepository.save(requestDto.toEntity());
    }
    public void Delete(FixExtensionSaveDto requestDto) {
        fixedExtensionRepository.delete(requestDto.toEntity());
    }

    public FixedExtensionVo GetExtensionsForUser(String userId) {
        Optional<UserEntity> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            List<String> userFixExtensionNameList = user.getFixedExtensions().stream()
                    .map(FixedExtensionEntity::getName)
                    .collect(Collectors.toList());

            List<FixedExtensionEntity> extensionEntity = fixedExtensionRepository.findAll();

            HashMap<String,Boolean> checkList = new HashMap<>();
            for (FixedExtensionEntity entity : extensionEntity) {
                checkList.put(entity.getName(), false);
            }

            for (String name : userFixExtensionNameList) {
                // 기존 키가 있는지 확인하여 기존 값이 false인 경우에만 true로 업데이트
                if (checkList.containsKey(name) && !checkList.get(name)) {
                    checkList.put(name, true);
                }
            }
            List<String> keyList = new ArrayList<>(checkList.keySet());
            List<Boolean> valueList = new ArrayList<>(checkList.values());
            return FixedExtensionVo.builder().fixedExtensionName(keyList).fixedExtensionStatus(valueList).build();
        } else {
            // 사용자를 찾을 수 없는 경우 처리
            return null; // 빈 Set을 반환하거나 에러 처리를 수행할 수 있습니다.
        }
    }

    public void SaveFixedToUser(FixToUserDto requestDto) {
        Optional<UserEntity> userOptional = userRepository.findByUserId(requestDto.getUserId());

        if (userOptional.isPresent()) {
            Optional<FixedExtensionEntity> extensionOptional = fixedExtensionRepository.findByName(requestDto.getExtensionName());

            if (extensionOptional.isPresent()) {
                UserEntity user = userOptional.get();
                FixedExtensionEntity extension = extensionOptional.get();

                user.getFixedExtensions().add(extension); // 사용자에게 확장 추가
                extension.getUsers().add(user); // 확장에 사용자 추가

                userRepository.save(user); // 사용자 저장
                fixedExtensionRepository.save(extension); // 확장 저장

            } else {
                // 해당 ID의 확장을 찾을 수 없는 경우 처리
            }
        } else {
            // 해당 ID의 사용자를 찾을 수 없는 경우 처리
        }
    }

    public void DeleteFixedToUser(FixToUserDto requestDto) {
        Optional<UserEntity> userOptional = userRepository.findByUserId(requestDto.getUserId());

        if (userOptional.isPresent()) {
            Optional<FixedExtensionEntity> extensionOptional = fixedExtensionRepository.findByName(requestDto.getExtensionName());

            if (extensionOptional.isPresent()) {
                UserEntity user = userOptional.get();
                FixedExtensionEntity extension = extensionOptional.get();

                user.getFixedExtensions().remove(extension);
                extension.getUsers().remove(user);

                userRepository.save(user);
                fixedExtensionRepository.save(extension);

            } else {
                // 해당 ID의 확장을 찾을 수 없는 경우 처리
            }
        } else {
            // 해당 ID의 사용자를 찾을 수 없는 경우 처리
        }
    }


}