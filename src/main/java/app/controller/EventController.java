package app.controller;

import app.daos.EventDAO;
import app.dtos.EventDTO;
import app.dtos.OptionDTO;
import app.entities.Event;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

public class EventController {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private EventDAO eventDao;

    public void getAllevents(Context ctx) {

    }

    public Event createNewEvent(Context ctx) {
        ObjectNode returnJson = objectMapper.createObjectNode();

        EventDTO eventDTO = ctx.bodyAsClass(EventDTO.class);

        Event event = EventDTO.convertFromDTOToOpenEntity(eventDTO);
        Event eventFromDB = eventDao.create(event);

        returnJson.put("event_id", event.getId());
        ctx.status(HttpStatus.OK).json(returnJson);

        return eventFromDB;
    }

    public void addOption(Context ctx) {
        OptionDTO optionDTO = ctx.bodyAsClass(OptionDTO.class);

    }
}
