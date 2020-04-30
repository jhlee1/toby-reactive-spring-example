package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec11;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Joohan Lee on 2020/04/30
 */

@Slf4j
public class CFuture {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    // 이미 처리가 완료된 값을 CompletableFuture를 통해 넘겨줘서 get을 하면 바로 처리됨

//    CompletableFuture completedFuture = CompletableFuture.completedFuture(1);
//    System.out.println(completedFuture.get());

//    completedFuture가 완료된 적이 없기 때문에 get을 호출항 경우 값을 넣어줄 때까지 무한대기
//    CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
//    System.out.println(completableFuture.get());

    // complete으로 값을 넣어주기 때문에 정상적으로 완료됨
//    CompletableFuture<Integer> completableFutureWithComplete = new CompletableFuture<>();
//    completableFutureWithComplete.complete(2);
//    System.out.println(completableFutureWithComplete.get());

    // 비동기 thread 작업의 예외처리
//    CompletableFuture<Integer> completableFutureWithException = new CompletableFuture<>();
//    completableFutureWithComplete.completeExceptionally(new RuntimeException()); // 예외가 발생하는 시점에서 해당 예외를 가지고만 있음
//    System.out.println(completableFutureWithException.get()); // Get을 통해서 값을 가져오려고 할 때 throw되면서 예외 발생


    // 비동기 Thread에서 작업 생성
    // CompletableFuture는 CompletionStage를 구현하고 있음 (나중에 API 문서 자세히 읽어보기) - 하나의 액션에 의존하여 다음 액션을 정의하는 구조
    CompletableFuture.runAsync(() -> log.info("runAsync"))
        .thenRun(() -> log.info("thenRun1"))
        .thenRun(() -> log.info("thenRun2"));

    log.info("Exit");

    CompletableFuture
        .supplyAsync(() -> {
          log.info("runAsync");
//          if (true) throw new RuntimeException(); Exceptionally 에서 처리됨

          return 1;
        })
        .thenApply(i -> {
          log.info("thenRun {}", i);
          return ++i;
        })
        .thenApply(i -> {
          log.info("thenRun {}", i);
          return i * 3;
        })
        .thenCompose(i -> { // flatMap과 유사한 방식. CompletableFuture<CompletableFuture<Integer>가 리턴되는 경우를 방지하기 위
          log.info("thenRun {}", i);
          return CompletableFuture.completedFuture(i * 3);
        })
        .exceptionally(e -> -10) // 예외가 발생한 경우 그 아래 작업들을 수행하지 않고 이 부분으로 와서 처리됨
        .thenAccept(i -> log.info("thenRun {}", i));

    log.info("Exit");
    ForkJoinPool.commonPool().shutdown();
    ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);

    // 따로 ThreadPool을 지정해서 Async 작업 처리하기
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    CompletableFuture
        .supplyAsync(() -> {
          log.info("runAsync");
          return 1;
        })
        .thenCompose(i -> {
          log.info("thenApply {}", i);
          return CompletableFuture.completedFuture(i * 3);
        })
        .thenApplyAsync(i -> {
          log.info("thenApply {}", i);
          return i * 3;
        }, executorService)
        .exceptionally(e -> -10) // 예외가 발생한 경우 그 아래 작업들을 수행하지 않고 이 부분으로 와서 처리됨
        .thenAcceptAsync(i -> log.info("thenRun {}", i), executorService);

  }

}
