package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec6;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by Joohan Lee on 2020/02/28
 */
public class DelegateSub<T, R> implements Subscriber<T> {
  private Subscriber subscriber;

  public DelegateSub(Subscriber<? super R> subscriber) {
    this.subscriber = subscriber;
  }

  @Override
  public void onSubscribe(Subscription subscription) {
    subscriber.onSubscribe(subscription);
  }

  @Override
  public void onNext(T t) {
    subscriber.onNext(t);
  }

  @Override
  public void onError(Throwable t) {
    subscriber.onError(t);

  }

  @Override
  public void onComplete() {
    subscriber.onComplete();
  }
}
