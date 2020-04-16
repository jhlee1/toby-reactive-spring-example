package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_2.flat_map;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;
import lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.DebugSubcriber;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {
  public static void main(String[] args) throws InterruptedException {
    flatMapWithErrorAndCompleteExample();
  }

  public static void flatMapExample() {
    Flowable<String> flowable = Flowable.just("A", "B", "C")
        .flatMap(data -> {
          if ("".equals(data)) {
            return Flowable.empty();
          } else {
            return Flowable.just(data.toLowerCase());
          }
        });

    flowable.subscribe(new DebugSubcriber<>());
  }

  public static void flatMapWithCombinerExample() throws InterruptedException {
    Flowable<String> flowable = Flowable.range(1, 3)
        .flatMap(data -> {
              return Flowable.interval(100L, TimeUnit.MILLISECONDS)
                  .take(3);
            },
            (sourceData, newData) -> String
                .format("sourceData: %s | newData: %s", sourceData, newData)
        );

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(1000L);
  }

  public static void flatMapWithErrorAndCompleteExample() {
    Flowable<Integer> flowable = Flowable.just(1, 2, 0, 4, 5)
        .map(data -> 10 / data)
        .flatMap(
            Flowable::just,
            error ->Flowable.just(-1),
            () -> Flowable.just(100)
        );

    flowable.subscribe(new DebugSubcriber<>());
  }
}
