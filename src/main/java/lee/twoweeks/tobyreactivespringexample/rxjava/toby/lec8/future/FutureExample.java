package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec8.future;


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

// 이 예제 방식에선 FutureTask를 계속 만들어 줘야하는 불편함 ... Callback으로 처리하기

// 여기까지 배운 비동기 작업의 결과를 가져오는 방법
// Future - 값을 가져올 때 blocking해서 결과를 받을 때까지 대기하도록 함. 내부에서 Exception이 발생할 경우 처리를 위해 try catch문이 필요하다
// Callback - 위에 Future와 비슷하지만 exception를 callback으로 처리함
// Ex. AsynchronousByteChannel의 read

// 여기까지 코드의 문제점
// 비지니스 로직과 기술적인 코드가 혼재되어 있음 -> ExecutorService와 FutureTask를 생성하는 기술적인 코드를 따로 분리할 필요가 있음
//
