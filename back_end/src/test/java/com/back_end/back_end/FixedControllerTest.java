package com.back_end.back_end;

import com.back_end.back_end.Entity.FixedExtensionEntity;
import com.back_end.back_end.Repository.FixedExtensionRepository;
import com.back_end.back_end.dto.FixExtensionSaveDto;
import com.back_end.back_end.dto.FixToUserDto;
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
public class FixedControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FixedExtensionRepository extensionRepository;

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

        String[] names = new String[]{ ".bat", ".cmd", ".com", ".cpl", ".exe", ".scr", ".js",};

        for (String name : names) {
            FixExtensionSaveDto requestDto = FixExtensionSaveDto.builder()
                    .name(name)
                    .build();

            String url = "http://localhost:" + port + "/fixed/save";
            //when
            Object message =  mvc.perform(post(url)
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content(new ObjectMapper().writeValueAsString(requestDto)))
                    .andExpect(status().isOk());

            //then
            Optional<FixedExtensionEntity> object = extensionRepository.findByName(name);
            if(object.isPresent())
            {
                System.out.println("저장 성공");
            }
            else
            {
                System.out.println("저장 실패");
            }
        }

    }
    @Test
    public void SaveUserToExtensionTest() throws Exception {
        //given

        String userId = "user";
        String extensionName = ".bat";


        FixToUserDto requestDto = FixToUserDto.builder()
                .userId(userId)
                .extensionName(extensionName)
                .build();

        String url = "http://localhost:" + port + "/fixed/savefixtouser";
        //when
        Object message =  mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

}

