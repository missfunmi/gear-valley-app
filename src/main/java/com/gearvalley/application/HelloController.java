package com.gearvalley.application;

import com.gearvalley.domain.HelloService;
import com.gearvalley.domain.models.Hello;
import lombok.val;
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
  public ResponseEntity<Hello> getHello() {
    val hello = helloService.getHello();
    return ResponseEntity.ok(hello);
  }
}
