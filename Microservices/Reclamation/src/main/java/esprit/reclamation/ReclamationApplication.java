package esprit.reclamation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("esprit.reclamation.Repositories")
public class ReclamationApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReclamationApplication.class, args);
	}
}