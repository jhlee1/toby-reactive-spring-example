package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec8;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Slf4j
@EnableAsync
public class TobyReactiveLec8Application {
	public static void main(String[] args) {
//		try(ConfigurableApplicationContext c = SpringApplication.run(TobyReactiveSpringExampleApplication.class, args)) { };
		SpringApplication.run(TobyReactiveLec8Application.class, args);
	}
}
