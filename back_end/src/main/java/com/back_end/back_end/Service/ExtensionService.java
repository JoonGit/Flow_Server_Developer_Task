package com.back_end.back_end.Service;


import com.back_end.back_end.Entity.ExtensionEntity;
import com.back_end.back_end.Repository.ExtensionRepository;
import com.back_end.back_end.Resource.ExtensionInfoVo;
import com.back_end.back_end.dto.ExtensionSaveDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExtensionService {
    private final ExtensionRepository extensionRepository;

    public ExtensionService(ExtensionRepository extensionRepository) {
        this.extensionRepository = extensionRepository;
    }

    public void TbSave(ExtensionSaveDto requestDto) {
        extensionRepository.save(requestDto.toEntity());
    }

    public List<ExtensionInfoVo> find() {
        List<ExtensionEntity> result = extensionRepository.findAll();
        List<ExtensionInfoVo> voList = result.stream().map(
                ExtensionEntity -> ExtensionInfoVo.builder()
                        .name(ExtensionEntity.getName())
                        .build()
        ).collect(Collectors.toList());
        return voList;
    }

}