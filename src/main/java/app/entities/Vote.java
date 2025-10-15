package app.entities;

import jakarta.persistence.*;

@Entity
@NamedQueries({@NamedQuery(name = "Vote.deleteAll", query = "DELETE FROM Vote")})
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer votes;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private Option option;
}
