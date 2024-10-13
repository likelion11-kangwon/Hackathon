package back.ailion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class AilionApplication {

	public static void main(String[] args) {
		SpringApplication.run(AilionApplication.class, args);
	}

}
