package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.ch4_2.buffer;

import io.reactivex.Flowable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator.DebugSubcriber;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class Main {

  public static void main(String[] args) throws InterruptedException {
//    bufferWithCountExample();
//bufferWithTimeExample();
//    bufferWithBoundaryIndicator();
    bufferWithBoundaryIndicatorSupplier();
//    bufferWithOpeningClosingIndicator();

  }

  private static void bufferWithCountExample() throws InterruptedException {
    Flowable<List<Long>> flowable = Flowable.interval(100L, TimeUnit.MILLISECONDS)
        .take(10)
        .buffer(3);

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(1000L);
  }

  private static void bufferWithTimeExample() throws InterruptedException {
    Flowable<List<Long>> flowable = Flowable.interval(100L, TimeUnit.MILLISECONDS)
        .take(10)
        .buffer(300L, TimeUnit.MILLISECONDS);

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(1200L);
  }

  private static void bufferWithBoundaryIndicator() throws InterruptedException {
    Flowable<List<Long>> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .take(7)
        .buffer(Flowable.interval(1000L, TimeUnit.MILLISECONDS));

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(4000L);
  }


  private static void bufferWithBoundaryIndicatorSupplier() throws InterruptedException {
    Flowable<List<Long>> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .take(7)
        .buffer(() -> Flowable.timer(200L, TimeUnit.MILLISECONDS));

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(6000L);
  }

  private static void bufferWithOpeningClosingIndicator() throws InterruptedException {
    Flowable<List<Long>> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
        .take(7)
        .buffer(Flowable.interval(0L, 900L, TimeUnit.MILLISECONDS), opening -> Flowable.interval(1000L, TimeUnit.MILLISECONDS));

    flowable.subscribe(new DebugSubcriber<>());

    Thread.sleep(6000L);
  }
}