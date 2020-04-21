package lee.twoweeks.tobyreactivespringexample.rxjava.toby.lec9;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class MyController {
    //    RestTemplate restTemplate = new RestTemplate();
    AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
    AsyncRestTemplate nonBlockingAsyncRestTemplate = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));

    @Autowired
    MyService myService;

    @GetMapping("rest")
    public ListenableFuture<ResponseEntity<String>> rest(int idx) {
        // Rest Template을 이용한 다른 서버 API 호출 ... 동기 방식이라서 해당 요청이 올 때까지 현재 thread는 block 상태
//        String res = restTemplate.getForObject("http://localhost:8081/service?req={req}", String.class,"hello" + idx);

        // Servlet thread를 바로 반환하기 위해 AsyncRestTemplate을 이용한 비동기 처리
        // ListenableFuture를 리턴해버리면 Spring MVC에서 알아서 값이 왔을때 Response를 리턴하도록 처리해줌
        // 하지만, AsyncRestTemplate은 요청을 날리는 만큼 비동기 thread를 생성한다 ... servlet thread를 늘리는 것과 차이가 없음
        // 이 문제를 해결하려면 Non-blocking IO 설정을 써보자!
        // AsyncRestTemplate을 생성할 때 Netty를 이용하도록 설정 => 이런식으로, AsyncRestTemplate nonBlockingAsyncRestTemplate = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));
        return asyncRestTemplate.getForEntity("http://localhost:8081/service1?req={req}", String.class,"hello" + idx);
    }

    // ListenableFuture를 비동기적으로 가공하기
    @GetMapping("asyncRestNIO")
    public DeferredResult<String> asyncRestNIO(int idx) {
        DeferredResult<String> dr = new DeferredResult<>();

        // 하나의 요청을 non-blocking으로 처리하기
//        ListenableFuture<ResponseEntity<String>> f1 = nonBlockingAsyncRestTemplate.getForEntity("http://localhost:8081/service1?req={req}", String.class,"hello" + idx);
//        f1.addCallback(s -> {
//            dr.setResult(s.getBody() + "/work");
//        }, e -> {
//            // 비동기 작업에선 Error를 throw하면 안됨. 여러 Thread가 복잡하기 돌아가기 때문에 Thread인지 추적이 쉽지 않다.
//            dr.setErrorResult(e.getMessage());
//        });


        // 여러개의 서비스에 요청을 callback으로 처리해야 하는 경우
        // NIO 방식으로 처리가 가능하지만, 코드가 너무 길어지고 복잡하다. Callback Hell!!
        ListenableFuture<ResponseEntity<String>> f2 = nonBlockingAsyncRestTemplate.getForEntity("http://localhost:8081/service1?req={req}", String.class,"hello" + idx);
        f2.addCallback(s -> {
            ListenableFuture<ResponseEntity<String>> f3 = nonBlockingAsyncRestTemplate.getForEntity("http://localhost:8081/service2?req={req}", String.class, s.getBody());
            f3.addCallback( s2 -> {
//                dr.setResult(s2.getBody());
                ListenableFuture<String> f4 = myService.work(s2.getBody());
                f4.addCallback(s3 -> {
                    dr.setResult(s3);
                }, e -> {
                    dr.setErrorResult(e.getMessage());
                });
            }, e -> {
                dr.setErrorResult(e.getMessage());
            });
        }, e -> {
            // 비동기 작업에선 Error를 throw하면 안됨. 여러 Thread가 복잡하기 돌아가기 때문에 Thread인지 추적이 쉽지 않다.
            dr.setErrorResult(e.getMessage());
        });
        return dr;
    }
}
