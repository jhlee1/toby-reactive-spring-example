package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_3.distinct_until_changed;

import io.reactivex.Flowable;
import lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.DebugSubcriber;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {
  public static void main(String[] args) {
    distinctUntilChangedExample();
    distinctUntilChangedWithKeySelectorExample();
    distinctUntilChangedWithComparerExample();
  }

  private static void distinctUntilChangedExample() {
    Flowable<String> flowable = Flowable.just("A", "B", "A", "A", "B", "B")
        .distinctUntilChanged();

    flowable.subscribe(new DebugSubcriber<>());
  }

  private static void distinctUntilChangedWithKeySelectorExample() {
    Flowable<String> flowable = Flowable.just("A", "a", "B", "b", "A", "a", "B", "b")
        .distinctUntilChanged(data -> data.toLowerCase());

    flowable.subscribe(new DebugSubcriber<>());
  }

  private static void distinctUntilChangedWithComparerExample() {
    Flowable<String> flowable = Flowable.just("A", "a", "B", "b", "A", "a", "B", "b")
        .distinctUntilChanged((data1, data2) -> data1.equalsIgnoreCase(data2));

    flowable.subscribe(new DebugSubcriber<>());
  }
}