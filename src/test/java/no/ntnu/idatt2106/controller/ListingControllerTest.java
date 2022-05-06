package no.ntnu.idatt2106.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.idatt2106.BocoApplication;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.ListingDTO;
import no.ntnu.idatt2106.repository.CategoryRepository;
import no.ntnu.idatt2106.repository.ListingCategoryRepository;
import no.ntnu.idatt2106.repository.ListingRepository;
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
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BocoApplication.class)
@AutoConfigureMockMvc
public class ListingControllerTest {

    ListingDTO listingDTO;
    UserDAO user;
    String userToken;
    String[] categories;
    int[] communityIDs;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    LoginService loginService;

    @Autowired
    ListingService listingService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    RentService rentService;

    @Autowired
    ListingCategoryRepository listingCategoryRepository;

    @Autowired
    ListingCategoryService listingCategoryService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("listingData.sql"));
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
    void listingController_getAllListings_ShouldBeOK() throws Exception {
        mockMvc.perform(get("/listing").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());
    }

    @Test
    void listingController_createListing_shouldBeOK() throws Exception {
        categories = new String[]{"Fussball", "Utstyr"};
        communityIDs = new int[]{100001, 100002};
        mockMvc.perform(post("/listing")
                        .content(asJsonString(new ListingDTO("testjekk", "Beskrivelse", 4.0, "Adresse", 4321, categories, communityIDs)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());

        assert (listingService.getListingDTOByTitle("testjekk").size() == 1);
    }

    @Test
    void listingController_getAvailabilityOfListing_ShouldBeOk() throws Exception {
        mockMvc.perform(get("/listing/1234/availability").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());
    }

    @Test
    void listingController_getAvailabilityOfListingRemovesPastRent_ShouldBeOk() throws Exception {
        mockMvc.perform(get("/listing/12345/availability").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());
        assert (rentService.getRentFromId(10002).isDeleted());
    }

    @Test
    void listingController_getAvailabilityOfNonExistingListing_ShouldBe4xx() throws Exception {
        mockMvc.perform(get("/listing/987654321/availability").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void listingController_searchForListingWithExistingTitleInDB_ShouldBeOK() throws Exception {
        mockMvc.perform(get("/listing/title/Fisking").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk());
    }

    @Test
    void listingController_listingController_getAllPicturesForAListing_ShouldBeOk() throws Exception {
        mockMvc.perform(get("/listing/4040/pictures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].*", hasSize(2)));
    }

    @Test
    void listingController_getAllPicturesForAListing_ShouldBe4xx() throws Exception {
        mockMvc.perform(get("/listing/0/pictures")
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
