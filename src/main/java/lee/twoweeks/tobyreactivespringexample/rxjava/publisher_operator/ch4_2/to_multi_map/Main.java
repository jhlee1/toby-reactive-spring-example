package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_2.to_multi_map;

import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.DebugSingleObserver;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {
    toMultiMapWithKeySelectorExample();
  }


  private static void toMultiMapWithKeySelectorExample() throws InterruptedException {
    Single<Map<String, Collection<Long>>> single = Flowable.interval(500L, TimeUnit.MILLISECONDS)
    .take(5)
    .toMultimap(data -> {
      if (data % 2 == 0) {
        return "Even";
      } else {
        return "Odd";
      }
    });

    single.subscribe(new DebugSingleObserver());

    Thread.sleep(3000L);
  }

  private static void toMapWithKeySelectorValueSelectorExample() {
    Single<Map<Long, String>> single = Flowable.just("1A", "2B", "3C", "1D", "2E")
        .toMap(data -> Long.valueOf(data.substring(0, 1)), data -> data.substring(1));

    single.subscribe(new DebugSingleObserver());
  }
}