package com.back_end.back_end.Service;


import com.back_end.back_end.Entity.FixedEntity;
import com.back_end.back_end.Entity.UserEntity;
import com.back_end.back_end.Repository.FixedRepository;
import com.back_end.back_end.Repository.UserRepository;
import com.back_end.back_end.Vo.FixedVo;
import com.back_end.back_end.dto.FixedSaveDto;
import com.back_end.back_end.dto.FixToUserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FixedService {
    private final UserRepository userRepository;

    private final FixedRepository fixedRepository;

    public FixedService(UserRepository userRepository, FixedRepository fixedRepository) {
        this.userRepository = userRepository;
        this.fixedRepository = fixedRepository;
    }
    public void Save(FixedSaveDto requestDto) {
        fixedRepository.save(requestDto.toEntity());
    }
    public void Delete(FixedSaveDto requestDto) {
        fixedRepository.delete(requestDto.toEntity());
    }

    public FixedVo GetFixedForUser(String userId) {
        Optional<UserEntity> userOptional = userRepository.findByUserId(userId);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();

            List<String> userFixNameList = user.getFixedExtensions().stream()
                    .map(FixedEntity::getName)
                    .collect(Collectors.toList());

            List<FixedEntity> Entity = fixedRepository.findAll();

            HashMap<String,Boolean> checkList = new HashMap<>();
            for (FixedEntity entity : Entity) {
                checkList.put(entity.getName(), false);
            }

            for (String name : userFixNameList) {
                // 기존 키가 있는지 확인하여 기존 값이 false인 경우에만 true로 업데이트
                if (checkList.containsKey(name) && !checkList.get(name)) {
                    checkList.put(name, true);
                }
            }
            List<String> keyList = new ArrayList<>(checkList.keySet());
            List<Boolean> valueList = new ArrayList<>(checkList.values());
            return FixedVo.builder().fixedName(keyList).fixedStatus(valueList).build();
        } else {
            // 사용자를 찾을 수 없는 경우 처리
            return null; // 빈 Set을 반환하거나 에러 처리를 수행할 수 있습니다.
        }
    }

    public void SaveFixedToUser(FixToUserDto requestDto) {
        Optional<UserEntity> userOptional = userRepository.findByUserId(requestDto.getUserId());

        if (userOptional.isPresent()) {
            Optional<FixedEntity> fixedOptional = fixedRepository.findByName(requestDto.getFixedName());

            if (fixedOptional.isPresent()) {
                UserEntity user = userOptional.get();
                FixedEntity Fixed = fixedOptional.get();

                user.getFixedExtensions().add(Fixed); // 사용자에게 확장 추가
                Fixed.getUsers().add(user); // 확장에 사용자 추가

                userRepository.save(user); // 사용자 저장
                fixedRepository.save(Fixed); // 확장 저장

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
            Optional<FixedEntity> fixedOptional = fixedRepository.findByName(requestDto.getFixedName());

            if (fixedOptional.isPresent()) {
                UserEntity user = userOptional.get();
                FixedEntity entity = fixedOptional.get();

                user.getFixedExtensions().remove(entity);
                entity.getUsers().remove(user);

                userRepository.save(user);
                fixedRepository.save(entity);
            } else {
                // 해당 ID의 확장을 찾을 수 없는 경우 처리
            }
        } else {
            // 해당 ID의 사용자를 찾을 수 없는 경우 처리
        }
    }


}