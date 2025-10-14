package app.entities;

import app.entities.Enums.Open;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    @Column(columnDefinition = "text")
    private String description;

    @OneToMany
    private List<Option> optionList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Open open;

    public Event() {
    }

    public Event(String title, String description, List<Option> optionList, Open open) {
        this.title = title;
        this.description = description;
        this.optionList = optionList;
        this.open = open;
    }
}