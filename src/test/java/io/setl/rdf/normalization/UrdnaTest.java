package io.setl.rdf.normalization;

import java.util.List;

import com.apicatalog.jsonld.http.media.MediaType;
import com.apicatalog.rdf.Rdf;
import com.apicatalog.rdf.RdfDataset;
import com.apicatalog.rdf.RdfGraph;
import com.apicatalog.rdf.RdfNQuad;
import com.apicatalog.rdf.RdfResource;
import com.apicatalog.rdf.RdfTriple;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * A test-suite is defined at: https://json-ld.github.io/normalization/tests/index.html#manifest-urdna2015.
 *
 * @author Simon Greatrix on 05/10/2020.
 */
public class UrdnaTest extends TestCase {

  private void checkGraph(String name, RdfGraph out, RdfGraph processed) {
    List<RdfTriple> triples = processed.toList();
    assertEquals("Graph " + name + " must be same size", out.toList().size(), triples.size());
    for (RdfTriple t : triples) {
      if (!out.contains(t)) {
        System.err.print("Generated triple not found in expected output: " + NQuadSerializer.write((RdfNQuad) t));
        System.err.println("Possible matches:");
        for (RdfTriple t0 : out.toList()) {
          System.err.print(NQuadSerializer.write((RdfNQuad) t0));
        }
        fail();
      }
    }
  }


  @Test
  public void test() throws Exception {
    for (int i = 1; i <= 62; i++) {
      String fileIn = String.format("test%03d-in.nq", i);
      String fileOut = String.format("test%03d-urdna2015.nq", i);
      System.out.println("Processing " + fileIn);
      RdfDataset dataIn = Rdf.createReader(MediaType.N_QUADS, UrdnaTest.class.getClassLoader().getResourceAsStream(fileIn)).readDataset();
      RdfDataset dataOut = Rdf.createReader(MediaType.N_QUADS, UrdnaTest.class.getClassLoader().getResourceAsStream(fileOut)).readDataset();

      RdfDataset processed = Urdna.normalize(dataIn);

      // processed and dataOut should be identical
      assertEquals("Canonical graph names should be identical", dataOut.getGraphNames(), processed.getGraphNames());
      checkGraph("*default*", dataOut.getDefaultGraph(), processed.getDefaultGraph());
      for (RdfResource name : dataOut.getGraphNames()) {
        checkGraph(name.getValue(), dataOut.getGraph(name).get(), processed.getGraph(name).get());
      }
      System.out.println("Processing " + fileIn + "   PASSED");
    }
  }

}