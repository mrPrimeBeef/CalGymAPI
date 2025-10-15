package app.controller;

import app.daos.EventDAO;
import app.daos.SecurityDAO;
import app.dtos.EventDTO;
import app.dtos.EventsDTO;
import app.dtos.OptionDTO;
import app.entities.Event;

import app.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class EventController {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private EventDAO eventDao;
    private SecurityDAO securityDao;

    public EventController() {
        this.eventDao = EventDAO.getInstance();
        this.securityDao = SecurityDAO.getInstance();
    }

    public EventsDTO getAllevents(Context ctx) {
        ObjectNode returnJson = objectMapper.createObjectNode();

        String userId = ctx.pathParam("userid");
        User user = securityDao.findById(userId);

        List<Event> eventList = eventDao.findAll();
        List<EventDTO> eventDTOList = new ArrayList<>();

        for (Event event: eventList){
            eventDTOList.add(EventDTO.convertFromEntityToDTO(event));
        }
        EventsDTO eventsDTO = new EventsDTO(eventDTOList);

        returnJson.put("EventsDTO", String.valueOf(eventsDTO))
                .put("username", user.getUsername());
        ctx.status(HttpStatus.OK).json(returnJson);

        return eventsDTO;
    }

    public EventDTO createNewEvent(Context ctx) {
        ObjectNode returnJson = objectMapper.createObjectNode();

        String userId = ctx.pathParam("userid");
        User user = securityDao.findById(userId);

        EventDTO eventDTO = ctx.bodyAsClass(EventDTO.class);

        Event event = EventDTO.convertFromDTOToEntity(eventDTO);
        user.addToEventList(event);

        Event eventFromDB = eventDao.create(event);
        EventDTO createdEventDTO = EventDTO.convertFromEntityToDTO(eventFromDB);

        returnJson.put("event_id", eventFromDB.getId())
                .put("username", user.getUsername());
        ctx.status(HttpStatus.CREATED).json(returnJson);
        return createdEventDTO;
    }

    public void addOption(Context ctx) {
        OptionDTO optionDTO = ctx.bodyAsClass(OptionDTO.class);

    }
}
