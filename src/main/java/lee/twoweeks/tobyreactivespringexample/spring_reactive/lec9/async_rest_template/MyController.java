package lee.twoweeks.tobyreactivespringexample.spring_reactive.lec9.async_rest_template;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @GetMapping("rest")
    public String rest() throws InterruptedException {
        Thread.sleep(100);
        return "rest";
    }
}
