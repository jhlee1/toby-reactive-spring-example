package lee.twoweeks.tobyreactivespringexample.spring_reactive.load_test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class LoadTest {
  private static final AtomicInteger counter = new AtomicInteger(0);
// Java Virtual VM으로 JVM 모니터링
  public static void main(String[] args) throws InterruptedException {
    ExecutorService es = Executors.newFixedThreadPool(100);

    RestTemplate restTemplate = new RestTemplate();
//    String url = "http://localhost:8080/sync";
    String url = "http://localhost:8080/asyncCallable";

    StopWatch main = new StopWatch();

    main.start();

    for (int i = 0; i < 100; i++) {
      es.execute(() -> {
        int idx = counter.addAndGet(1);
        log.info("Thread {}", idx);

        StopWatch thread = new StopWatch();

        thread.start();

        restTemplate.getForObject(url, String.class);

        thread.stop();
        log.info("Elapsed: {} {}", idx, thread.getTotalTimeSeconds() );
      });
    }

    es.shutdown();
    es.awaitTermination(100, TimeUnit.SECONDS); // shutdown이 불러지고 비동기 작업들이 다 마무리 될 때까지 100초 기다려준다. 100초동안 안 끝나면 그냥 넘어가버림

    main.stop();
    log.info("Total: {}", main.getTotalTimeSeconds());
  }
}
