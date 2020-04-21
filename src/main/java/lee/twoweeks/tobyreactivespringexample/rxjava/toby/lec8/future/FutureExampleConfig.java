package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec8.future;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

@Configuration
@Slf4j
public class FutureExampleConfig {
  @Autowired
  MyFutureService myFutureService;

  @Autowired
  MyListenableFutureService myListenableFutureService;

  @Bean
  ThreadPoolTaskExecutor threadPoolTaskExecutor() {
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

    // Thread가 core에 넣을 수 있는 개수 (여기선 10)을 넘기면 Queue에 들어가고 그 Queue를 넘기면 MaxPoolSize까지 Pool이 늘어나게 된다.
    // Thread가 11개 인 경우 Queue로 가고 Queue에 들어갈 수 있는 200개를 넘어가면 PoolSize가 늘어난다.

    threadPoolTaskExecutor.setCorePoolSize(10);
    threadPoolTaskExecutor.setMaxPoolSize(100);
    threadPoolTaskExecutor.setQueueCapacity(200);
    threadPoolTaskExecutor.setThreadNamePrefix("mythread");
//    threadPoolTaskExecutor.setTaskDecorator(); thread 전처리 설정
    threadPoolTaskExecutor.initialize();

    return threadPoolTaskExecutor;
  }

  @Bean
  ApplicationRunner run() {
    return args -> {
//			Future 쓰기
//			log.info("run()");
//			Future<String> future = myFutureService.hello();
//			log.info("exit: " + future.isDone());
//			log.info("result: " + future.get());

//			Listenable Future쓰기
      log.info("run()");
      ListenableFuture<String> listenableFuture = myListenableFutureService.hello();

      // callback으로 처리하면 future.get처럼 Async thread가 처리를 끝낼 때까지 blocking되지 않고 그냥 빠져나옴
      //TODO: CompletableFuture도 나중에 찾아보기
      listenableFuture
          .addCallback(s -> System.out.println(s), e -> System.out.println(e.getMessage()));
//			listenableFuture.cancel(true); cancel한다고 무조건 해당 작업이 중단되는 것은 아니다. interrupt를 받아서 처리하는 코드를 넣어줘야 실제 작업이 중단되지 따로 처리 안하면 그냥 interrupt받아도 무시됨
      log.info("exit");
    };
  }
}
