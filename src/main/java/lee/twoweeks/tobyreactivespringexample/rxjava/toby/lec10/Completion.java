package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec10;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.function.Consumer;
import java.util.function.Function;

public class Completion {
    Completion next;
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
        Completion completion = new Completion(consumer);
        this.next = completion;
    }

    public Completion andApply(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> function) {
        Completion completion = new Completion(function);
        this.next = completion;

        return completion;
    }

    private void error(Throwable exception) {

    }

    private void complete(ResponseEntity<String> responseEntity) {
        if (next != null) next.run(responseEntity);
    }

    private void run(ResponseEntity<String> responseEntity) {
        if (consumer != null) consumer.accept(responseEntity);
        else if (function != null) {
            ListenableFuture<ResponseEntity<String>> listenableFuture = function.apply(responseEntity);
            listenableFuture.addCallback(s -> complete(s), e -> error(e));
        }
    }
}
