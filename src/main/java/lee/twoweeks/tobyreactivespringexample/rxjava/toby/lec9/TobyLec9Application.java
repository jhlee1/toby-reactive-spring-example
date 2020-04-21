package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@SpringBootApplication
public class TobyLec9Application {
    public static void main(String[] args) {
//		try(ConfigurableApplicationContext c = SpringApplication.run(TobyReactiveSpringExampleApplication.class, args)) { };
        SpringApplication.run(TobyLec9Application.class, args);
    }

    
    // Myservice.work가 thread 하나에서 동작하기 위한 설정
    @Bean
    public ThreadPoolTaskExecutor myThreadPool() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(1);
        threadPoolTaskExecutor.setMaxPoolSize(1);
        threadPoolTaskExecutor.initialize();

        return threadPoolTaskExecutor;
    }
}
