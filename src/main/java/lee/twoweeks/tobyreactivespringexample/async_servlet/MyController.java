package lee.twoweeks.tobyreactivespringexample.async_servlet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
  @GetMapping("async")
  public String async() {

  }
}
