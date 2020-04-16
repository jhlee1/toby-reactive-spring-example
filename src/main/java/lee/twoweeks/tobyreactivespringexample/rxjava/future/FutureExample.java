package lee.twoweeks.tobyreactivespringexample.rxjava.future;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FutureExample {

  /**
   * Created by Joohan Lee on 2020/04/16
   */

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService es = Executors.newCachedThreadPool();

//    es.execute(() -> { // Runnable
//      try {
//        Thread.sleep(2000);
//      } catch (InterruptedException e) {}
//      log.info("Async");
//    });

    Future<String> f = es.submit(() -> { //Callable - Runnable과 다르게 return 할 수 있고 Exception을 throw한다.
      Thread.sleep(2000);
      log.info("Async");
      return "Hello";
    });

    log.info("" + f.isDone()); // false
    Thread.sleep(2100);
    log.info("" + f.isDone()); // true
    log.info(f.get()); // Async 작업이 끝날 때까지 Blocking 으로 기다림
    log.info("Exit");

    FutureTask<String> futureTask = new FutureTask<String>(() -> {// Future로 받기 전 처리할 작업을 객체로 선언
      Thread.sleep(2000);
      log.info("Async");
      return "Hello";
    });

    es.execute(futureTask); // 위에 es.execute (() -> ...) 으로 처리한 것과 같은 결과임

    FutureTask<String> futureTask2 = new FutureTask<String>(() -> {// Future로 받기 전 처리할 작업을 객체로 선언
      Thread.sleep(2000);
      log.info("Async");
      return "Hello";
    }) {
      @Override
      protected void done() {
        try {
          System.out.println(get());
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (ExecutionException e) {
          e.printStackTrace();
        }
      }
    };

    es.execute(futureTask2);
    es.shutdown(); // shutdown하지 않으면 ExecutorService가 계속 떠있어서 종료되지 않음
  }
}
