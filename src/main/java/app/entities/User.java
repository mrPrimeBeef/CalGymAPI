package app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
@NamedQueries(@NamedQuery(name = "User.deleteAll", query = "DELETE FROM User"))
public class User {
    @Id
    @Column(unique = true)
    private String username;
    private String password;
    private Boolean loggedIn;

    @OneToMany
    private List<Event> eventList = new ArrayList<>();

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean verifyPassword(String pw) {
        return BCrypt.checkpw(pw, this.password);
    }
}
