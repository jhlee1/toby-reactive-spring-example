package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by Joohan Lee on 2020/01/21
 */
public class DebugSubcriber<T> extends DisposableSubscriber<T> {

  private String label;

  public DebugSubcriber() {
    super();
  }

  public DebugSubcriber(String label) {
    super();
    this.label = label;
  }

  @Override
  public void onNext(T data) {
    String threadName = Thread.currentThread().getName();

    if (label == null) {
      System.out.println(threadName + ": " + data);
    } else {
      System.out.println(threadName + ": " + label + ": " + data);
    }
  }

  @Override
  public void onError(Throwable throwable) {
    String threadName = Thread.currentThread().getName();

    if (label == null) {
      System.out.println(threadName + ": " + throwable);
    } else {
      System.out.println(threadName + ": " + label + ": " + throwable);
    }
  }

  @Override
  public void onComplete() {
    String threadName = Thread.currentThread().getName();

    if (label == null) {
      System.out.println(threadName + ": " + "완료");
    } else {
      System.out.println(threadName + ": " + label + ": " + "완료");
    }
  }
}
