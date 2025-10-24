package app.rest;

import app.controller.EventController;
import app.controller.SecurityController;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;


public class Routes {
    private SecurityController securityController = new SecurityController();
    private EventController eventController = new EventController();

    public Routes() {
    }

    public void registerRoutes(Javalin app) {
        // Auth routes ->
        app.post("auth/login", (ctx) -> securityController.login(ctx));
        app.post("auth/register", (ctx) -> securityController.register(ctx));

        // Event routes ->
        app.post("event/{userid}", (ctx -> eventController.createNewEvent(ctx)));
        app.post("event/{eventid}/option", (ctx -> eventController.addOption(ctx)));
        app.get("event/{userid}", (ctx -> eventController.getAllevents(ctx)));

        // Healthcheck
        app.get("auth/health", this::healthCheck);
    }

    public void healthCheck(@NotNull Context ctx) {
        ctx.status(200).json("{Msg: API is up and running}");
    }
}