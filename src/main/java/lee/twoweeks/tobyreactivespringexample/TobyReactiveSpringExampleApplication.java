package lee.twoweeks.tobyreactivespringexample;

import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@SpringBootApplication
@Slf4j
@EnableAsync
public class TobyReactiveSpringExampleApplication {



	public static void main(String[] args) {
//		try(ConfigurableApplicationContext c = SpringApplication.run(TobyReactiveSpringExampleApplication.class, args)) { };
		SpringApplication.run(TobyReactiveSpringExampleApplication.class, args);
	}



}
