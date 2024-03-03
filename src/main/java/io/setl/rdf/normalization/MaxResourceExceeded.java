package io.setl.rdf.normalization;

/**
 * A runtime exception thrown when the normalization process exceeds the maximum number of resources allowed.
 */
public class MaxResourceExceeded extends RuntimeException {

  public MaxResourceExceeded(String message) {
    super(message);
  }

}
