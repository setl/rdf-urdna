# rdf-urdna

This is an implementation of the Universal RDF Dataset Normalization Algorithm (2015) for Java 1.8.

Usage:

```
   RdfDataset normalizedData = RdfNormalize.normalize(rdfInput);
```

The library passes all the [standard tests for the URDNA2015 algorithm](https://json-ld.github.io/normalization/tests/index.html#manifest-urdna2015).

## Warnings
Users are advised that the time the URDNA2015 algorithm takes to complete is influenced by the number of blank nodes in the input. It is possible to create
small datasets that would take more energy than exists in the observable universe to normalize. This means it is trivial to perform a denial-of-service 
attack by submitting tiny documents that require years to process.

For an example of this, please see the `ExponentialFailure` class in the test folder.

### Use with JSON-LD for Verifiable Credentials
The W3C considers the URDNA2015 algorithm a suitable way for digitally signing JSON-LD. If you are considering doing this, please look in the 
`jsonld-warnings` folder for reasons why you should not.