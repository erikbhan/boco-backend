package no.ntnu.idatt2106.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.idatt2106.BocoApplication;
import no.ntnu.idatt2106.model.DTO.RegisterUserDTO;
import no.ntnu.idatt2106.repository.UserRepository;
import no.ntnu.idatt2106.service.LoginService;
import no.ntnu.idatt2106.service.UserService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = BocoApplication.class)
@WebAppConfiguration
public class RegisterControllerTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginService loginService;

    @Autowired
    MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    void setup(@Autowired DataSource dataSource) throws SQLException {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("registerData.sql"));
        }
    }

    @After
    void teardown(@Autowired DataSource dataSource) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("cleanup.sql"));
        }
    }

    /**
     * Makes sure that a correct register user request will give HTTP status code 200
     *
     * @throws Exception if a status code different from 200 is given
     */
    @Test
    void registerController_register_ShouldGive200OK() throws Exception {
        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new RegisterUserDTO("erna@solberg.no", "hackerman", "Erna", "Solberg", "Oslo"))))
                .andExpect(status().is2xxSuccessful());
    }

    /**
     * Assures that when given a in-use e-mail, a 400 Bad Request status code
     *
     * @throws Exception when test fails
     */
    @Test
    void registerController_register_ShouldGive400Error() throws Exception {
        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new RegisterUserDTO(
                        "erna@solberg.no", "hackerman",
                        "Erna", "Solberg", "Oslo"))));

        mvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new RegisterUserDTO(
                                "erna@solberg.no", "hackerman",
                                "Erna", "Solberg", "Oslo"))))
                .andExpect(status().is4xxClientError());
    }

    /**
     * Utility method to generate JSON-strings from objects
     *
     * @param obj the object to generate a a JSON-string from
     * @return a JSON-string of the object
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}