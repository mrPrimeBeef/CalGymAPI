package app.entities;

import app.entities.Enums.Open;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "events")
@NamedQueries({@NamedQuery(name = "Event.deleteAll", query = "DELETE FROM Event")})
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_username", referencedColumnName = "username")
    private User user;

    private String title;

    @Column(columnDefinition = "text")
    private String description;


    @OneToMany(mappedBy = "event", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
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

    public void addUser(User user){
        this.user = user;
    }
}