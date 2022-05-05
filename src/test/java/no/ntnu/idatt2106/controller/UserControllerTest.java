package no.ntnu.idatt2106.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.idatt2106.BocoApplication;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.repository.UserRepository;
import no.ntnu.idatt2106.service.*;
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
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BocoApplication.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    String userToken;
    UserDAO user;
    UserDAO user2;
    String user2Token;

    @Autowired
    UserService userService;

    @Autowired
    ListingService listingService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCommunityService userCommunityService;

    @Autowired
    RentService rentService;

    @Autowired
    LoginService loginService;

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("userData.sql"));

        }
    }

    @BeforeEach
    void login() throws ServletException, IOException {
        user = new UserDAO(2022, "test@email.com", "test", "user", "gløshaugen", "ok", "l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==", "Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==");
        userToken = loginService.successfulAuthentication(user);
        user2 = new UserDAO(10, "not@email.com", "test", "mcTester", "gløshaugen", "ok", "l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==", "Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==");
        user2Token = loginService.successfulAuthentication(user2);
    }


    @AfterAll
    static void cleanup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("cleanup.sql"));
        }
    }

    @Test
    void userController_getAUserFromUserId_ShouldBeOk() throws Exception {
        String expectedJson = "{\"userId\":2022,\"email\":\"test@email.com\",\"firstName\":\"test\",\"lastName\":\"user\",\"address\":\"kalvskinnet\",\"picture\":\"ok\"}";
        mockMvc.perform(get("/users/2022/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void userController_getAUserFromUserId_ShouldBe4xx() throws Exception {
        mockMvc.perform(get("/users/10/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + user2Token))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void userController_deleteUser_ShouldBeTrue() throws Exception {
        UserDAO user = userService.findUserByUserId(2023);
        userToken = loginService.successfulAuthentication(user);
        mockMvc.perform(delete("/user/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken));

        boolean listingIsDeleted = listingService.findListingByListingId(3000).isDeleted();
        boolean removedFromCommunities = userCommunityService.getAllCommunitiesForUser(user).isEmpty();
        boolean rentIsDeleted = rentService.getRentFromId(4000).isDeleted();
        boolean userIsCleared = userService.findUserByUserId(2023).getEmail().isEmpty();

        assert (listingIsDeleted && removedFromCommunities && rentIsDeleted && userIsCleared);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
