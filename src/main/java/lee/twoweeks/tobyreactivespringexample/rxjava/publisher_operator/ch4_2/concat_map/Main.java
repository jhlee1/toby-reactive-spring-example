package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_2.concat_map;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;
import lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.DebugSubcriber;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {
    concatMapDelayErrorExample();
//    concatMapExample();
  }

  private static void concatMapExample() throws InterruptedException {
    Flowable<String> flowable = Flowable.range(10, 3)
        .concatMap(sourceData ->
            Flowable.interval(500L, TimeUnit.MILLISECONDS)
                .take(2)
                .map(data -> {
                  long time = System.currentTimeMillis();
                  return String.format("%s ms | sourceData: %s | data: %s", time, sourceData, data);
                }));

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(4000L);
  }

  private static void concatMapDelayErrorExample() throws InterruptedException {
    Flowable<String> flowable = Flowable.range(10, 3)
        .concatMapDelayError(sourceData ->
            Flowable.interval(500L, TimeUnit.MILLISECONDS)
                .take(3)
                .doOnNext(data -> {
                      if (sourceData == 11 && data == 1) {
                        throw new Exception("Exception!");
                      }
                    }
                ).map(data -> String.format("sourceData: %s | data: %s", sourceData, data)), 1, false);

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(6000L);
  }
}