package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_2.to_map;

import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.Map;
import lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.DebugSingleObserver;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {
//    toMapWithKeySelectorExample();
    toMapWithKeySelectorValueSelectorExample();
  }


  private static void toMapWithKeySelectorExample() {
    Single<Map<Long, String>> single = Flowable.just("1A", "2B", "3C", "1D", "2E")
        .toMap(data -> Long.valueOf(data.substring(0, 1)));

    single.subscribe(new DebugSingleObserver());
  }

  private static void toMapWithKeySelectorValueSelectorExample() {
    Single<Map<Long, String>> single = Flowable.just("1A", "2B", "3C", "1D", "2E")
        .toMap(data -> Long.valueOf(data.substring(0, 1)), data -> data.substring(1));

    single.subscribe(new DebugSingleObserver());
  }
}