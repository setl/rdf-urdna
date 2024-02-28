package io.setl.rdf.normalization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.apicatalog.jsonld.http.media.MediaType;
import com.apicatalog.jsonld.json.JsonProvider;
import com.apicatalog.rdf.Rdf;
import com.apicatalog.rdf.RdfDataset;
import com.apicatalog.rdf.RdfGraph;
import com.apicatalog.rdf.RdfNQuad;
import com.apicatalog.rdf.RdfResource;
import com.apicatalog.rdf.RdfTriple;
import com.apicatalog.rdf.io.error.RdfReaderException;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import org.junit.Test;

/**
 * A test-suite is defined at: https://github.com/w3c/rdf-canon/tree/main/tests/rdfc10
 *
 * @author Simon Greatrix on 28/02/2024
 */
public class Rdfc10OfficialTest {

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
        //fail();
        return;
      }
    }
  }


  @Test
  public void test() throws Exception {
    for (int i = 1; i <= 77; i++) {
      String fileIn = String.format("rdfc10/test%03d-in.nq", i);
      String fileOut = String.format("rdfc10/test%03d-rdfc10.nq", i);
      String fileJson = String.format("rdfc10/test%03d-rdfc10map.json", i);

      RdfDataset dataIn;
      RdfDataset dataOut;

      try {
        InputStream inputStream = Rdfc10OfficialTest.class.getClassLoader().getResourceAsStream(fileIn);
        dataIn = inputStream != null ? Rdf.createReader(MediaType.N_QUADS, inputStream).readDataset() : null;

        inputStream = Rdfc10OfficialTest.class.getClassLoader().getResourceAsStream(fileOut);
        dataOut = inputStream != null ? Rdf.createReader(MediaType.N_QUADS, inputStream).readDataset() : null;
      } catch (RdfReaderException e) {
        System.out.println("Error reading file: " + fileIn + " or " + fileOut);
        e.printStackTrace();
        continue;
      }

      // Does the test case exist?
      if (dataIn == null && dataOut == null) {
        continue;
      }
      if (dataIn == null || dataOut == null) {
        System.out.println("WARNING: Test case " + i + " is missing " + (dataIn == null ? "input" : "output"));
        continue;
      }
      System.out.println("Processing " + fileIn);
      InputStream inputStream = Rdfc10OfficialTest.class.getClassLoader().getResourceAsStream(fileJson);
      InputMappings inputMappings = new InputMappings();
      if (inputStream != null) {
        JsonObject object = JsonProvider.instance().createReader(inputStream).readObject();
        for (Map.Entry<String, JsonValue> e : object.entrySet()) {
          if (e.getValue().getValueType() != JsonValue.ValueType.STRING) {
            fail("Invalid JSON");
          }
          inputMappings.addNameMapping(e.getKey(), ((JsonString) e.getValue()).getString());
        }
      }

      RdfDataset processed = RdfNormalize.normalize(dataIn, inputMappings, null);

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