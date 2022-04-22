package no.ntnu.idatt2106.controller;
import java.io.IOException;

import no.ntnu.idatt2106.BocoApplication;
import no.ntnu.idatt2106.model.DTO.LoginDTO;
import no.ntnu.idatt2106.model.DTO.TokenDTO;
import no.ntnu.idatt2106.service.LoginService;
import no.ntnu.idatt2106.service.UserService;
import org.apache.juli.logging.Log;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BocoApplication.class)
@WebAppConfiguration
public class LoginControllerTest {
        @Autowired
        MockMvc mvc;
        @Autowired
        WebApplicationContext webApplicationContext;


        protected void setUp() {
            mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }

    //Tests that 4xx error code is given when entering wrong email and password
    @Test
    public void loginController_login_ShouldGive4xxError() throws Exception {
        mvc.perform(post("/api/login/authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new LoginDTO("feilemail@email.com","detteeretdårligpassord"))))
                .andExpect(status().is4xxClientError());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





}
