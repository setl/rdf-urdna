package io.setl.rdf.normalization;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.apicatalog.rdf.Rdf;
import com.apicatalog.rdf.RdfResource;
import com.apicatalog.rdf.RdfValue;

/**
 * An issuer of counted identifiers to map identifiers from one naming scheme to another.
 *
 * @author Simon Greatrix on 06/10/2020.
 */
public class IdentifierIssuer {

  /** Identifiers that have already been issued. */
  private final LinkedHashMap<RdfResource, RdfResource> existing = new LinkedHashMap<>();

  /** Set of values that have already been issued. This set should match the values of {@link #existing}. */
  private final HashSet<RdfResource> generated = new HashSet<>();

  /** The prefix for new identifiers. */
  private final String prefix;

  /** Counter for creating new identifiers. */
  private int counter = 0;


  /**
   * Create a new instance.
   *
   * @param prefix the prefix for new identifiers.
   */
  IdentifierIssuer(String prefix) {
    this.prefix = prefix;
  }


  /**
   * Create a mapping in another issuer for all identifiers issued by this, in the same order that they were issued by this.
   *
   * @param other the other identifier issuer.
   */
  public void assign(IdentifierIssuer other) {
    existing.forEach((k, v) -> other.getId(k));
  }


  /**
   * Create a copy of this issuer.
   *
   * @return the issuer to copy
   */
  public IdentifierIssuer copy() {
    IdentifierIssuer newIssuer = new IdentifierIssuer(prefix);
    newIssuer.existing.putAll(existing);
    newIssuer.counter = counter;
    newIssuer.generated.addAll(generated);
    return newIssuer;
  }


  private RdfResource getForBlank(RdfResource value, AtomicBoolean flag) {
    if (hasId(value)) {
      flag.set(true);
      return getId(value);
    }
    return value;
  }


  /**
   * Get or allocate a new ID for the specified old ID.
   *
   * @param id the old ID
   *
   * @return the new ID
   */
  public RdfResource getId(RdfResource id) {
    RdfResource v = existing.get(id);
    if (v != null) {
      return v;
    }

    do {
      v = Rdf.createBlankNode(prefix + (counter++));
    } while (generated.contains(v));
    existing.put(id, v);
    generated.add(v);

    return v;
  }


  /**
   * Get the resource replaced by a proper blank identifier if appropriate.
   *
   * @param value the resource to check
   * @param flag  set to true if a replacement happens
   *
   * @return the value or the replaced value
   */
  RdfValue getIfExists(RdfValue value, AtomicBoolean flag) {
    return (value != null && value.isBlankNode()) ? getForBlank((RdfResource) value, flag) : value;
  }


  /**
   * Get the resource replaced by a proper blank identifier if appropriate.
   *
   * @param value the resource to check
   * @param flag  set to true if a replacement happens
   *
   * @return the value or the replaced value
   */
  public RdfResource getIfExists(RdfResource value, AtomicBoolean flag) {
    return (value != null && value.isBlankNode()) ? getForBlank(value, flag) : value;
  }


  /**
   * Get all the identifiers issued as a map.
   *
   * @return the map of identifiers
   */
  public Map<RdfResource, RdfResource> getMappings() {
    LinkedHashMap<RdfResource, RdfResource> copy = new LinkedHashMap<>(existing);
    return Collections.unmodifiableMap(copy);
  }


  /**
   * Does an old ID have an allocated new ID?.
   *
   * @param id the old ID
   *
   * @return true of a new ID has been allocated for this old ID.
   */
  public boolean hasId(RdfResource id) {
    return existing.containsKey(id);
  }


  IdentifierIssuer withMappings(InputMappings mappings) {
    if (mappings != null) {
      mappings.getMappings().forEach((k, v) -> {
        existing.put(k, v);
        generated.add(v);
      });
    }
    return this;
  }

}
