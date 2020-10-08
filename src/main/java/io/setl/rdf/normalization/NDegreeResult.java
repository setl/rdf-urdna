package io.setl.rdf.normalization;

/**
 * @author Simon Greatrix on 08/10/2020.
 */
class NDegreeResult implements Comparable<NDegreeResult> {

  private final String hash;
  private final IdentifierIssuer issuer;


  NDegreeResult(String hash, IdentifierIssuer issuer) {
    this.hash = hash;
    this.issuer = issuer;
  }


  @Override
  public int compareTo(NDegreeResult o) {
    return hash.compareTo(o.hash);
  }


  public String getHash() {
    return hash;
  }


  public IdentifierIssuer getIssuer() {
    return issuer;
  }

}
