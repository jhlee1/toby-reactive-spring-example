package lee.twoweeks.tobyreactivespringexample.spring_reactive.async_servlet;

import java.util.concurrent.Callable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MyController {

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

  @GetMapping("asyncDeferredResultQueue")
  public void asyncDeferredResultQueue() {
    //TODO: Deferred Result Queue부터 노트 참조 + 2:03:13 부터
  }
}
