package app.daos;

import app.config.HibernateConfig;
import app.dtos.UserDTO;
import app.entities.User;

import app.exceptions.DaoException;
import app.exceptions.ValidationException;
import jakarta.persistence.EntityManagerFactory;



public class SecurityDAO extends AbstractDao<User, Integer> {
    EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static SecurityDAO instance;

    private SecurityDAO() {
        super(User.class);
    }

    public static SecurityDAO getInstance() {
        if (instance == null) {
            instance = new SecurityDAO();
        }
        return instance;
    }

    public UserDTO getVerifiedUser(String username, String password) throws ValidationException, DaoException {
        User user = findById(username);
        if (user == null) {
            throw new DaoException("Error reading object from db");
        }
        if (!user.verifyPassword(password)) {
            throw new ValidationException("Password does not match");
        }
        return new UserDTO(user.getUsername(), user.getPassword());
    }
}