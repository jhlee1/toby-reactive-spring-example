package lee.twoweeks.tobyreactivespringexample.spring_reactive.lec9.async_rest_template;

import lee.twoweeks.tobyreactivespringexample.TobyReactiveSpringExampleApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Slf4j
public class TobyLec9Application {
    public static void main(String[] args) {
//		try(ConfigurableApplicationContext c = SpringApplication.run(TobyReactiveSpringExampleApplication.class, args)) { };
        SpringApplication.run(TobyLec9Application.class, args);
    }
}
