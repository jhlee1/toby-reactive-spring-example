package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_2.concat_map_eager;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;
import lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.DebugSubcriber;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {
//    concatMapEagerExample();
    concatMapEagerDelayErrorExample();
  }

  private static void concatMapEagerExample() throws InterruptedException {
    Flowable<String> flowable = Flowable.range(10, 3)
        .concatMapEager(sourceData ->
            Flowable.interval(500L, TimeUnit.MILLISECONDS)
                .take(2)
                .map(data -> {
                  long time = System.currentTimeMillis();
                  return String.format("%s ms | sourceData: %s | data: %s", time, sourceData, data);
                }));

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(4000L);
  }

  private static void concatMapEagerDelayErrorExample() throws InterruptedException {
    Flowable<String> flowable = Flowable.range(10, 3)
        .concatMapEagerDelayError(sourceData ->
            Flowable.interval(500L, TimeUnit.MILLISECONDS)
                .take(3)
                .doOnNext(data -> {
                      if (sourceData == 11 && data == 1) {
                        throw new Exception("Exception!");
                      }
                    }
                ).map(data -> String.format("sourceData: %s | data: %s", sourceData, data)), false);

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(5000L);
  }
}