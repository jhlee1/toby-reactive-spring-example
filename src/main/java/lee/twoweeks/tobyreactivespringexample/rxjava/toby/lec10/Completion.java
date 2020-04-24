package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec10;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.function.Consumer;
import java.util.function.Function;

public class Completion<S, T> {
    Completion next;
    /* Accept와 Apply Completion으로 분리

    Consumer<ResponseEntity<String>> consumer;
    Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> function;

    public Completion() {
    }

    public Completion(Consumer<ResponseEntity<String>> consumer) {
        this.consumer = consumer;
    }
    public Completion(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> function) {
        this.function = function;
    }
*/

    public static <S,T> Completion<S,T> from(ListenableFuture<T> listenableFuture) {
        Completion completion = new Completion();
        listenableFuture.addCallback(response -> {
            completion.complete(response);
        }, exception -> {
            completion.error(exception);
        });

        return completion;
    }

    public Completion<T,T> andError(Consumer<Throwable> econ) {
        Completion<T, T> completion = new ErrorCompletion(econ);
        this.next = completion;

        return completion;
    }

    public void andAccept(Consumer<T> consumer) {
        Completion<T, Void> completion = new AcceptCompletion(consumer);
        this.next = completion;
    }

    public <V> Completion<T,V> andApply(Function<T, ListenableFuture<V>> function) {
        Completion completion = new ApplyCompletion(function);
        this.next = completion;

        return completion;
    }

    void error(Throwable exception) {
        if (next != null) next.error(exception);

    }

    void complete(T responseEntity) {
        if (next != null) next.run(responseEntity);
    }

    void run(S responseEntity) {
    }
}
