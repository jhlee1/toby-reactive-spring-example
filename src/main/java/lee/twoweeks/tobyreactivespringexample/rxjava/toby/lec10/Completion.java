package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec10;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.function.Consumer;

public class Completion {
    Completion next;
    Consumer<ResponseEntity<String>> consumer;

    public Completion() {
    }

    public Completion(Consumer<ResponseEntity<String>> consumer) {
        this.consumer = consumer;
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

    private void error(Throwable exception) {

    }

    private void complete(ResponseEntity<String> responseEntity) {
        if (next != null) next.run(responseEntity);
    }

    private void run(ResponseEntity<String> responseEntity) {
        if (consumer != null) consumer.accept(responseEntity);
    }
}
