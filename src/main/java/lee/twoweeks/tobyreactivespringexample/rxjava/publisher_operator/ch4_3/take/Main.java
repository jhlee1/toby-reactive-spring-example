package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_3.take;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;
import lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.DebugSubcriber;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {
    takeExample();
  }


  private static void takeExample() throws InterruptedException {
    Flowable<Long> flowable = Flowable.interval(1000L, TimeUnit.MILLISECONDS)
        .take(3);

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(4000L);
  }
}