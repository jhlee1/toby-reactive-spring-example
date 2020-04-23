package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec10;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.function.Consumer;
import java.util.function.Function;

public class Completion {
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

    public static Completion from(ListenableFuture<ResponseEntity<String>> listenableFuture) {
        Completion completion = new Completion();
        listenableFuture.addCallback(response -> {
            completion.complete(response);
        }, exception -> {
            completion.error(exception);
        });

        return completion;
    }

    public void andAccept(Consumer<ResponseEntity<String>> consumer) {
        Completion completion = new AcceptCompletion(consumer);
        this.next = completion;
    }

    public Completion andApply(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> function) {
        Completion completion = new ApplyCompletion(function);
        this.next = completion;

        return completion;
    }

    void error(Throwable exception) {

    }

    void complete(ResponseEntity<String> responseEntity) {
        if (next != null) next.run(responseEntity);
    }

    void run(ResponseEntity<String> responseEntity) {
    }
}
