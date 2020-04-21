package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec8.future;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by Joohan Lee on 2020/04/21
 */
public class CallbackFutureTask extends FutureTask<String> {
  private SuccessCallback successCallback;
  private ExceptionCallback exceptionCallback;

  public CallbackFutureTask(Callable<String> callable, SuccessCallback sc, ExceptionCallback ec) {
    super(callable);
    successCallback = requireNonNull(sc); // Null인 경우 NullPointerException을 발생
    exceptionCallback = requireNonNull(ec);
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
