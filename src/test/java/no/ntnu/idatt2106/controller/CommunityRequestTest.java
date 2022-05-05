package no.ntnu.idatt2106.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.idatt2106.BocoApplication;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.CommunityRequestDTO;
import no.ntnu.idatt2106.service.CommunityRequestService;
import no.ntnu.idatt2106.service.LoginService;
import no.ntnu.idatt2106.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BocoApplication.class)
@AutoConfigureMockMvc
public class CommunityRequestTest {

    @Autowired
    private MockMvc mockMvc;
    String userToken;

    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @Autowired
    CommunityRequestService communityRequestService;

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("communityRequestData.sql"));
        }
    }

    @AfterAll
    static void cleanup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("cleanup.sql"));
        }
    }

    @Test
    void communityRequestController_sendRequestToPrivateCommunity_ShouldBeOk() throws Exception {
        UserDAO user = userService.findUserByUserId(2022);
        userToken = loginService.successfulAuthentication(user);

        mockMvc.perform(post("/communities/3000/private/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new CommunityRequestDTO("Hello, i want to join please")))
                .header("Authorization", "Bearer " + userToken)).andExpect(status().isOk());

        assert (communityRequestService.getRequestsForCommunity(3000).size() == 1);
    }

    @Test
    void communityRequestController_sendRequestToPublicCommunity_ShouldBe4xx() throws Exception {
        UserDAO user = userService.findUserByUserId(2022);
        userToken = loginService.successfulAuthentication(user);

        mockMvc.perform(post("/communities/3001/private/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new CommunityRequestDTO("Hello, i want to join please")))
                .header("Authorization", "Bearer " + userToken)).andExpect(status().is4xxClientError());

        assert (communityRequestService.getRequestsForCommunity(3001).isEmpty());
    }

    @Test
    void communityRequestController_sendRequestToAlreadyJoinedCommunity_ShouldBe4xx() throws Exception {
        UserDAO user = userService.findUserByUserId(2022);
        userToken = loginService.successfulAuthentication(user);

        mockMvc.perform(post("/communities/3002/private/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new CommunityRequestDTO("Hello, i want to join please")))
                .header("Authorization", "Bearer " + userToken)).andExpect(status().is4xxClientError());

        assert (communityRequestService.getRequestsForCommunity(3002).isEmpty());
    }

    @Test
    void communityRequestController_acceptCommunityRequestAsAdmin_ShouldBeOk() throws Exception {
        UserDAO user = userService.findUserByUserId(2022);
        userToken = loginService.successfulAuthentication(user);

        mockMvc.perform(post("/communities/3003/requests/?userId=2023")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + userToken)).andExpect(status().isOk());

        assert (communityRequestService.getRequestsForCommunity(3003).isEmpty());
    }

    @Test
    void communityRequestController_acceptCommunityRequestNotAsAdmin_ShouldBe4xx() throws Exception {
        UserDAO user = userService.findUserByUserId(2022);
        userToken = loginService.successfulAuthentication(user);

        mockMvc.perform(post("/communities/3004/requests/?userId=2023")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + userToken)).andExpect(status().is4xxClientError());

        assert (communityRequestService.getRequestsForCommunity(3004).size() == 1);
    }

    @Test
    void communityRequestController_removeOwnCommunityRequest_ShouldBeOk() throws Exception {
        UserDAO user = userService.findUserByUserId(2022);
        userToken = loginService.successfulAuthentication(user);

        mockMvc.perform(patch("/communities/3005/requests/remove")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + userToken)).andExpect(status().isOk());

        assert (communityRequestService.getRequestsForCommunity(3005).size() == 0);
    }

    @Test
    void communityRequestController_rejectCommunityRequestAsAdmin_ShouldBeOk() throws Exception {
        UserDAO user = userService.findUserByUserId(2022);
        userToken = loginService.successfulAuthentication(user);

        mockMvc.perform(post("/communities/3006/requests/?userId=2023")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + userToken)).andExpect(status().isOk());

        assert (communityRequestService.getRequestsForCommunity(3006).isEmpty());
    }

    @Test
    void communityRequestController_rejectCommunityRequestNotAsAdmin_ShouldBe4xx() throws Exception {
        UserDAO user = userService.findUserByUserId(2022);
        userToken = loginService.successfulAuthentication(user);

        mockMvc.perform(post("/communities/3007/requests/?userId=2023")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + userToken)).andExpect(status().is4xxClientError());

        assert (communityRequestService.getRequestsForCommunity(3007).size() == 1);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
