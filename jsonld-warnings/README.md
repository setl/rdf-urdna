# Dangerous flaws when using URDNA-2015 to digitally sign JSON-LD

In the sub-folders you will find three JSON-LD Verifiable Credentials that express vastly different claims but have absolutely identical digital signatures.

If you submit the JSON-LD documents to the [JSON-LD Playground](https://json-ld.org/playground/), you can verify for yourself that all three documents are
identical once transformed to N-Quads. This means the RDF definition is identical and hence the signature is identical, despite expressing different data.

When using URDNA-2015 to digitally sign JSON-LD one should be aware that:

1) You can add data with very few restrictions and it does not change the signature. See "hack1" for an example.
2) You can rename or move any property without changing the signature. See "hack2".
3) You can replace any string value with a different string without changing the signature. See "hack2"
4) You can submit small documents that take years to process.

An URDN-2015 signature can be trusted if you follow this algorithm:

+ Step 1: Convert the JSON-LD to RDF Quads. This discards inserted data and corrects renamed data.
+ Step 2: Verify no RDF Quad is a blank node referencing another blank node. This ensures the time to verify the signature is O(N) not O(N!). 
+ Step 3: Verify the URDNA signature.
+ Step 4: Convert the RDF Quads to JSON-LD using JSON-LD Framing.

Note that step 2 requires all valid signed documents also have no blank nodes referencing other blank nodes, so must be agreed by all document creators.


