package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_3.throttle_first;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;
import lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.DebugSubcriber;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {
  public static void main(String[] args) throws InterruptedException {
    throttleFirstWithTimeAndUnit();
  }

  private static void throttleFirstWithTimeAndUnit() throws InterruptedException {
    Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .take(10)
        .throttleFirst(1000L, TimeUnit.MILLISECONDS);

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(3000L);
  }
}