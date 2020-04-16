package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_3.skip_until;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;
import lee.joohan.DebugSubcriber;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {
    skipUntilWithOther();
  }

  private static void skipUntilWithOther() throws InterruptedException {
    Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .skipUntil(Flowable.timer(1000L, TimeUnit.MILLISECONDS));

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(2000L);
  }
}