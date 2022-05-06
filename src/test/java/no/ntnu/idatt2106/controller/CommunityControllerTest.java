package no.ntnu.idatt2106.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.idatt2106.BocoApplication;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.CommunityDTO;
import no.ntnu.idatt2106.repository.CommunityRepository;
import no.ntnu.idatt2106.service.CommunityService;
import no.ntnu.idatt2106.service.LoginService;
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
import java.sql.SQLException;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BocoApplication.class)
@AutoConfigureMockMvc
public class CommunityControllerTest {

    @Autowired
    private MockMvc mockMvc;
    String userToken;
    UserDAO user;

    @Autowired
    LoginService loginService;

    @Autowired
    CommunityService communityService;

    @Autowired
    CommunityRepository communityRepository;

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("communityData.sql"));
        }
    }

    @BeforeEach
    void login() throws ServletException, IOException {
        user = new UserDAO(2022, "test@email.com", "test", "user", "gløshaugen", "ok", "l/hjdIHi9Us2uJZ7MP/urY6ALjISdukPrN5sjpD7wTMEV+DnQkWzOF3qfnO6r2PnIQM6zP7ZcdEYh0Gdok8nFQ==", "Ge7Y9frKWdgKcAysHdYCIoOOsAcn9We3f2+C74xlc6kWQZn2scBE8sEf4iZezwsmG/KdeeEuspZD9Q4Ojt27Hg==");
        userToken = loginService.successfulAuthentication(user);
    }

    @AfterAll
    static void cleanup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("cleanup.sql"));
        }
    }

    @Test
    void communityController_showAllCommunities_ShouldBeOK() throws Exception {
        mockMvc.perform(get("/communities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].*", hasSize(6)));
    }

    @Test
    void communityController_showAllCommunitiesMatchingSearchTerm_ShouldBeOK() throws Exception {
        mockMvc.perform(get("/communities/search?search_word=MC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].*", hasSize(6)));
    }

    @Test
    void communityController_saveNewCommunity_ShouldBeOk() throws Exception {
        mockMvc.perform(post("/communities/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new CommunityDTO("AleksMCKlubb", "kul klubb", 0, "Opp og ned elgeseter gate midt på natten hele fukin tiden", "bilde")))
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());

        assert (communityService.findAllCommunityDAOWithContainingAGivenName("AleksMCKlubb").size() == 1);
    }

    @Test
    void communityController_deleteCommunityFromAdminUser_ShouldBeOk() throws Exception {
        mockMvc.perform(delete("/communities/9999/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());

        assert (communityService.findCommunityDAOByCommunityID(9999) == null);
    }

    @Test
    void communityController_getCommunity_ShouldBeOk() throws Exception {
        String expectedJson = "{\"communityId\":1001,\"name\":\"Det regner fisk\",\"description\":\"Fisk for folk\",\"visibility\":1,\"location\":\"Ravnkloa\",\"picture\":\"imagen a place ...with fish\"}";
        mockMvc.perform(get("/community/1001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void communityController_getCommunity_ShouldBe4xx() throws Exception {
        mockMvc.perform(get("/community/99999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void communityController_getAllListingsInACommunity_ShouldBeOk() throws Exception {
        mockMvc.perform(get("/community/4444/listings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].*", hasSize(8)));
    }

    @Test
    void communityController_getAllListingsInACommunity_ShouldBe4xx() throws Exception {
        mockMvc.perform(get("/community/99999/listings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void communityController_showAllCommunitiesMatchingSearchTerm_ShouldBe4xx() throws Exception {
        mockMvc.perform(get("/communities/search/?search_word=Oldboys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void communityController_deleteCommunityFromNotAdminUser_ShouldBe4xx() throws Exception {
        mockMvc.perform(delete("/communities/4001/remove")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void communityController_deleteCommunityFromNonMemberUser_ShouldBe4xx() throws Exception {
        mockMvc.perform(delete("/communities/4002/remove")
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
