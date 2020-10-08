package io.setl.rdf.normalization;

import static org.junit.Assert.*;

import java.util.Arrays;

import com.apicatalog.rdf.Rdf;
import com.apicatalog.rdf.RdfResource;
import org.junit.Test;

/**
 * @author Simon Greatrix on 06/10/2020.
 */
public class PermutatorTest {

  @Test
  public void test() {
    Permutator permutator = new Permutator(new RdfResource[] {
        Rdf.createBlankNode("_:s"),
        Rdf.createBlankNode("_:e"),
        Rdf.createBlankNode("_:t"),
        Rdf.createBlankNode("_:l")
    });
    StringBuilder builder = new StringBuilder();
    while(permutator.hasNext()) {
      builder.append(Arrays.toString(permutator.next()));
      builder.append("\n");
    }
    assertEquals("[_:s, _:e, _:t, _:l]\n"
            + "[_:e, _:s, _:t, _:l]\n"
            + "[_:t, _:s, _:e, _:l]\n"
            + "[_:s, _:t, _:e, _:l]\n"
            + "[_:e, _:t, _:s, _:l]\n"
            + "[_:t, _:e, _:s, _:l]\n"
            + "[_:l, _:e, _:s, _:t]\n"
            + "[_:e, _:l, _:s, _:t]\n"
            + "[_:s, _:l, _:e, _:t]\n"
            + "[_:l, _:s, _:e, _:t]\n"
            + "[_:e, _:s, _:l, _:t]\n"
            + "[_:s, _:e, _:l, _:t]\n"
            + "[_:s, _:t, _:l, _:e]\n"
            + "[_:t, _:s, _:l, _:e]\n"
            + "[_:l, _:s, _:t, _:e]\n"
            + "[_:s, _:l, _:t, _:e]\n"
            + "[_:t, _:l, _:s, _:e]\n"
            + "[_:l, _:t, _:s, _:e]\n"
            + "[_:l, _:t, _:e, _:s]\n"
            + "[_:t, _:l, _:e, _:s]\n"
            + "[_:e, _:l, _:t, _:s]\n"
            + "[_:l, _:e, _:t, _:s]\n"
            + "[_:t, _:e, _:l, _:s]\n"
            + "[_:e, _:t, _:l, _:s]\n",
        builder.toString());
  }

}