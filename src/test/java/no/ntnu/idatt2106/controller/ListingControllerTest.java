// package no.ntnu.idatt2106.controller;

// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.sql.Connection;
// import java.sql.SQLException;
// import java.util.ArrayList;
// import java.util.List;

// import javax.sql.DataSource;

// import com.fasterxml.jackson.databind.ObjectMapper;

// import org.junit.Before;
// import org.junit.Test;
// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.core.io.ClassPathResource;
// import org.springframework.http.MediaType;
// import org.springframework.jdbc.datasource.init.ScriptUtils;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.web.context.WebApplicationContext;

// import io.restassured.RestAssured;
// import io.restassured.module.mockmvc.RestAssuredMockMvc;
// import org.springframework.mock.web.MockHttpServletResponse;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
// import no.ntnu.idatt2106.BocoApplication;
// import no.ntnu.idatt2106.exception.StatusCodeException;
// import no.ntnu.idatt2106.model.DAO.ListingDAO;
// import no.ntnu.idatt2106.model.DTO.ListingDTO;
// import no.ntnu.idatt2106.repository.CategoryRepository;
// import no.ntnu.idatt2106.repository.ListingCategoryRepository;
// import no.ntnu.idatt2106.repository.ListingRepository;
// import no.ntnu.idatt2106.service.CategoryService;
// import no.ntnu.idatt2106.service.ListingCategoryService;
// import no.ntnu.idatt2106.service.ListingService;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @RunWith(SpringJUnit4ClassRunner.class)
// @ActiveProfiles("test")
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BocoApplication.class)
// @AutoConfigureMockMvc

// public class ListingControllerTest {
//     ListingDTO listingDTO;
//     String[] categories;
//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     ListingRepository listingRepository;

//     @Autowired
//     ListingService listingService;

//     @Autowired
//     CategoryRepository categoryRepository;

//     @Autowired
//     CategoryService categoryService;

//     @Autowired
//     ListingCategoryRepository listingCategoryRepository;

//     @Autowired
//     ListingCategoryService listingCategoryService;

//     @Autowired
//     private WebApplicationContext webApplicationContext;

//     @BeforeAll
//     static void setup(@Autowired DataSource dataSource) throws SQLException {
//         try (Connection conn = dataSource.getConnection()) {
//             ScriptUtils.executeSqlScript(conn, new ClassPathResource("cleanupListing.sql"));
//             ScriptUtils.executeSqlScript(conn, new ClassPathResource("dataListing.sql"));
//         }
//     }

//     @Before
//     public void initialiseRestAssuredMockMvcWebApplicationContext() {
//         RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
//     }

//     @BeforeEach
//     void createListingDaos() {
//         // listingDTO = new ListingDTO("Jekk", "Beskrivelse", 4.0, "Adresse", 2022,
//         // categories);
//     }

//     @AfterAll
//     static void cleanup(@Autowired DataSource dataSource) throws SQLException {
//         try (Connection conn = dataSource.getConnection()) {
//             ScriptUtils.executeSqlScript(conn, new ClassPathResource("cleanupListing.sql"));
//         }
//     }

//     // @Test
//     // public void getAllListingsShouldBeOK() throws Exception{
//     //     mockMvc.perform(get("/api/listing").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
//     // }

//     /**
//      * Aint working
//      * @throws Exception
//      */
//     // @Test
//     // public void createListingShouldBeOK() throws Exception {
//     //     categories = new String[] { "Fotball", "Utstyr" };
//     //     mockMvc.perform(post("/api/listing")
//     //             .content(asJsonString(new ListingDTO("Jekk", "Beskrivelse", 4.0, "Adresse", 2022, categories)))
//     //             .contentType(MediaType.APPLICATION_JSON)
//     //             .accept(MediaType.APPLICATION_JSON))
//     //             .andExpect(status().isOk());
//     // }

//     @Test
//     public void checkReality() {
//         System.out.println(asJsonString(listingDTO));
//         assertTrue(true);
//     }

//     public static String asJsonString(final Object obj) {
//         try {
//             return new ObjectMapper().writeValueAsString(obj);
//         } catch (Exception e) {
//             throw new RuntimeException(e);
//         }
//     }

// }
