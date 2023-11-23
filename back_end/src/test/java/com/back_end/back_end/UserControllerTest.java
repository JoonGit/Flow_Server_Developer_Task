package com.back_end.back_end;

import com.back_end.back_end.Entity.User.UserEntity;
import com.back_end.back_end.Repository.User.UserRepository;
import com.back_end.back_end.dto.User.UserSaveDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

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
    public void SignupTest() throws Exception {
        //given

        String userId = "user2";
        String password = "qwer1234";


        UserSaveDto requestDto = UserSaveDto.builder()
                .userId(userId)
                .password(password)
                .build();

        String url = "http://localhost:" + port + "/user/signup";
        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        Optional<UserEntity> user = userRepository.findByUserId(userId);
        if(user.isPresent())
        {
            System.out.println("회원가입 성공");
        }
        else
        {
            System.out.println("회원가입 실패");
        }
    }

    @Test
    public void LoginTest() throws Exception {
        //given

        String userId = "user";
        String password = "qwer1234";


        UserSaveDto requestDto = UserSaveDto.builder()
                .userId(userId)
                .password(password)
                .build();

        String url = "http://localhost:" + port + "/user/login";
        //when
        Object message =  mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }


}

