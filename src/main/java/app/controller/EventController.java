package app.controller;

import app.daos.EventDAO;
import app.dtos.EventDTO;
import app.dtos.OptionDTO;
import app.entities.Event;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;

public class EventController {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private EventDAO eventDao;

    public void getAllevents(Context ctx) {

    }

    public Event createNewEvent(Context ctx) {
        EventDTO eventDTO = ctx.bodyAsClass(EventDTO.class);

        Event event = EventDTO.convertFromDTOToOpenEntity(eventDTO);
        Event eventFromDB = eventDao.create(event);

        return eventFromDB;
    }

    public void addOption(Context ctx) {
        OptionDTO optionDTO = ctx.bodyAsClass(OptionDTO.class);

    }
}
