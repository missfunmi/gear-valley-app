package com.gearvalley.domain;

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
  public String getHello() {
    return "Hello " + worldPort.getWorld();
  }
}
