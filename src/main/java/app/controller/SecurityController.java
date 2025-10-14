package app.controller;

import app.daos.SecurityDAO;

import app.dtos.UserDTO;
import app.entities.User;
import app.exceptions.ApiException;
import app.exceptions.DaoException;
import app.exceptions.ValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;


public class SecurityController {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private SecurityDAO securityDAO;

    public SecurityController() {
        this.securityDAO = SecurityDAO.getInstance();
    }

    public void login(Context ctx) {
        ObjectNode returnJson = objectMapper.createObjectNode();
        try {
            UserDTO userInput = ctx.bodyAsClass(UserDTO.class);
            UserDTO verifiedUser = securityDAO.getVerifiedUser(userInput.username(), userInput.password());

            ctx.status(HttpStatus.OK).json(returnJson);
        } catch (DaoException | ValidationException e) {
            throw new ApiException(401, "Could not verify user", e);
        }
    }

    public void register(Context ctx) {
        ObjectNode returnJson = objectMapper.createObjectNode();
        try {
            UserDTO userInput = ctx.bodyAsClass(UserDTO.class);
            User user = new User(userInput.username(), userInput.password());

            try {
                if (securityDAO.findById(user.getUsername()) != null) {
                    ctx.status(400).json("Username already in use");
                }
            } catch (DaoException ignored) {
                // Exception intentionally ignored
            }

            User createdUserAccount = securityDAO.create(user);

            ctx.status(HttpStatus.OK).json(returnJson);
        } catch (DaoException e) {
            ctx.status(400).json("Somthing went wrong, Error happend or User already exists " + e.getMessage());
        }
    }
}