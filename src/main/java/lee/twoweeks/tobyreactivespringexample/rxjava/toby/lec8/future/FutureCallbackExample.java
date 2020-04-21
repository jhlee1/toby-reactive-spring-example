package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec8.future;

/**
 * Created by Joohan Lee on 2020/04/16
 */

import static java.util.Objects.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FutureCallbackExample {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();

    CallbackFutureTask callbackFutureTask = new CallbackFutureTask(() -> {
      Thread.sleep(2000);
      log.info("Async");
      return "Hello";
    },
        result -> log.info("Result:" + result),
        e -> log.info("Error: " + e.getMessage())
    );

    executorService.execute(callbackFutureTask);
    executorService.shutdown();

  }
}