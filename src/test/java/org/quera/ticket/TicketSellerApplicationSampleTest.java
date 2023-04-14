package org.quera.ticket;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quera.ticket.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class TicketSellerApplicationSampleTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TestDbService testDbService;

    @BeforeEach
    void beforeEach() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        testDbService.resetDatabase();
        entityManager.createNativeQuery("INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER')").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO roles (id, name) VALUES (2, 'ROLE_ADMIN')").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO users (id, username, password, balance) VALUES (1, 'admin', '$2a$10$TGTo2u0MIYRtEV32XczjXO1CnsE8vFMYDQ6Q7pgrdGfl5c4iuJ9fu', 0)").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO users (id, username, password, balance) VALUES (2, 'quera', '$2y$10$Jifn39810yQVUPrtdeCfq./qi/aVW.JFDFnn9rteiO2Lm6RSRLRQu', 1000)").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO user_roles (user_id, role_id) VALUES (1, 2)").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO user_roles (user_id, role_id) VALUES (2, 1)").executeUpdate();
    }

    @Test
    public void testPing() throws Exception {
        mvc.perform(get("/api/ping"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"message\":\"ok\"}"));
    }

    @Test
    public void testGetStadiumsWithoutAuthentication() throws Exception {
        insertStadium(1, "Al-Iran 1", 2001);
        insertStadium(2, "Al-Iran 2", 2002);
        insertStadium(3, "Al-Iran 3", 2003);
        mvc.perform(get("/api/stadiums"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Al-Iran 1")))
                .andExpect(jsonPath("$[0].capacity", is(2001)))
                .andExpect(jsonPath("$[0].length()", is(3)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Al-Iran 2")))
                .andExpect(jsonPath("$[1].capacity", is(2002)))
                .andExpect(jsonPath("$[1].length()", is(3)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("Al-Iran 3")))
                .andExpect(jsonPath("$[2].capacity", is(2003)))
                .andExpect(jsonPath("$[2].length()", is(3)));
    }

    @Test
    public void testGetStadiumsWithNormalUser() throws Exception {
        insertStadium(1, "Al-Iran 1", 2001);
        insertStadium(2, "Al-Iran 2", 2002);
        insertStadium(3, "Al-Iran 3", 2003);
        mvc.perform(get("/api/stadiums")
                        .header("Authorization", "Bearer " + getBearerToken("quera", "12345678")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Al-Iran 1")))
                .andExpect(jsonPath("$[0].capacity", is(2001)))
                .andExpect(jsonPath("$[0].length()", is(3)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Al-Iran 2")))
                .andExpect(jsonPath("$[1].capacity", is(2002)))
                .andExpect(jsonPath("$[1].length()", is(3)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("Al-Iran 3")))
                .andExpect(jsonPath("$[2].capacity", is(2003)))
                .andExpect(jsonPath("$[2].length()", is(3)));
    }

    @Test
    public void testGetStadiumsWithAdmin() throws Exception {
        insertStadium(1, "Al-Iran 1", 2001);
        insertStadium(2, "Al-Iran 2", 2002);
        insertStadium(3, "Al-Iran 3", 2003);
        mvc.perform(get("/api/stadiums")
                        .header("Authorization", "Bearer " + getBearerToken("admin", "admin")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Al-Iran 1")))
                .andExpect(jsonPath("$[0].capacity", is(2001)))
                .andExpect(jsonPath("$[0].length()", is(3)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Al-Iran 2")))
                .andExpect(jsonPath("$[1].capacity", is(2002)))
                .andExpect(jsonPath("$[1].length()", is(3)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("Al-Iran 3")))
                .andExpect(jsonPath("$[2].capacity", is(2003)))
                .andExpect(jsonPath("$[2].length()", is(3)));
    }

    private String getBearerToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateJwtToken(authentication);
    }

    private void insertStadium(int id, String name, int capacity) {
        entityManager.createNativeQuery(String.format("INSERT INTO stadiums (id, name, capacity) VALUES (%d, '%s', %d)", id, name, capacity)).executeUpdate();
    }
}
