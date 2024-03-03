package io.setl.rdf.normalization;

import java.util.Map;

import com.apicatalog.rdf.RdfDataset;
import com.apicatalog.rdf.RdfResource;

public class RdfNormalizationResult {

  private final Map<RdfResource, RdfResource> inputMappings;

  private final Map<RdfResource, RdfResource> issuedMappings;

  private final RdfDataset normalized;


  /**
   * New instance.
   *
   * @param normalized     the output dataset
   * @param inputMappings  the input mappings
   * @param issuedMappings the issued mappings
   */
  public RdfNormalizationResult(RdfDataset normalized, Map<RdfResource, RdfResource> inputMappings, Map<RdfResource, RdfResource> issuedMappings) {
    this.inputMappings = inputMappings;
    this.issuedMappings = issuedMappings;
    this.normalized = normalized;
  }


  public Map<RdfResource, RdfResource> getInputMappings() {
    return inputMappings;
  }


  public Map<RdfResource, RdfResource> getIssuedMappings() {
    return issuedMappings;
  }


  public RdfDataset getNormalized() {
    return normalized;
  }

}
