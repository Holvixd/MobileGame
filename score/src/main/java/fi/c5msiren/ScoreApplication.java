package fi.c5msiren;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class for starting the spring boot application
 *
 * @author Miika
 * @version 2017.4.23
 * @since 1.8
 */
@SpringBootApplication
public class ScoreApplication {

	/**
     * Starts the spring boot application
     *
     * @param args not used
     */
	public static void main(String[] args) {
		SpringApplication.run(ScoreApplication.class, args);
	}
}
