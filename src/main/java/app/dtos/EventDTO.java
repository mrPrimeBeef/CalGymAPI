package app.dtos;

import app.entities.Option;

import java.time.LocalDate;
import java.util.List;

public record EventDTO(String title, String description, List<Option> options, LocalDate startDate) {
}
