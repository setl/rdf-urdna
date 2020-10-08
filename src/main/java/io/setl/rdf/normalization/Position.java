package io.setl.rdf.normalization;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

import com.apicatalog.rdf.RdfNQuad;
import com.apicatalog.rdf.RdfResource;
import com.apicatalog.rdf.RdfValue;

/**
 * @author Simon Greatrix on 07/10/2020.
 */
public enum Position {
  SUBJECT('s') {
    @Override
    public boolean isBlank(RdfNQuad quad) {
      return quad.getSubject().isBlankNode();
    }


    @Override
    RdfValue get(RdfNQuad quad) {
      return quad.getSubject();
    }

  },
  OBJECT('o') {
    @Override
    RdfValue get(RdfNQuad quad) {
      return quad.getObject();
    }


    @Override
    public boolean isBlank(RdfNQuad quad) {
      return quad.getObject().isBlankNode();
    }
  },
  GRAPH('g') {
    @Override
    RdfValue get(RdfNQuad quad) {
      return quad.getGraphName().orElse(null);
    }


    @Override
    public boolean isBlank(RdfNQuad quad) {
      Optional<RdfResource> name = quad.getGraphName();
      return name.isPresent() && name.get().isBlankNode();
    }
  },
  PREDICATE('p') {
    @Override
    RdfValue get(RdfNQuad quad) {
      return quad.getPredicate();
    }


    @Override
    public boolean isBlank(RdfNQuad quad) {
      // predicates cannot be blank
      return false;
    }
  };

  public static final Set<Position> CAN_BE_BLANK = Collections.unmodifiableSet(EnumSet.of(Position.SUBJECT, Position.OBJECT, Position.GRAPH));

  private final byte tag;


  Position(char ch) {
    tag = (byte) ch;
  }


  abstract RdfValue get(RdfNQuad quad);


  abstract boolean isBlank(RdfNQuad quad);


  public byte tag() {
    return tag;
  }
}
