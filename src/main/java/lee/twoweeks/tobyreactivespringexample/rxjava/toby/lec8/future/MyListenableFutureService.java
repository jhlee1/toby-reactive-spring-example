package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec8.future;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Component
public class MyListenableFutureService {
  // Future로는 처리하지 못하는 Callback처리가 하고 싶다


  @Async
  public ListenableFuture<String> hello() throws InterruptedException {
    log.info("hello()");
    Thread.sleep(1000);
    return new AsyncResult<>("Hello");
  }
}
