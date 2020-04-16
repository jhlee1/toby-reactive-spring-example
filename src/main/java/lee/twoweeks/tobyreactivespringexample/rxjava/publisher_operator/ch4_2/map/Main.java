package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_2.map;

import io.reactivex.Flowable;
import lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.DebugSubcriber;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {

  public static void main(String[] args) {
    Flowable<String> flowable = Flowable.just("A", "B", "C", "D", "E")
        .map(String::toLowerCase);

    flowable.subscribe(new DebugSubcriber<>());
  }
}
