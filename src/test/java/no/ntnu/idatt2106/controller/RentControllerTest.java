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

import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Autowired
    LoginService loginService;

    @Autowired
    RentService rentService;

    @Autowired
    RentRepository rentRepository;

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("data.sql"));
        }
    }

    @BeforeEach
    void login() throws ServletException, IOException {
        user = new UserDAO(2022,"test@email.com", "test", "user", "gløshaugen", "ok", "l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==", "Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==");
        userToken = loginService.successfulAuthentication(user);
    }

    @AfterAll
    static void cleanup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("cleanup.sql"));
        }
    }

    @Test
    void rentController_getRentHistoryOfUser_ShouldBeOk() throws Exception {
        mockMvc.perform(get("/users/2022/profile/rent/userHistory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());
    }

    @Test
    void rentController_getRentHistoryOfUser_ShouldGive4xxError() throws Exception {
        mockMvc.perform(get("/users/10/profile/rent/userHistory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void rentController_getFullRentHistoryOfOwner_ShouldBeOk() throws Exception {
        mockMvc.perform(get("/users/3034/profile/rent/userHistory/owner/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());
    }

    @Test
    void rentController_getFullRentHistoryOfOwner_ShouldGive4xxError() throws Exception {
        mockMvc.perform(get("/users/10/profile/rent/userHistory/owner/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void rentController_getFullRentHistoryOfUser_ShouldBeOk() throws Exception {
        mockMvc.perform(get("/users/2022/profile/rent/userHistory/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());
    }

    @Test
    void rentController_getFullRentHistoryOfUser_ShouldGive4xxError() throws Exception {
        mockMvc.perform(get("/users/10/profile/rent/userHistory/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void rentController_getRentHistoryOfOwner_ShouldBeOk() throws Exception {
        mockMvc.perform(get("/users/3034/profile/rent/userHistory/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());
    }

    @Test
    void rentController_getRentHistoryOfOwner_ShouldGive4xxError() throws Exception {
        mockMvc.perform(get("/users/10/profile/rent/userHistory/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void rentController_saveRentingAgreementForRenter_ShouldBeOk() throws Exception {
        mockMvc.perform(post("/renting/renter/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new RentDTO(new Date(2013,8,23),new Date(2016,6,4),false,1235,2022)))
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());
    }

    @Test
    void rentController_deleteRent_ShouldBeOk() throws Exception{
        mockMvc.perform(put("/renting/10001/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + userToken))
        .andExpect(status().isOk());
    }

    @Test
    void rentController_deleteNonExistingRent_ShouldBe4xx() throws Exception {
        mockMvc.perform(put("/api/renting/10002/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void rentController_acceptRent_ShouldBeOk() throws Exception {
        mockMvc.perform(put("/renting/10000/accept")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());
    }

    @Test
    void rentController_saveRentingAgreementForRenter_ShouldGive4xx() throws Exception {
        mockMvc.perform(post("/renting/renter/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new RentDTO(new Date(2013,8,23),new Date(2016,6,4),false,1235,2022)))
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());
    }
    @Test
    void rentController_acceptNonExistingRent_ShouldBe4xx() throws  Exception {
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
