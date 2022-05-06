package no.ntnu.idatt2106.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.idatt2106.BocoApplication;
import no.ntnu.idatt2106.model.DAO.RatingDAO;
import no.ntnu.idatt2106.model.DAO.UserDAO;
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
import org.springframework.test.web.servlet.MvcResult;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("ratingData.sql"));
        }
    }

    @AfterAll
    static void cleanup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("cleanup.sql"));
        }
    }

    @Test
    void ratingController_getAllRatingsAsRenterForUser_ShouldBeOK() throws Exception {
        mockMvc.perform(get("/rating/4321/renter").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void ratingController_getAllRatingsAsRenterForNonExistingUser_ShouldBe4xx() throws Exception {
        mockMvc.perform(get("/rating/3000/renter").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
    }

    @Test
    void ratingController_getAllRatingsAsOwnerForUser_ShouldBeOK() throws Exception {
        mockMvc.perform(get("/rating/4321/owner").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void ratingController_getAllRatingsAsOwnerForNonExistingUser_ShouldBe4xx() throws Exception {
        mockMvc.perform(get("/rating/3000/owner").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
    }

    @Test
    void ratingController_getAverageRatingsAsOwnerForUser_ShouldBeOK() throws Exception {
        mockMvc.perform(get("/rating/4321/average/owner").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void ratingController_getAverageRatingsAsOwnerForUserAsNonExistingUser_ShouldBe4xx() throws Exception {
        mockMvc.perform(get("/rating/3000/average/owner").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
    }

    @Test
    void ratingController_getAverageRatingsAsRenterForUser_ShouldBeOK() throws Exception {
        mockMvc.perform(get("/rating/4321/average/renter").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void ratingController_getAverageRatingsAsRenterForUserAsNonExistingUser_ShouldBe4xx() throws Exception {
        mockMvc.perform(get("/rating/3000/average/renter").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
    }

    @Test
    void ratingController_postNewRating_ShouldBeOK() throws Exception {
        user = new UserDAO(2022, "test@email.com", "test", "user", "gløshaugen", "ok", "l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==", "Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==");
        userToken = loginService.successfulAuthentication(user);
        mockMvc.perform(post("/rating/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new RatingDTO(6789123, 1, "work bro????", true, 10001)))
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isCreated());

        List<RatingDAO> ratings = ratingService.findRatingsByRentID(10001);
        assert (ratings.get(ratings.size() - 1).getComment().equals("work bro????"));
    }

    @Test
    void ratingController_postRatingFromUserThatHasAlreadyRatedRent_ShouldBe4xx() throws Exception {
        user = new UserDAO(2022, "test@email.com", "test", "user", "gløshaugen", "ok", "l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==", "Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==");
        userToken = loginService.successfulAuthentication(user);
        mockMvc.perform(post("/rating/save")
                        .content(asJsonString(new RatingDTO(98732, 1, "This fucking jekk man jesus christ it doesnt work bro????", false, 10002)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void ratingController_postRatingWithNonexistentRent_ShouldBe4xx() throws Exception {
        user = new UserDAO(2022, "test@email.com", "test", "user", "gløshaugen", "ok", "l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==", "Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==");
        userToken = loginService.successfulAuthentication(user);
        mockMvc.perform(post("/rating/save")
                        .content(asJsonString(new RatingDTO(9876, 1, "This fucking jekk man jesus christ it doesnt work bro????", false, 777777)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void ratingController_postRatingWithScore6_ShouldBe4xx() throws Exception {
        user = new UserDAO(2022, "test@email.com", "test", "user", "gløshaugen", "ok", "l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==", "Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==");
        userToken = loginService.successfulAuthentication(user);
        mockMvc.perform(post("/rating/save")
                        .content(asJsonString(new RatingDTO(9876, 6, "This fucking jekk man jesus christ it doesnt work bro????", false, 10002)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void ratingController_seeIfUserHasGivenRatingWhenUserHasGivenRating_ShouldBeTrue() throws Exception {
        user = new UserDAO(2022, "test@email.com", "test", "user", "gløshaugen", "ok", "l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==", "Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==");
        userToken = loginService.successfulAuthentication(user);
        MvcResult result = mockMvc.perform(get("/rating/10002/israted")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + userToken)).andReturn();

        boolean hasGivenRating = Boolean.parseBoolean(result.getResponse().getContentAsString());
        assert (hasGivenRating);
    }

    @Test
    void ratingController_seeIfUserHasGivenRatingWhenUserHasNotGivenRating_ShouldBeFalse() throws Exception {
        user = new UserDAO(2022, "test@email.com", "test", "user", "gløshaugen", "ok", "l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==", "Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==");
        userToken = loginService.successfulAuthentication(user);
        MvcResult result = mockMvc.perform(get("/rating/10000/israted")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + userToken)).andReturn();

        boolean hasGivenRating = Boolean.parseBoolean(result.getResponse().getContentAsString());
        assert (!hasGivenRating);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
