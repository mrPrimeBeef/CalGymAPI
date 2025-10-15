package app.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({@NamedQuery(name = "Option.deleteAll", query = "DELETE FROM Option")})
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> voteList = new ArrayList<>();

    public Option(LocalDateTime time) {
        this.time = time;
    }

    public Option() {

    }

    public void addVote(Vote vote){
        this.voteList.add(vote);
    }

}