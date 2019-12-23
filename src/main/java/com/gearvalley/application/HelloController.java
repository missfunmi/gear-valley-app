package com.gearvalley.application;

import com.gearvalley.domain.HelloService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class HelloController {
  private final HelloService helloService;

  @Autowired
  public HelloController(HelloService helloService) {
    this.helloService = helloService;
  }

  @RequestMapping("/hello")
  public ResponseEntity<Map> getHello() {
    Map<String, String> data = new HashMap<>();
    data.put("message", helloService.getHello());
    return ResponseEntity.ok(data);
  }
}
