package no.ntnu.idatt2106.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.idatt2106.BocoApplication;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.CommunityDTO;
import no.ntnu.idatt2106.model.DTO.ListingDTO;
import no.ntnu.idatt2106.model.DTO.RatingDTO;
import no.ntnu.idatt2106.service.LoginService;
import no.ntnu.idatt2106.service.RatingService;
import org.junit.jupiter.api.*;
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
import javax.transaction.TransactionScoped;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BocoApplication.class)
@AutoConfigureMockMvc
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    String userToken;
    UserDAO user;

    @Autowired
    LoginService loginService;

    @Autowired
    private RatingService ratingService;

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("ratingCleanup.sql"));
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("ratingData.sql"));
        }
    }

    @AfterAll
    static void cleanup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("ratingCleanup.sql"));
        }
    }

    @Test
    public void getAllRatingsAsRenterForUser_ShouldBeOK() throws Exception {
        mockMvc.perform(get("/rating/4321/as_renter").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void getAllRatingsAsRenterForNonExistingUser_ShouldBe4xx() throws Exception {
        mockMvc.perform(get("/rating/3000/as_renter").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
    }

    @Test
    public void getAllRatingsAsOwnerForUser_ShouldBeOK() throws Exception {
        mockMvc.perform(get("/rating/4321/as_owner").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void getAllRatingsAsOwnerForNonExistingUser_ShouldBe4xx() throws Exception {
        mockMvc.perform(get("/rating/3000/as_owner").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
    }

    @Test
    public void getAverageRatingsAsOwnerForUser_ShouldBeOK() throws Exception {
        mockMvc.perform(get("/rating/4321/average_as_owner").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void getAverageRatingsAsOwnerForUserAsNonExistingUser_ShouldBe4xx() throws Exception {
        mockMvc.perform(get("/rating/3000/average_as_owner").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
    }

    @Test
    public void getAverageRatingsAsRenterForUser_ShouldBeOK() throws Exception {
        mockMvc.perform(get("/rating/4321/average_as_renter").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void getAverageRatingsAsRenterForUserAsNonExistingUser_ShouldBe4xx() throws Exception {
        mockMvc.perform(get("/rating/3000/average_as_renter").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
    }

    @Order(1)
    @Test
    public void postNewRating_ShouldBeOK() throws Exception {
        user = new UserDAO(2022,"test@email.com", "test", "user", "gløshaugen", "ok", "l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==", "Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==");
        userToken = loginService.successfulAuthentication(user);
        mockMvc.perform(post("/rating/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new RatingDTO(696969,1, "work bro????", false, 10001)))
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isCreated());
    }

    @Order(2)
    @Test
    public void postRatingWithNonexistentRent_ShouldBe4xx() throws Exception {
        user = new UserDAO(2022,"test@email.com", "test", "user", "gløshaugen", "ok", "l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==", "Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==");
        userToken = loginService.successfulAuthentication(user);
        mockMvc.perform(post("/rating/save")
                        .content(asJsonString(new RatingDTO(9876,1, "This fucking jekk man jesus christ it doesnt work bro????", false, 777777)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void seeIfUserHasGivenRatingWhenUserHasGivenRating_ShouldBeOK() throws Exception {
        user = new UserDAO(2022,"test@email.com", "test", "user", "gløshaugen", "ok", "l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==", "Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==");
        userToken = loginService.successfulAuthentication(user);
        mockMvc.perform(get("/rating/10002/israted")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());
    }

    @Test
    public void seeIfUserHasGivenRatingWhenUserHasNotGivenRating_ShouldBe4xx() throws Exception {
        user = new UserDAO(2022,"test@email.com", "test", "user", "gløshaugen", "ok", "l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==", "Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==");
        userToken = loginService.successfulAuthentication(user);
        mockMvc.perform(get("/rating/10000/israted")
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
