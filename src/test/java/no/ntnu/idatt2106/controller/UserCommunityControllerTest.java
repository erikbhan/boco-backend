package no.ntnu.idatt2106.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.idatt2106.BocoApplication;

import no.ntnu.idatt2106.model.DTO.UserCommunityDTO;
import no.ntnu.idatt2106.repository.CommunityRepository;

import no.ntnu.idatt2106.service.UserCommunityService;
import no.ntnu.idatt2106.service.UserService;
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
    MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;

    protected void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void userCommunityController_addUserToCommunity_ShouldGive200OK() throws Exception {
        mvc.perform(post("/addUserToCommunity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new UserCommunityDTO(1, 2))))
                .andExpect(status().is2xxSuccessful());
    }

    //needs to be performed after OK test at the moment
    public void userCommunityController_addUserToCommunityWhereUserAlreadyIsInCommunity_ShouldGive4xxError() throws Exception {
        mvc.perform(post("/addUserToCommunity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new UserCommunityDTO(1, 2))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void userCommunityController_addUserToCommunity_ShouldGive4xxError() throws Exception {
        mvc.perform(post("/addUserToCommunity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new UserCommunityDTO(1000, 30))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void userCommunityController_getCommunitiesForUser_ShouldGive200OK() throws Exception {
        mvc.perform(get("/getCommunitiesForUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new UserCommunityDTO(1, 2))))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    public void userCommunityController_getCommunitiesForUser_ShouldGive400Error() throws Exception {
        mvc.perform(get("/getCommunitiesForUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new UserCommunityDTO(1000, 2))))
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
