# rdf-urdna

This is an implementation of the Universal RDF Dataset Normalization Algorithm (2015).

Usage:

```
   RdfDataset normalizedData = RdfNormalize.normalize(rdfInput);
```

The library passes all the [standard tests for the URDNA2015 algorithm](https://json-ld.github.io/normalization/tests/index.html#manifest-urdna2015).

Users are advised that the time the URDNA2015 algorithm takes to complete is influenced by the number of blank nodes in the input. It is possible to create
 small datasets that would take more energy than exists in the observable universe to normalize. 