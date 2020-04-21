package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec9;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class TobyLec9Application {
    public static void main(String[] args) {
//		try(ConfigurableApplicationContext c = SpringApplication.run(TobyReactiveSpringExampleApplication.class, args)) { };
        SpringApplication.run(TobyLec9Application.class, args);
    }
}
