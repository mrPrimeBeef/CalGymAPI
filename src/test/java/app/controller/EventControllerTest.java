package app.controller;

import app.config.ApplicationConfig;
import app.config.HibernateConfig;
import app.daos.EventDAO;
import app.daos.SecurityDAO;
import app.dtos.EventDTO;
import app.dtos.EventsDTO;
import app.entities.Enums.Open;
import app.entities.Event;
import app.entities.Option;
import app.entities.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class EventControllerTest {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private ObjectMapper objectMapper = new ObjectMapper();

    private final String EVENT_TITLE = "TestTitle";
    private final String EVENT_DESCRIPTION = "TestDesciption";

    private final String EVENT_TITLE2 = "TestTitle2";
    private final String EVENT_DESCRIPTION2 = "TestDesciption2";

    private final LocalDateTime EVENT_DateTime1 = LocalDateTime.of(2025, 10, 15, 14, 30);
    private final LocalDateTime EVENT_DateTime2 = LocalDateTime.of(2025, 10, 16, 15, 30);
    private final Option EVENT_Option1 = new Option(EVENT_DateTime1);
    private final Option EVENT_Option2 = new Option(EVENT_DateTime2);

    private final String TEST_USER = "testuser";
    private final String TEST_PASSWORD = "password123";

    @BeforeAll
    static void setUpAll() {
        EventDAO eventDAO = EventDAO.getInstance();
        EventController eventController = new EventController();

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
            em.createNativeQuery("DELETE FROM users_events").executeUpdate();
            em.createNamedQuery("Event.deleteAll").executeUpdate();
            em.createNamedQuery("User.deleteAll").executeUpdate();

            // USERS
            User testUserAccount = new User(TEST_USER, TEST_PASSWORD);
            em.persist(testUserAccount);

            //EVENTS
            List<Option> optionList = new ArrayList<>();
            optionList.add(EVENT_Option1);
            optionList.add(EVENT_Option2);
            Event testEvent = new Event(EVENT_TITLE, EVENT_DESCRIPTION, optionList, Open.OPEN);
            Event testEvent2 = new Event(EVENT_TITLE2, EVENT_DESCRIPTION2, optionList, Open.OPEN);

            EventDTO testEventDTO = EventDTO.convertFromEntityToDTO(testEvent);
            EventDTO testEventDTO2 = EventDTO.convertFromEntityToDTO(testEvent2);

            testUserAccount.addToEventList(testEvent);
            testUserAccount.addToEventList(testEvent2);

            em.persist(testEvent);
            em.persist(testEvent2);

            em.getTransaction().commit();
        }
    }

    @Test
    void getAllevents(){
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("event/testuser")
                .then()
                .statusCode(200)
                .body("eventsDTO.size()", equalTo(2));
    }


    @Test
    void createNewEvent() throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());

        List<Option> optionList = new ArrayList<>();
        optionList.add(EVENT_Option1);
        optionList.add(EVENT_Option2);
        Event testEvent = new Event("Fresh Test Title", "Fresh Desciption", optionList, Open.OPEN);

        EventDTO testEventDTO = EventDTO.convertFromEntityToDTO(testEvent);
        String bodyJson = objectMapper.writeValueAsString(testEventDTO);

        given()
                .contentType(ContentType.JSON)
                .body(bodyJson)
                .when()
                .post("event/testuser")
                .then()
                .statusCode(201)
                .body("event_id", equalTo(3))
                .body("username", equalTo(TEST_USER));
    }
}