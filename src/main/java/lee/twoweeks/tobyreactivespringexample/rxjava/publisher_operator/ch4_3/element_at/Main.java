package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_3.element_at;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import java.util.concurrent.TimeUnit;
import lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.DebugMaybeObserver;


/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {
  public static void main(String[] args) throws InterruptedException {
    elementAtExample();
  }

  private static void elementAtExample() throws InterruptedException {
    Maybe<Long> maybe = Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .elementAt(3);

    maybe.subscribe(new DebugMaybeObserver());

    Thread.sleep(3000L);
  }
}