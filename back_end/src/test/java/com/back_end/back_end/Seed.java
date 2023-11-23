package com.back_end.back_end;

import com.back_end.back_end.Entity.CustomEntity;
import com.back_end.back_end.Repository.CustomRepository;
import com.back_end.back_end.dto.CustomSaveDto;
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
public class Seed {

    @LocalServerPort
    private int port;


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
    public void Seed() throws Exception {
        UserControllerTest userControllerTest = new UserControllerTest();
        FixedControllerTest fixedControllerTest = new FixedControllerTest();
        CustomControllerTest customControllerTest = new CustomControllerTest();

        userControllerTest.SignupTest();
        fixedControllerTest.SaveTest();


        }





}

