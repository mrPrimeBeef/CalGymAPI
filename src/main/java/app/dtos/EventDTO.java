package app.dtos;

import app.entities.Enums.Open;
import app.entities.Event;
import app.entities.Option;


import java.util.List;

public record EventDTO(String title, String description, List<Option> options) {

    public static Event convertFromDTOToOpenEntity(EventDTO eventDTO) {
        return new Event(eventDTO.title, eventDTO.description, eventDTO.options, Open.OPEN);
    }
}
