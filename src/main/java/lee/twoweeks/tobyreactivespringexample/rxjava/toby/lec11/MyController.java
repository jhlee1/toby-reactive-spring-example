package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec11;

import io.netty.channel.nio.NioEventLoopGroup;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

@RequiredArgsConstructor
@RestController
public class MyController {
    AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));
    private static final String REMOTE_URL_SERVICE_1 = "http://localhost:8081/service1";
    private static final String REMOTE_URL_SERVICE_2 = "http://localhost:8081/service2";
    private final MyService myService;
    @GetMapping("rest")
    public DeferredResult<String> rest(Integer idx) {
        DeferredResult<String> dr = new DeferredResult<>();

        toCF(asyncRestTemplate.getForEntity(REMOTE_URL_SERVICE_1, String.class, "h" + idx))
            .thenCompose(s -> {
                if (true) throw new RuntimeException("ERROR");

                return toCF(asyncRestTemplate.getForEntity(REMOTE_URL_SERVICE_2, String.class, s.getBody()));
            })
            .thenApplyAsync(s2 -> myService.work(s2.getBody()))
            .thenAccept(s3 -> dr.setResult(s3))
        .exceptionally(e -> {dr.setErrorResult(e.getMessage()); return null; });

//        Completion.from(nonBlockingAsyncRestTemplate.getForEntity(REMOTE_URL_SERVICE_1, String.class))
//                .andApply(s -> nonBlockingAsyncRestTemplate.getForEntity(REMOTE_URL_SERVICE_1, String.class, s.getBody()))
//                .andApply(s -> myService.work(s.getBody()))
//                .andError(e -> dr.setErrorResult(e))
//                .andAccept(s -> dr.setResult(s));
        return dr;
    }

    <T> CompletableFuture<T> toCF(ListenableFuture<T> listenableFuture) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        listenableFuture.addCallback(s -> completableFuture.complete(s), e -> completableFuture.completeExceptionally(e));

        return completableFuture;
    }
}
