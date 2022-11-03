package oit.is.z0604.kaizi.janken2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class Janken2Application {

	public static void main(String[] args) {
		SpringApplication.run(Janken2Application.class, args);
	}

}
