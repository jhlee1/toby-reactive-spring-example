package lee.twoweeks.tobyreactivespringexample.rxjava.publisher_operator;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by Joohan Lee on 2020/01/22
 */
public class DebugMaybeObserver implements MaybeObserver {
  private String label;

  public DebugMaybeObserver() {
  }

  public DebugMaybeObserver(String label) {
    this.label = label;
  }

  @Override
  public void onSubscribe(Disposable d) {

  }

  @Override
  public void onSuccess(Object data) {
    String threadName = Thread.currentThread().getName();

    if (label == null) {
      System.out.println(threadName + ": " + data);
    } else {
      System.out.println(threadName + ": " + label + ": " + data);
    }
  }

  @Override
  public void onError(Throwable e) {

  }

  @Override
  public void onComplete() {

  }
}
