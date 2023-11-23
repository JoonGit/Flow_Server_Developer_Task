package com.back_end.back_end;

import com.back_end.back_end.Entity.Custom.CustomEntity;
import com.back_end.back_end.Repository.Custom.CustomRepository;
import com.back_end.back_end.dto.Custom.CustomSaveDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CustomRepository customRepository;

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void SaveTest() throws Exception {
        //given
        String name = "custom1";
        String userId = "user";

            CustomSaveDto requestDto = CustomSaveDto.builder()
                    .name(name)
                    .userId(userId)
                    .build();

            String url = "http://localhost:" + port + "/custom/save";
            //when
            Object message =  mvc.perform(post(url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(new ObjectMapper().writeValueAsString(requestDto)))
                    .andExpect(status().isOk());
            //then
            Optional<CustomEntity> object = customRepository.findByNameOrderByName(name);
            if(object.isPresent())
            {
                System.out.println("저장 성공");
            }
            else
            {
                System.out.println("저장 실패");
            }
        }

    @Test
    public void DeleteTest() throws Exception {
        //given
        String name = "custom1";
        String userId = "user";

        CustomSaveDto requestDto = CustomSaveDto.builder()
                .name(name)
                .userId(userId)
                .build();

        String url = "http://localhost:" + port + "/custom/delete";
        //when
        Object message =  mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
        //then
        Optional<CustomEntity> object = customRepository.findByNameOrderByName(name);
        if(!object.isPresent())
        {
            System.out.println("삭제 성공");
        }
        else
        {
            System.out.println("삭제 실패");
        }
    }



}

