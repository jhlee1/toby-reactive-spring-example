package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec10;

import org.springframework.http.ResponseEntity;

import java.util.function.Consumer;

public class ErrorCompletion<T> extends Completion<T, T> {
    Consumer<Throwable> econ;

    public ErrorCompletion(Consumer<Throwable> econ) {
        this.econ = econ;
    }

    @Override
    void run(T responseEntity) {
        if (next != null) {
            next.run(responseEntity);
        }
    }

    @Override
    void error(Throwable exception) {
        econ.accept(exception);
    }
}
