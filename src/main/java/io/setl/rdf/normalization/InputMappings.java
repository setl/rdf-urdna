package io.setl.rdf.normalization;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.apicatalog.rdf.Rdf;
import com.apicatalog.rdf.RdfResource;

/**
 * Use Input Mappings to force some of the blank node identifiers to be the specified values.
 */
public class InputMappings {

  private final Set<RdfResource> assigned = new HashSet<>();
  private final Map<RdfResource, RdfResource> mappings = new LinkedHashMap<>();


  private void add(RdfResource key, RdfResource value) {
    if (key == null || value == null) {
      throw new IllegalArgumentException("Null resource in mapping");
    }
    if (!key.isBlankNode()) {
      throw new IllegalArgumentException("Resource is not a blank node: " + key);
    }
    if (!value.isBlankNode()) {
      throw new IllegalArgumentException("Resource is not a blank node: " + value);
    }
    if (mappings.containsKey(key)) {
      if (mappings.get(key).equals(value)) {
        // This is OK
        return;
      }
      throw new IllegalArgumentException("Resource already mapped: " + key);
    }

    if (!assigned.add(value)) {
      throw new IllegalArgumentException("Resource already assigned: " + value);
    }
    mappings.put(key, value);
  }


  public void addNameMapping(String key, String value) {
    add(Rdf.createBlankNode(key), Rdf.createBlankNode(value));
  }


  /**
   * Add name mappings. These are converted to blank nodes.
   *
   * @param nameMappings the name mappings
   */
  public void addNameMappings(Map<String, String> nameMappings) {
    for (Map.Entry<String, String> entry : nameMappings.entrySet()) {
      add(Rdf.createBlankNode(entry.getKey()), Rdf.createBlankNode(entry.getValue()));
    }
  }


  /**
   * Add a single resource mapping. The resources should be blank nodes.
   *
   * @param key   the key
   * @param value the value
   */
  public void addResourceMapping(RdfResource key, RdfResource value) {
    add(key, value);
  }


  /**
   * Add pre-specified resource mappings. The resources should be blank nodes.
   *
   * @param nameMappings the name mappings
   */
  public void addResourceMappings(Map<RdfResource, RdfResource> nameMappings) {
    for (Map.Entry<RdfResource, RdfResource> entry : nameMappings.entrySet()) {
      add(entry.getKey(), entry.getValue());
    }
  }


  public Map<RdfResource, RdfResource> getMappings() {
    return Collections.unmodifiableMap(mappings);
  }

}
