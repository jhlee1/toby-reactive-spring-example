package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_3.throttleWithTimeout;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;
import lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.DebugSubcriber;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {
  public static void main(String[] args) throws InterruptedException {
    throttleLastWithTimeAndUnit();
  }

  private static void throttleLastWithTimeAndUnit() throws InterruptedException {
    Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .take(9)
        .throttleWithTimeout(500L, TimeUnit.MILLISECONDS);

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(3000L);
  }

  private static void debounce() throws InterruptedException {
    Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .take(9)
        .debounce(500L, TimeUnit.MILLISECONDS);

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(3000L);
  }
}