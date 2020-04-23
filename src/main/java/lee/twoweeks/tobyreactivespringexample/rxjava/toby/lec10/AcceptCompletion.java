package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec10;

import org.springframework.http.ResponseEntity;

import java.util.function.Consumer;

public class AcceptCompletion extends Completion {
    Consumer<ResponseEntity<String>> consumer;

    public AcceptCompletion(Consumer<ResponseEntity<String>> consumer) {
        this.consumer = consumer;
    }
}
