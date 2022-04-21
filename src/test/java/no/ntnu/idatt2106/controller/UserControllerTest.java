package no.ntnu.idatt2106.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.idatt2106.BocoApplication;
import no.ntnu.idatt2106.model.DTO.UserDTO;
import no.ntnu.idatt2106.repository.UserRepository;
import no.ntnu.idatt2106.service.UserService;
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

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BocoApplication.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @BeforeAll
    static void setup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("data.sql"));
        }
    }


    @AfterAll
    static void cleanup(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("cleanup.sql"));
        }
    }

    @Test
    void userController_getAUserFromUserId_ShouldBeOk() throws Exception {
        mockMvc.perform(get("/api/user/findUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new UserDTO("token","2022"))))
                .andExpect(status().isOk());
    }

    @Test
    void userController_getAUserFromUserId_ShouldGive4xxError() throws Exception {
        mockMvc.perform(get("/api/user/findUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new UserDTO("token","10"))))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void userController_getFullnameForAUser_ShouldGiveOk() throws Exception {
        mockMvc.perform(get("/api/user/fullname")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new UserDTO("token","2022"))))
                .andExpect(status().isOk());
    }

    @Test
    void userController_getFullnameForAUser_ShouldGive4xxError() throws Exception {
        mockMvc.perform(get("/api/user/fullname")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new UserDTO("token","10"))))
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
