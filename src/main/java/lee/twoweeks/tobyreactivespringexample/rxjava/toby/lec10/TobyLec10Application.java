package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec10;

import io.netty.channel.nio.NioEventLoopGroup;
import lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec9.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;


@SpringBootApplication
public class TobyLec10Application {
    public static void main(String[] args) {
        SpringApplication.run(TobyLec10Application.class, args);
    }
}
