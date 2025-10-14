package app.daos;

import app.config.HibernateConfig;
import app.dtos.UserDTO;
import app.entities.Event;
import app.entities.User;
import app.exceptions.DaoException;
import app.exceptions.ValidationException;
import jakarta.persistence.EntityManagerFactory;


public class EventDAO extends AbstractDao<Event, Integer> {
    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static EventDAO instance;

    private EventDAO() {
        super(Event.class);
    }

    public static EventDAO getInstance() {
        if (instance == null) {
            instance = new EventDAO();
        }
        return instance;
    }

}