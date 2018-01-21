
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.example.controller","com.example.service"})


public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}