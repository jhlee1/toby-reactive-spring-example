package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_3.take_until;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;
import lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.DebugSubcriber;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {
//    takeUntilWithStopPredicateExample();
    takeUntilWithOtherExample();
  }

  private static void takeUntilWithStopPredicateExample() throws InterruptedException {
    Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .takeUntil(data -> data == 3);

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(4000L);
  }

  private static void takeUntilWithOtherExample() throws InterruptedException {
    Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .takeUntil(Flowable.timer(1000L, TimeUnit.MILLISECONDS));

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(4000L);
  }

}