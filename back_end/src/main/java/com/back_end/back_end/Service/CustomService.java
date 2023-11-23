package com.back_end.back_end.Service;


import com.back_end.back_end.Entity.CustomEntity;
import com.back_end.back_end.Entity.UserEntity;
import com.back_end.back_end.Repository.CustomRepository;
import com.back_end.back_end.Repository.UserRepository;
import com.back_end.back_end.Vo.CustomVo;
import com.back_end.back_end.dto.CustomDeleteDto;
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
    public String Save(CustomSaveDto requestDto) {
        // 1 : N 관계의 저장
        try{
            // 1.저장하는 1의 관계의 데이터를 찾는다

            Optional<UserEntity> optionalUser = userRepository.findByUserId(requestDto.getUserId());
            // 2.N 데이터에 대응하는 1에 정보를 넣고 저장한다
            CustomEntity custom = CustomEntity.builder()
                    .name(requestDto.getName())
                    .user(optionalUser.get())
                    .build();
            customRepository.save(custom);
            return "success";
        }catch (Exception e) {
            return ("데이터 추가에 실패하였습니다 : " + e);
        }

    }
    public String Delete(CustomDeleteDto requestDto) {
        // 1 : N 관계의 삭제
        try{
            // 1.저장하는 1의 관계의 데이터를 찾는다
            Optional<UserEntity> optionalUser = userRepository.findByUserId(requestDto.getUserId());
            UserEntity user = optionalUser.get();

            // 2.1의 데이터를 가지고 있는 N 데이터를 찾는다
            CustomEntity customToDelete = user.getCustomExtensions()
                    .stream()
                    .filter(extension -> extension.getName().equals(requestDto.getName()))
                    .findFirst()
                    .orElse(null);
            // 3.1의 데이터의 있는 N 데이터를 삭제후 저장한다
            user.getCustomExtensions().remove(customToDelete);
            userRepository.save(user);
            return "success";
        }
        catch (Exception e) {
            return ("데이터 삭제에 실패 하였습니다 : " + e);
        }
    }
    public CustomVo GetCustomForUser(String userId) {
        try{
            // User의 정보를 찾는다
            Optional<UserEntity> userOptional = userRepository.findByUserId(userId);

            // User의 정보가 존재 한다면
            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                // User가 가지고 있는 Custom의 이름만 저장하여 전송한다
                List<String> customNameList = user.getCustomExtensions().stream()
                        .map(CustomEntity::getName).sorted()
                        .collect(Collectors.toList());
                return CustomVo.builder().customName(customNameList).build();
            }
            else {
                String error = "정보를 찾을 수 없습니다";
                return CustomVo.builder().error(error).build();}
        }
        catch (Exception e) {
            return CustomVo.builder().error(e.getMessage()).build();
        }
    }
}