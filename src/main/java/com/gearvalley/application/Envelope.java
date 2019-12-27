package com.gearvalley.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * JSON:API-compliant wrapper for endpoints returning JSON objects
 *
 * @param <T> data element containing request or response payload
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Envelope<T> {
  private T data;
}
