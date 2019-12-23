package com.gearvalley.infrastructure;

import com.gearvalley.domain.WorldPort;
import org.springframework.stereotype.Service;

@Service
public class DefaultWorldPort implements WorldPort {

  @Override
  public String getWorld() {
    return "World";
  }

}
