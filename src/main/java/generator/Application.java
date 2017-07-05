package generator;

import generator.config.ContextConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication

@EnableAutoConfiguration
@EnableJpaRepositories
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        /*AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ContextConfiguration.class);
        ctx.refresh();
*/
    }

}