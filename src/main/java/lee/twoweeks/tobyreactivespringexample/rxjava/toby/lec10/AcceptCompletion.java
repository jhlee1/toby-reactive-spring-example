package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec10;

import org.springframework.http.ResponseEntity;

import java.util.function.Consumer;

public class AcceptCompletion<S> extends Completion<S, Void> {
    Consumer<S> consumer;

    public AcceptCompletion(Consumer<S> consumer) {
        this.consumer = consumer;
    }

    @Override
    void run(S responseEntity) {
        consumer.accept(responseEntity);
    }
}
