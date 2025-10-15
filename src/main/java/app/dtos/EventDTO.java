package app.dtos;

import app.entities.Enums.Open;
import app.entities.Event;
import app.entities.Option;


import java.util.List;

public record EventDTO(String title, String description, List<Option> options, Open open) {

    public static Event convertFromDTOToEntity(EventDTO eventDTO) {
        return new Event(eventDTO.title, eventDTO.description, eventDTO.options, eventDTO.open);
    }

    public static EventDTO convertFromEntityToDTO(Event event) {
        return new EventDTO(event.getTitle(), event.getDescription(), event.getOptionList(), event.getOpen());
    }
}
