package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec8.async_servlet;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

@Slf4j
@RestController
public class MyController {
  Queue<DeferredResult<String>> results = new ConcurrentLinkedQueue<>();

  // 100개가 돌리면 100개의 nio thread가 생성됨
  // 임시 설정으로 thread 개수를 20으로 바꾸면 100개를 처리에 대한 response 시간이 5배가 걸림
  @GetMapping("sync")
  public String sync() throws InterruptedException {
    Thread.sleep(2000);
    return "hello";
  }


  // nio thread 개수가 줄어도 요청을 받고 바로 worker thread에 넘기고 새로운 요청을 받아서 처리하기 때문에 시간이 늘어나지 않음
  // 100개의 요청에 대해 nio thread가 한개라도 요청 처리 시간이 엄청 늘어나진 않음
  // But, 그만큼 worker thread(여기선 MvcAsync)개수가 늘어나기 때문에 사실 큰 의미가 없음
  @GetMapping("asyncCallable")
  public Callable<String> asyncCallable() throws InterruptedException {
    log.info("Callable");
    return () -> {
      log.info("Async");
      Thread.sleep(2000);
      return "hello";
    };
  }


  // DeferredResult - Reactive 직전까지 가장 최신 비동기 기술
  // (앞으로 줄) 값들을 저장해두고 외부의 이벤트(요청)에 의해서 값의 일부나 전부를 반환
  // 장점 - 각 요청마다 Worker Thread를 따로 생성하지 않고 처리가 가능하다.
  @GetMapping("asyncDeferredResultQueue")
  public DeferredResult<String> asyncDeferredResultQueue() {
    log.info("dr");
    DeferredResult<String> dr = new DeferredResult<>();

    results.add(dr);

    return dr;
  }

  @GetMapping("/asyncDeferredResultQueue/count")
  public String drCount() {
    return String.valueOf(results.size());
  }

  @GetMapping("/asyncDeferredResultQueue/event")
  public String drevent(String msg) {
    for (DeferredResult<String> dr : results) {
      dr.setResult("Hello" + msg); // Response를 보내도록 Trigger 역할
      results.remove(dr);
    }

    return "OK";
  }


  // Emitter 사용하기 - 데이터를 한꺼번에 모아서 response로 보내는게 아니라 여러개로 쪼개서 보내는 기술
  // ResponseBodyEmitter, SseEmitter, StreamingResponseBody
  // SSE 스트리밍 방식으로 Response를 보냄
  @GetMapping("/emitter")
  public ResponseBodyEmitter emitter(String msg) {
    ResponseBodyEmitter emitter = new ResponseBodyEmitter();

    Executors.newSingleThreadExecutor().submit(() ->{
      for (int i = 0; i < 50; i++) {
        try {
          emitter.send("<p>Stream " + i + "</p>");
          Thread.sleep(100);
        } catch (IOException | InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    return emitter;
  }
}
