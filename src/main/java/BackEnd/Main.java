package BackEnd;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;



/**
 * Created by User on 16.04.2017.
 */
@SpringBootApplication
@EnableAutoConfiguration
public class Main extends WebMvcConfigurerAdapter  {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
