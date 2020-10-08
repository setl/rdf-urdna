package io.setl.rdf.normalization;

import com.apicatalog.rdf.RdfNQuad;

/**
 * @author Simon Greatrix on 08/10/2020.
 */
public class SerializedQuad implements Comparable<SerializedQuad> {

  private final RdfNQuad quad;
  private final String serialized;


  SerializedQuad(RdfNQuad quad) {
    this.quad = quad;
    serialized = NQuadSerializer.write(quad);
  }


  @Override
  public int compareTo(SerializedQuad o) {
    return serialized.compareTo(o.serialized);
  }


  public RdfNQuad getQuad() {
    return quad;
  }


  public String getSerialized() {
    return serialized;
  }

}
