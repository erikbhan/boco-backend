package no.ntnu.idatt2106.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.idatt2106.BocoApplication;

import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.repository.CommunityRepository;

import no.ntnu.idatt2106.service.LoginService;
import no.ntnu.idatt2106.service.UserCommunityService;
import no.ntnu.idatt2106.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
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


import javax.servlet.ServletException;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BocoApplication.class)
@WebAppConfiguration
public class UserCommunityControllerTest {
    @Autowired
    UserCommunityService userCommunityService;
    @Autowired
    UserService userService;
    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    LoginService loginService;

    @Autowired
    MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    UserDAO user;
    String userToken;

    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @BeforeEach
    void login() throws ServletException, IOException {
        user = new UserDAO(2022,"test@email.com", "test", "user", "gløshaugen", "ok", "l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==", "Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==");
        userToken = loginService.successfulAuthentication(user);
    }
    @Before
    public void setUp1() throws Exception {
            mvc.perform(post("/communities/3/join")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + userToken));
    }
    @Test
    public void userCommunityController_addUserToCommunity_ShouldGive200OK() throws Exception {
        mvc.perform(post("/communities/2/join")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    //needs to be performed after OK test at the moment
    public void userCommunityController_addUserToCommunityWhereUserAlreadyIsInCommunity_ShouldGive4xxError() throws Exception {
        mvc.perform(post("/communities/3/join")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void userCommunityController_addUserToCommunity_ShouldGive4xxError() throws Exception {
        mvc.perform(post("/communities/200/join")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void userCommunityController_getCommunitiesForUser_ShouldGive200OK() throws Exception {
        mvc.perform(get("/user/communities")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().is2xxSuccessful());
    }

    /*
    @Test
    public void userCommunityController_getCommunitiesForUser_ShouldGive400Error() throws Exception {
        mvc.perform(get("/user/communities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new UserCommunityDTO(1000, 2))))
                .andExpect(status().is4xxClientError());
    } */

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
