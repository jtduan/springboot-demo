package rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import rest.schedule.MyApplicationReadyEventListener;

@SpringBootApplication
@EnableScheduling
//@ServletComponentScan
public class Application {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);

//		SpringApplication.run(Application.class, args);
		app.addListeners(new MyApplicationReadyEventListener());
		app.run(args);
	}
}
