package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_3.distinct;

import io.reactivex.Flowable;
import lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.DebugSubcriber;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {
    distinctWithKeySelectorExample();
  }


  private static void distinctExample() throws InterruptedException {
    Flowable<String> flowable = Flowable.just("A", "a", "B", "b", "A", "a", "B", "b")
        .distinct();

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(3000L);
  }

  private static void distinctWithKeySelectorExample() throws InterruptedException {
    Flowable<String> flowable = Flowable.just("A", "a", "B", "b", "A", "a", "B", "b")
        .distinct(String::toLowerCase);

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(3000L);
  }
}