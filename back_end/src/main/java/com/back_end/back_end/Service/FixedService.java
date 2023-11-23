package com.back_end.back_end.Service;


import com.back_end.back_end.Entity.FixedEntity;
import com.back_end.back_end.Entity.UserEntity;
import com.back_end.back_end.Repository.FixedRepository;
import com.back_end.back_end.Repository.UserRepository;
import com.back_end.back_end.Vo.CustomVo;
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

    public String Save(FixedSaveDto requestDto) {
        try {
            fixedRepository.save(requestDto.toEntity());
            return "success";
        } catch (Exception e) {
            return ("저장에 실패 하였습니다 : " + e);
        }

    }

    public String Delete(FixedSaveDto requestDto) {
        try {
            fixedRepository.delete(requestDto.toEntity());
            return "success";
        } catch (Exception e) {
            return ("삭제에 실패 하였습니다 : " + e);
        }
    }


    public String SaveFixedToUser(FixToUserDto requestDto) {
        // N : M 저장
        try {
            // N 을 찾는다
            Optional<UserEntity> userOptional = userRepository.findByUserId(requestDto.getUserId());

            if (userOptional.isPresent()) {
                // M을 찾는다
                Optional<FixedEntity> fixedOptional = fixedRepository.findByName(requestDto.getFixedName());

                if (fixedOptional.isPresent()) {
                    UserEntity user = userOptional.get();
                    FixedEntity Fixed = fixedOptional.get();
                    // N에 M을 넣어준다
                    user.getFixedExtensions().add(Fixed); // 사용자에게 확장 추가
                    // M에 N을 넣어준다
                    Fixed.getUsers().add(user); // 확장에 사용자 추가

                    // N을 저장한다
                    userRepository.save(user); // 사용자 저장
                    // M을 저장한다
                    fixedRepository.save(Fixed); // 확장 저장
                    return "success";

                } else {
                    // ID의 Fixed을 찾을 수 없는 경우 처리
                    return "올바른 고정 확장자가 아닙니다";
                }
            } else {
                // 사용자를 찾을 수 없는 경우 처리
                return "유저를 찾을 수 없습니다";
            }
        } catch (Exception e) {
            return ("저장에 실패 하였습니다 : " + e);
        }

    }

    public String DeleteFixedToUser(FixToUserDto requestDto) {
        // N : M 삭제
        try {
            // N 을 찾는다
            Optional<UserEntity> userOptional = userRepository.findByUserId(requestDto.getUserId());

            if (userOptional.isPresent()) {
                // M 을 찾는다
                Optional<FixedEntity> fixedOptional = fixedRepository.findByName(requestDto.getFixedName());

                if (fixedOptional.isPresent()) {
                    UserEntity user = userOptional.get();
                    FixedEntity entity = fixedOptional.get();
                    // N에 M을 삭제한다
                    user.getFixedExtensions().remove(entity);
                    // M에 N을 삭제한다
                    entity.getUsers().remove(user);

                    // N을 저장한다
                    userRepository.save(user);
                    // M을 저장한다
                    fixedRepository.save(entity);
                    return "success";
                } else {
                    // ID의 Fixed을 찾을 수 없는 경우 처리
                    return "올바른 고정 확장자가 아닙니다";
                }
            } else {
                // 사용자를 찾을 수 없는 경우 처리
                return "유저를 찾을 수 없습니다";
            }

        } catch (Exception e) {
            return ("삭제에 실패 하였습니다 : " + e);
        }

//        return "삭제에 실패 하였습니다";
    }

    public FixedVo GetFixedForUser(String userId) {
        try {
            // N:M 관계 Get
            // User를 찾는다
            Optional<UserEntity> userOptional = userRepository.findByUserId(userId);

            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();

                // User의 Fixed의 이름만 추출한다
                List<String> userFixNameList = user.getFixedExtensions().stream()
                        .map(FixedEntity::getName)
                        .collect(Collectors.toList());

                // Fixed를 모두 불러온다
                List<FixedEntity> Entity = fixedRepository.findAll();
                HashMap<String, Boolean> checkList = new HashMap<>();
                // Fixed를 HashMpa에 넣어준다
                for (FixedEntity entity : Entity) {
                    checkList.put(entity.getName(), false);
                }

                for (String name : userFixNameList) {
                    // user의 Fixed 목록에 있는 데이터가 checkList에 있을경우
                    // 해당 Fixed를 true로 만들어 준다
                    if (checkList.containsKey(name) && !checkList.get(name)) {
                        checkList.put(name, true);
                    }
                }
                List<String> keyList = new ArrayList<>(checkList.keySet());
                List<Boolean> valueList = new ArrayList<>(checkList.values());
                return FixedVo.builder().fixedName(keyList).fixedStatus(valueList).build();
            } else {
                String error = "정보를 찾을 수 없습니다";
                return FixedVo.builder().error(error).build();
            }
        } catch (Exception e) {
            return FixedVo.builder().error(e.getMessage()).build();
        }
    }
}

