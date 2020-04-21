package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec6;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by Joohan Lee on 2020/02/15
 *
 * 6회 Operator
 */


@Slf4j
public class PubSub {
  public static void main(String[] args) {
    Publisher<Integer> publisher = new Publisher<Integer>() {
      Iterable<Integer> iter = Stream.iterate(1, x -> x + 1).limit(10).collect(Collectors.toList());

      @Override
      public void subscribe(Subscriber<? super Integer> subscriber) {
        subscriber.onSubscribe(new Subscription() {
          @Override
          public void request(long n) { // 요청 개수랑 상관없이 다 보냄
            try {
              iter.forEach(i -> subscriber.onNext(i));
              subscriber.onComplete();
            } catch (Throwable t) {
              subscriber.onError(t);
            }
          }

          @Override
          public void cancel() {

          }
        });
      }
    };

    Subscriber<Integer> subscriber = new Subscriber<Integer>() {
      @Override
      public void onSubscribe(Subscription s) {
        log.info("onSubscribe:");
        s.request(Long.MAX_VALUE);
      }

      @Override
      public void onNext(Integer integer) {
        log.info("On Next: {}", integer);

      }

      @Override
      public void onError(Throwable t) {
        log.error("On Error: ", t);
      }

      @Override
      public void onComplete() {
        log.debug("On Complete");
      }
    };

    publisher.subscribe(subscriber);
  }


}
