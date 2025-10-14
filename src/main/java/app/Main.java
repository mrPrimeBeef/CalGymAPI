package app;

import app.config.ApplicationConfig;
import app.config.HibernateConfig;
import app.rest.Routes;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {
//        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        //TODO setBaseProperties set to update
        //TODO check hibernateconfig getAnnotationConfiguration
        ApplicationConfig
                .getInstance()
                .initiateServer()
                .startServer(7070);
                // routes are added in startServer metode
    }
}