package rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import rest.backthreads.Chiefer;
import rest.config.SpringUtil;
import rest.service.AllSevice;

@SpringBootApplication
@EnableScheduling
//@ServletComponentScan
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		SpringUtil.getBean(AllSevice.class).initDataBase();
		new Thread(new Chiefer()).start();
	}
}
