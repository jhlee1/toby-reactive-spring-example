package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec10;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class MyController {
    AsyncRestTemplate nonBlockingAsyncRestTemplate = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));
    private static final String REMOTE_URL_SERVICE_1 = "http://localhost:8081/service1";
    private static final String REMOTE_URL_SERVICE_2 = "http://localhost:8081/service2";
    @GetMapping("rest")
    public DeferredResult<String> rest() {
        DeferredResult<String> dr = new DeferredResult<>();
        Completion.from(nonBlockingAsyncRestTemplate.getForEntity(REMOTE_URL_SERVICE_1, String.class))
                .andApply(s -> nonBlockingAsyncRestTemplate.getForEntity(REMOTE_URL_SERVICE_1, String.class, s.getBody()))
                .andAccept(s -> dr.setResult(s.getBody()));

        return dr;
    }
}
