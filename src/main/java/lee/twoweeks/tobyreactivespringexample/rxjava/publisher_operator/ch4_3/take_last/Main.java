package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_3.take_last;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;
import lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.DebugSubcriber;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {
//    takeLastWithCountExample();
    takeLastWithTimeAndUnitExample();
  }

  private static void takeLastWithCountExample() throws InterruptedException {
    Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .take(5)
        .takeLast(2);

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(2000L);
  }

  private static void takeLastWithTimeAndUnitExample() throws InterruptedException {
    Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .take(10)
        .takeLast(3, 300L, TimeUnit.MILLISECONDS);

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(4000L);
  }
}