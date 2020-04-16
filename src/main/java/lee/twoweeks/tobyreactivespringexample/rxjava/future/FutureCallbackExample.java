package lee.twoweeks.tobyreactivespringexample.rxjava.future;

/**
 * Created by Joohan Lee on 2020/04/16
 */

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FutureCallbackExample {
  interface SuccessCallback {
    void onSuccess(String result);
  }

  interface ExceptionCallback {
    void onError(Throwable t);
  }

  public static class CallbackFutureTask extends FutureTask<String> {
    SuccessCallback successCallback;
    ExceptionCallback exceptionCallback;

    public CallbackFutureTask(Callable<String> callable, SuccessCallback successCallback, ExceptionCallback exceptionCallback) {
      super(callable);
      this.successCallback = Objects.requireNonNull(successCallback); // Null인 경우 NullPointerException을 발생
      this.exceptionCallback = Objects.requireNonNull(exceptionCallback);
    }

    @Override
    protected void done() {
      try {
        successCallback.onSuccess(get());
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      } catch (ExecutionException e) {
        exceptionCallback.onError(e.getCause());
      }
    }
  }
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