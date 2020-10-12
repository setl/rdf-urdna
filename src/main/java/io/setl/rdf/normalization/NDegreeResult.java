package io.setl.rdf.normalization;

/**
 * The output of the Hash N-Degree Quad algorithm.
 *
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof NDegreeResult)) {
      return false;
    }
    return getHash().equals(((NDegreeResult) o).getHash());
  }


  public String getHash() {
    return hash;
  }


  public IdentifierIssuer getIssuer() {
    return issuer;
  }


  @Override
  public int hashCode() {
    return getHash().hashCode();
  }

}
