package io.setl.rdf.normalization;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;

import com.apicatalog.rdf.Rdf;
import com.apicatalog.rdf.RdfDataset;
import com.apicatalog.rdf.RdfResource;
import com.apicatalog.rdf.RdfTriple;

/**
 * Shows how the normalization algorithm becomes impractical in the presence of blank nodes.
 *
 * @author Simon Greatrix on 03/07/2022.
 */
public class ExponentialFailure {

  public static void main(String[] args) throws Exception {
    System.out.println("This program does not terminate! Use CTRL+C or equivalent to stop it.\n\n");
    RdfNormalize.setDefaultMaxPermutations(Integer.MAX_VALUE);
    RdfNormalize.setDefaultMaxRunTimeMs(Integer.MAX_VALUE);

    int size = 1;
    ArrayList<RdfResource> links = new ArrayList<>();
    links.add(Rdf.createBlankNode("_:x0"));
    RdfResource prop = Rdf.createIRI("p");

    while (true) {
      links.add(Rdf.createBlankNode("_:x" + size));
      size++;

      RdfDataset rdfDataset = Rdf.createDataset();
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          RdfTriple triple = Rdf.createTriple(links.get(i), prop, links.get(j));
          rdfDataset.add(triple);
        }
      }

      long start = System.currentTimeMillis();
      System.out.println("Normalizing data set of " + size + " nodes.");
      for (RdfTriple triple : rdfDataset.getDefaultGraph().toList()) {
        System.out.print(NQuadSerializer.write(triple.getSubject(), triple.getPredicate(), triple.getObject(), Optional.empty()));
      }
      System.out.println();
      RdfNormalize.normalize(rdfDataset);
      long end = System.currentTimeMillis();
      Duration duration = Duration.of((end - start), ChronoUnit.MILLIS);
      System.out.println("Normalization took: " + duration);
    }
  }

}
