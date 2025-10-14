package app.controller;

import app.daos.EventDAO;
import app.dtos.EventDTO;
import app.dtos.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

public class EventController {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private EventDAO eventDao;

    public void getAllevents(Context ctx) {

    }

    public void createNewEvent(Context ctx) {
        EventDTO eventDTO = ctx.bodyAsClass(EventDTO.class);

    }
}
