package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec6;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by Joohan Lee on 2020/02/15
 * Publisher -> Data -> Subscriber
 * Publisher -> [Data1] -> Operator1 -> [Data2] -> Operator2 -> [Data3] -> Subscriber
 * 1. map (d1 -> f -> d2)
 * EX) pub -> d1 -> mapPub -> d2 -> logSub
 *                <- subscribe(logSub) - Upstream(Subcriber에서 Publisher로 올라가는 것)
 *                -> onSubscribe(s) - Downstream(Publisher에서 Subscriber로 내려가는 것)
 *                ->onNext
 *                ->onNext
 *                ->onComplete
 */

@Slf4j
public class PubSubMultiple {
  public static void main(String[] args) {
    List<Integer> iter = Stream.iterate(1, x -> x + 1).limit(10).collect(Collectors.toList());
    Publisher<Integer> pub = interPub(iter);

    //    Publisher<Integer> mapPub1 = mapPub(pub, s -> s * 10);
//    Publisher<Integer> mapPub2 = mapPub(mapPub1, s -> -s);
//    mapPub2.subscribe(logSub());


    // 값이 모두 오기를 기다렸다가 onComplete 받고 처리하기

//    Publisher<Integer> sumPub = sumPub(pub);
//    sumPub.subscribe(logSub());


    // Reduce 구현하기
    // 1, 2, 3, 4, 5
    // 초기값 -> (초기값, 받은값[0) -> 초기값 + 받은값[0] = 결과1
    // 결과1 -> (결과1, 받은값[1] -> 결과1 + 받은값[1] = 결과2
    // 0 -> (0,1) -> 0 + 1 = 1
    // 1 -> (1,2) -> 1 + 2 = 3
    // 3 -> (3,3) -> 3 + 3 = 6
    // 6 -> (6,4) -> 6 + 4 = 10
    // 10 -> (10,5) -> 10 + 5 = 15

//    Publisher<Integer> reducePub = reducePub(pub, 0, (a,b) -> a + b); //BiFunction<Integer, Integer, Integer> 을 구현
//    reducePub.subscribe(logSub());

    // Generic하게 mapPub 적용하기
    Publisher<String> mapPub = mapPub(pub, s -> "[" + s + "]");
    mapPub.subscribe(logSub());

    // Generic하게 reducePub 적용하기


  }

  public static Publisher<Integer> interPub(List<Integer> iter) {
    return new Publisher<Integer>() {
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
  }

  private static <T> Subscriber<T> logSub() {
    return new Subscriber<T>() {
      @Override
      public void onSubscribe(Subscription s) {
        log.info("onSubscribe:");
        s.request(Long.MAX_VALUE);
      }

      @Override
      public void onNext(T t) {
        log.info("On Next: {}", t);

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
  }

  // Upstream도 고려해서 subscription을 설계해야함
  private static <T, R> Publisher<R> mapPub(Publisher<T> publisher, Function<T, R> func) {
    return new Publisher<R>() {
      @Override
      public void subscribe(Subscriber<? super R> subscriber) {
        publisher.subscribe(new DelegateSub<T, R>(subscriber) { // 반복되는 부분은 DelegateSub으로 이동
          @Override
          public void onNext(T t) {
            subscriber.onNext(func.apply(t));
          }
        });
      }
    };
  }

//  private static Publisher<Integer> sumPub(Publisher<Integer> pub) {
//    return new Publisher<Integer>() {
//      @Override
//      public void subscribe(Subscriber<? super Integer> sub) {
//        pub.subscribe(
//            new DelegateSub(sub) {
//              int sum = 0;
//
//              @Override
//              public void onNext(Integer integer) {
//                sum += integer;
//              }
//
//              @Override
//              public void onComplete() {
//                sub.onNext(sum);
//                sub.onComplete();
//              }
//            });
//      }
//    };
//  }
//
//  private static Publisher<Integer> reducePub(Publisher<Integer> pub, int init, BiFunction<Integer, Integer, Integer> biFunction) {
//    return new Publisher<Integer>() {
//      @Override
//      public void subscribe(Subscriber<? super Integer> s) {
//        pub.subscribe(
//            new DelegateSub(s) {
//              int result = init;
//
//              @Override
//              public void onNext(Integer integer) {
//                result = biFunction.apply(result, integer);
//              }
//
//              @Override
//              public void onComplete() {
//                s.onNext(result);
//                s.onComplete();
//              }
//            });
//      }
//    };
//  }

    private static Publisher<Integer> reducePubGeneric(Publisher<Integer> pub, int init, BiFunction<Integer, Integer, Integer> biFunction) {
    return new Publisher<Integer>() {
      @Override
      public void subscribe(Subscriber<? super Integer> s) {
        pub.subscribe(
            new DelegateSub<Integer, Integer>(s) {
              int result = init;

              @Override
              public void onNext(Integer integer) {
                result = biFunction.apply(result, integer);
              }

              @Override
              public void onComplete() {
                s.onNext(result);
                s.onComplete();
              }
            });
      }
    };
  }


}
