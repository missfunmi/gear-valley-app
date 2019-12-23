package com.gearvalley.domain;

import com.gearvalley.domain.models.Hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultHelloService implements HelloService {
  private final WorldPort worldPort;

  @Autowired
  public DefaultHelloService(WorldPort worldPort) {
    this.worldPort = worldPort;
  }

  @Override
  public Hello getHello() {
    return Hello.builder().value("Hello " +  worldPort.getWorld()).build();
  }
}
