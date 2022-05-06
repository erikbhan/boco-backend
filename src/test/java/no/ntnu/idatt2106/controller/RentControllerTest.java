package no.ntnu.idatt2106.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.idatt2106.BocoApplication;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.RentDTO;
import no.ntnu.idatt2106.repository.RentRepository;
import no.ntnu.idatt2106.service.LoginService;
import no.ntnu.idatt2106.service.RentService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;


import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BocoApplication.class)
@AutoConfigureMockMvc
public class RentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    String userToken;
    UserDAO user;
    UserDAO user2;
    String user2Token;
    String ownerToken;

    @Autowired
    LoginService loginService;

    @Autowired
    RentService rentService;

    @Autowired
    RentRepository rentRepository;

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {

            ScriptUtils.executeSqlScript(conn, new ClassPathResource("rentData.sql"));
        }
    }

    @BeforeEach
    void login() throws ServletException, IOException {
        user = new UserDAO(2022, "test@email.com", "test", "user", "gløshaugen", "ok", "l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==", "Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==");
        userToken = loginService.successfulAuthentication(user);
        user2 = new UserDAO(10, "test@email.com", "test", "user", "gløshaugen", "ok", "l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==", "Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==");
        user2Token = loginService.successfulAuthentication(user2);
        user2 = new UserDAO(3034, "test@email.com", "test", "user", "gløshaugen", "ok", "l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==", "Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==");
        ownerToken = loginService.successfulAuthentication(user2);
    }

    @AfterAll
    static void cleanup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("cleanup.sql"));
        }
    }

    @Test
    void rentController_getRentHistoryOfUser_ShouldBeOk() throws Exception {
        mockMvc.perform(get("/user/profile/rent/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].*", hasSize(10)));
    }

    @Test
    void rentController_getRentHistoryOfUser_ShouldBe4xx() throws Exception {
        mockMvc.perform(get("/user/profile/rent/history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + user2Token))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void rentController_getFullRentHistoryOfOwner_ShouldBeOk() throws Exception {
        mockMvc.perform(get("/user/profile/rent/history/owner/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + ownerToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].*", hasSize(10)));
    }

    @Test
    void rentController_getFullRentHistoryOfOwner_ShouldBe4xx() throws Exception {
        mockMvc.perform(get("/user/profile/rent/history/owner/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + user2Token))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void rentController_getFullRentHistoryOfUser_ShouldBeOk() throws Exception {
        mockMvc.perform(get("/user/profile/rent/history/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].*", hasSize(10)));
    }

    @Test
    void rentController_getFullRentHistoryOfUser_ShouldBe4xx() throws Exception {
        mockMvc.perform(get("/user/profile/rent/history/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + user2Token))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void rentController_getRentHistoryOfOwner_ShouldBeOk() throws Exception {
        mockMvc.perform(get("/user/profile/rent/history/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + ownerToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].*", hasSize(10)));
    }

    @Test
    void rentController_getRentHistoryOfOwner_ShouldBe4xx() throws Exception {
        mockMvc.perform(get("/user/profile/rent/history/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + user2Token))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void rentController_saveRentingAgreementForRenter_ShouldBeOk() throws Exception {
        LocalDateTime ldt = LocalDateTime.of(1997, 12, 13, 12, 12, 12);
        mockMvc.perform(post("/renting/renter/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new RentDTO(rentService.fromLocalDateTimeToMillis(ldt), rentService.fromLocalDateTimeToMillis(ldt), false, 1235, 2022, false)))
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());
    }

    @Test
    void rentController_deleteRent_ShouldBeOk() throws Exception {
        mockMvc.perform(delete("/renting/10001/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + ownerToken))
                .andExpect(status().isOk());
    }

    @Test
    void rentController_deleteNonExistingRent_ShouldBe4xx() throws Exception {
        mockMvc.perform(delete("/api/renting/10002/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void rentController_acceptRent_ShouldBeOk() throws Exception {
        mockMvc.perform(put("/renting/10000/accept")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + ownerToken))
                .andExpect(status().isOk());
    }

    @Test
    void rentController_acceptNonExistingRent_ShouldBe4xx() throws Exception {
        mockMvc.perform(post("/api/renting/10002/accept")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
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
