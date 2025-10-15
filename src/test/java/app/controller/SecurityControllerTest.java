package app.controller;

import app.config.ApplicationConfig;
import app.config.HibernateConfig;
import app.daos.SecurityDAO;
import app.entities.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

class SecurityControllerTest {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private final String TEST_USER = "testuser";
    private final String TEST_PASSWORD = "password123";


    @BeforeAll
    static void setUpAll() {
        SecurityDAO securityDAO = SecurityDAO.getInstance();
        SecurityController securityController = new SecurityController();

        ApplicationConfig
                .getInstance()
                .initiateServer()
                .handleException()
                .startServer(7079);
        RestAssured.baseURI = "http://localhost:7079/api";
    }


    @BeforeEach
    void setUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            em.createNamedQuery("Vote.deleteAll").executeUpdate();
            em.createNamedQuery("Option.deleteAll").executeUpdate();
            em.createNamedQuery("Event.deleteAll").executeUpdate();
            em.createNamedQuery("User.deleteAll").executeUpdate();

            // Create test user with user role
            User testUserAccount = new User(TEST_USER, TEST_PASSWORD);
            em.persist(testUserAccount);

            em.getTransaction().commit();
        }
    }

    @Test
    void login() {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("username", TEST_USER);
        loginRequest.put("password", TEST_PASSWORD);

        given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("username", equalTo(TEST_USER));
    }

    @Test
    void register() {
        Map<String, String> registerRequest = new HashMap<>();
        registerRequest.put("username", "test2");
        registerRequest.put("password", "badpassword");

        given()
                .contentType(ContentType.JSON)
                .body(registerRequest)
                .when()
                .post("/auth/register")
                .statusCode()
                .body("username", equalTo(registerRequest.get("username")));
    }
}