package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec10;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.function.Function;

public class ApplyCompletion<S, T> extends Completion<S, T> {
    Function<S, ListenableFuture<T>> function;

    public ApplyCompletion(Function<S, ListenableFuture<T>> function) {
        this.function = function;
    }

    @Override
    void run(S responseEntity) {
        ListenableFuture<T> listenableFuture = function.apply(responseEntity);
            listenableFuture.addCallback(s -> complete(s), e -> error(e));
    }

}
