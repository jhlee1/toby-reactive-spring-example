package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_3.filter;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;
import lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.DebugSubcriber;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {
    filterExample();
  }


  private static void filterExample() throws InterruptedException {
    Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .filter(data -> data % 2 == 0);

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(3000L);
  }
}