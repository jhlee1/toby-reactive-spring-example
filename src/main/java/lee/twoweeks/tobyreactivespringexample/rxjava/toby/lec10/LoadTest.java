package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec10;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class LoadTest {
  private static final AtomicInteger counter = new AtomicInteger(0);
// Java Virtual VM으로 JVM 모니터링
  public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
    System.setProperty("logging.level.root", "INFO");
    ExecutorService es = Executors.newFixedThreadPool(100);

    RestTemplate restTemplate = new RestTemplate();

    String url = "http://localhost:8080/rest";

    // 동기화해서 순차적이 아니라 한꺼번에 100개를 처리해버리고 싶은 경우
    CyclicBarrier cyclicBarrier = new CyclicBarrier(101);

    StopWatch main = new StopWatch();

    main.start();

    for (int i = 0; i < 100; i++) {
      es.submit(() -> {
        int idx = counter.addAndGet(1);

          cyclicBarrier.await(); // await을 부르는 순간 초기 생성할 때 넣어준 값 (여기선 101)의 횟수만큼 불릴 때까지 현재 thread를 blocking한다

        log.info("Thread {}", idx);

        StopWatch thread = new StopWatch();

        thread.start();

        String res = restTemplate.getForObject(url, String.class, idx);

        thread.stop();
        log.info("Elapsed: {} {} / {}", idx, thread.getTotalTimeSeconds(), res);

        return null;
      });
    }
    cyclicBarrier.await(); // 101번째 불려서 실행됨

    es.shutdown();
    es.awaitTermination(100, TimeUnit.SECONDS); // shutdown이 불러지고 비동기 작업들이 다 마무리 될 때까지 100초 기다려준다. 100초동안 안 끝나면 그냥 넘어가버림

    main.stop();
    log.info("Total: {}", main.getTotalTimeSeconds());
  }
}
