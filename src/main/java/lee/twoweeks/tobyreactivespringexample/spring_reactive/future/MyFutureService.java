package lee.twoweeks.tobyreactivespringexample.spring_reactive.future;

import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyFutureService {
  // 웹환경에서 n분 이상 오래 걸리는 작업을 처리해야할 때 말곤 이런 Async 처리를 할 일은 사실 많이 없다.
  // 이런 경우 이렇게 Request에 대해서 future.get으로 해당 thread 작업을 오랫동안 blocking하면 Http Request Timeout이 발생할 것이다
  // 두가지 방법이 있다.
  // 1. DB에 상태값을 저장해놓고 확인
  // 2. Http Session에 저장해서 return 해버리고 다음 Request에서 session값을 꺼내와서 처리되었는지 확인 & 결과값 받기

  // @Async annotation을 사용할 때 주의할 점! ThreadPool 설정을 정해놓지 않으면 매 요청마다 계속 Thread를 만들어버림. 한번 쓰고 바로 thread를 버리는데 thread를 생성할 때 CPU작업과 memory를 많이 필요하기 때문에 비효율적. 따로 config을 설정해야됨

//  @Async(value = "te") Thread Pool을 분리해서 사용하고 싶을 때 이런식으로 Executor를 정의해 줄 수 있음
  @Async
  public Future<String> hello() throws InterruptedException {
    log.info("hello()");
    Thread.sleep(1000);
    return new AsyncResult<>("Hello");
  }
}

