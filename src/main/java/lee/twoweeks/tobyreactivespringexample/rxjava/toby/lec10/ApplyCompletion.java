package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec10;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.function.Function;

public class ApplyCompletion extends Completion {
    Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> function;

    public ApplyCompletion(Function<ResponseEntity<String>, ListenableFuture<ResponseEntity<String>>> function) {
        this.function = function;
    }

}
