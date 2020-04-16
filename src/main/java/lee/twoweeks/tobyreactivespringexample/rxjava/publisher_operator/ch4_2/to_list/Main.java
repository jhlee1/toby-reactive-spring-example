package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_2.to_list;

import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;
import lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.DebugSingleObserver;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {
    toListExample();
  }

  private static void toListExample() throws InterruptedException {
    Single<List<String>> single = Flowable.just("A", "B", "C", "D", "E")
        .toList();

    single.subscribe(new DebugSingleObserver());
  }
}