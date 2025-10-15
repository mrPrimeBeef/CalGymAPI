package app.controller;

import app.daos.EventDAO;
import app.daos.SecurityDAO;
import app.dtos.EventDTO;
import app.dtos.OptionDTO;
import app.entities.Event;

import app.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class EventController {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private EventDAO eventDao;
    private SecurityDAO securityDao;

    public EventController() {
        this.eventDao = EventDAO.getInstance();
        this.securityDao = SecurityDAO.getInstance();
    }

    public void getAllevents(Context ctx) {

    }

    public Event createNewEvent(Context ctx) {
        ObjectNode returnJson = objectMapper.createObjectNode();

        String userId = ctx.pathParam("userid");
        User user = securityDao.findById(userId);

        EventDTO eventDTO = ctx.bodyAsClass(EventDTO.class);

        Event event = EventDTO.convertFromDTOToOpenEntity(eventDTO);
        user.addToEventList(event);

        Event eventFromDB = eventDao.create(event);

        returnJson.put("event_id", eventFromDB.getId())
                .put("username", user.getUsername());
        ctx.status(HttpStatus.CREATED).json(returnJson);
        return eventFromDB;
    }

    public void addOption(Context ctx) {
        OptionDTO optionDTO = ctx.bodyAsClass(OptionDTO.class);

    }
}
