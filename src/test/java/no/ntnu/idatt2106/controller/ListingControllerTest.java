package no.ntnu.idatt2106.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.idatt2106.BocoApplication;
import no.ntnu.idatt2106.model.DAO.UserDAO;
import no.ntnu.idatt2106.model.DTO.ListingDTO;
import no.ntnu.idatt2106.model.DTO.UserDTO;
import no.ntnu.idatt2106.repository.CategoryRepository;
import no.ntnu.idatt2106.repository.ListingCategoryRepository;
import no.ntnu.idatt2106.repository.ListingRepository;
import no.ntnu.idatt2106.repository.UserRepository;
import no.ntnu.idatt2106.service.*;
import org.junit.Assert;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BocoApplication.class)
@AutoConfigureMockMvc
public class ListingControllerTest {

    ListingDTO listingDTO;
    String[] categories;
    int[] communityIDs;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    ListingService listingService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ListingCategoryRepository listingCategoryRepository;

    @Autowired
    ListingCategoryService listingCategoryService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("listingCleanup.sql"));
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("listingData.sql"));
        }
    }

    @AfterAll
    static void cleanup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("listingCleanup.sql"));
        }
    }

    @Test
    public void getAllListings_ShouldBeOK() throws Exception {
        mockMvc.perform(get("/listing").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }


    /**
     * Posting listing with all vallid fields. Should be good
     *
     * @throws Exception
     */
    @Test
    public void createListing_shouldBeOK() throws Exception {
        categories = new String[]{"Fussball", "Utstyr"};
        communityIDs = new int[]{100001, 100002};
        mockMvc.perform(post("/listing")
                        .content(asJsonString(new ListingDTO("Jekk", "Beskrivelse", 4.0, "Adresse", 4321, categories, communityIDs)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Posting Listing with invalid category. Should throw erro
     *
     * @throws Exception
     */
    @Test
    public void createListingWithNonExistingCategry_shouldThrow400error() throws Exception {
        categories = new String[]{"Salse", "Utstyr"};
        communityIDs = new int[]{1000, 1001};
        mockMvc.perform(post("/listing")
                        .content(asJsonString(new ListingDTO("Jekk", "Beskrivelse", 4.0, "Adresse", 2022, categories, communityIDs)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getAvailabilityOfListing() throws Exception {
        mockMvc.perform(get("/listing/1234/availability").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void getAvailabilityOfNonExistingListing() throws Exception {
        mockMvc.perform(get("/listing/987654321/availability").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
