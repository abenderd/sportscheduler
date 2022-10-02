package br.com.abenderd.sportscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("br.com.abenderd.sportscheduler.*")
@EntityScan("br.com.abenderd.sportscheduler.*")
public class SportschedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportschedulerApplication.class, args);
	}

}
