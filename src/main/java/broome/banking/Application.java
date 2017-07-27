package broome.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@EnableAutoConfiguration
@SpringBootApplication
public class Application {

	public static void main(String[] args){
		ApplicationContext ctx =SpringApplication.run(Application.class, args);
	}
}
