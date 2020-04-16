package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_3.skip;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;
import lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.DebugSubcriber;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {
    skip();
  }

  private static void skip() throws InterruptedException {
    Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .skip(2);

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(1000L);
  }
}