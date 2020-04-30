package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec11;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class MyService {
    @Async
    public String work(String req) {
        return req + "/asyncwork";
    }
}
