package se.uu.it.runestone.teamone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;


/**
 * The main application. Starts a server as well as a Spring instance.
 *
 * @author Åke Lagercrantz
 */
@SpringBootApplication
@IntegrationComponentScan
public class Application {
    public static void main(String[] args) {

        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        new Thread(new Server());
    }
}
