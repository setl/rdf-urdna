package io.setl.rdf.normalization;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import com.apicatalog.jsonld.http.media.MediaType;
import com.apicatalog.rdf.Rdf;
import com.apicatalog.rdf.RdfDataset;
import com.apicatalog.rdf.io.RdfWriter;
import com.apicatalog.rdf.io.error.RdfReaderException;
import com.apicatalog.rdf.io.error.RdfWriterException;
import com.apicatalog.rdf.io.error.UnsupportedContentException;
import org.junit.Test;

/**
 * A test submitted from a user bug report.
 */
public class UserSubmittedTest {

  @Test
  public void test() throws UnsupportedContentException, IOException, RdfWriterException, RdfReaderException {
    String nQuads = "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192d4231> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <https://example.com/#DocumentVerification> _:b0 .\n"
        + "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192d4231> <https://example.com/#documentPresence> \"Physical\" _:b0 .\n"
        + "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192d4231> <https://example.com/#evidenceDocument> \"DriversLicense\" _:b0 .\n"
        + "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192d4231> <https://example.com/#subjectPresence> \"Physical\" _:b0 .\n"
        + "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192d4231> <https://example.com/#verifier> \"https://example.edu/issuers/14\" _:b0 .\n"
        + "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192dxyzab> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <https://example.com/#SupportingActivity> _:b0 .\n"
        + "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192dxyzab> <https://example.com/#documentPresence> \"Digital\" _:b0 .\n"
        + "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192dxyzab> <https://example.com/#evidenceDocument> \"Fluid Dynamics Focus\" _:b0 .\n"
        + "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192dxyzab> <https://example.com/#subjectPresence> \"Digital\" _:b0 .\n"
        + "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192dxyzab> <https://example.com/#verifier> \"https://example.edu/issuers/14\" _:b0 .\n"
        + "<urn:uuid:789> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <https://www.w3.org/2018/credentials#VerifiablePresentation> .\n"
        + "<urn:uuid:789> <https://www.w3.org/2018/credentials#holder> <did:example:123> .\n"
        + "<urn:uuid:789> <https://www.w3.org/2018/credentials#verifiableCredential> _:b0 .\n"
        + "_:b1 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <https://www.w3.org/2018/credentials#VerifiableCredential> _:b0 .\n"
        + "_:b1 <https://w3id.org/security#proof> _:b2 _:b0 .\n"
        + "_:b1 <https://www.w3.org/2018/credentials#credentialSubject> <did:example:456> _:b0 .\n"
        + "_:b1 <https://www.w3.org/2018/credentials#evidence> <https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192d4231> _:b0 .\n"
        + "_:b1 <https://www.w3.org/2018/credentials#evidence> <https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192dxyzab> _:b0 .\n"
        + "_:b1 <https://www.w3.org/2018/credentials#issuanceDate> \"2021-01-01T19:23:24Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime> _:b0 .\n"
        + "_:b1 <https://www.w3.org/2018/credentials#issuer> <did:example:123> _:b0 .\n"
        + "_:b3 <http://purl.org/dc/terms/created> \"2021-10-02T17:58:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime> _:b2 .\n"
        + "_:b3 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <https://w3id.org/security#JsonWebSignature2020> _:b2 .\n"
        + "_:b3 <https://w3id.org/security#jws> \"eyJiNjQiOmZhbHNlLCJjcml0IjpbImI2NCJdLCJhbGciOiJFZERTQSJ9..VA8VQqAerUT6AIVdHc8W8Q2aj12LOQjV_VZ1e134NU9Q20eBsNySPjNdmTWp2HkdquCnbRhBHxIbNeFEIOOhAg\" _:b2 .\n"
        + "_:b3 <https://w3id.org/security#proofPurpose> <https://w3id.org/security#assertionMethod> _:b2 .\n"
        + "_:b3 <https://w3id.org/security#verificationMethod> <did:example:123#key-0> _:b2 .\n";

    // I got the same output from RDF distiller and JSON-LD playground...
    String expectedCanonizedOutput = "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192d4231> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <https://example.com/#DocumentVerification> _:c14n1 .\n"
        + "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192d4231> <https://example.com/#documentPresence> \"Physical\" _:c14n1 .\n"
        + "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192d4231> <https://example.com/#evidenceDocument> \"DriversLicense\" _:c14n1 .\n"
        + "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192d4231> <https://example.com/#subjectPresence> \"Physical\" _:c14n1 .\n"
        + "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192d4231> <https://example.com/#verifier> \"https://example.edu/issuers/14\" _:c14n1 .\n"
        + "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192dxyzab> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <https://example.com/#SupportingActivity> _:c14n1 .\n"
        + "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192dxyzab> <https://example.com/#documentPresence> \"Digital\" _:c14n1 .\n"
        + "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192dxyzab> <https://example.com/#evidenceDocument> \"Fluid Dynamics Focus\" _:c14n1 .\n"
        + "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192dxyzab> <https://example.com/#subjectPresence> \"Digital\" _:c14n1 .\n"
        + "<https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192dxyzab> <https://example.com/#verifier> \"https://example.edu/issuers/14\" _:c14n1 .\n"
        + "<urn:uuid:789> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <https://www.w3.org/2018/credentials#VerifiablePresentation> .\n"
        + "<urn:uuid:789> <https://www.w3.org/2018/credentials#holder> <did:example:123> .\n"
        + "<urn:uuid:789> <https://www.w3.org/2018/credentials#verifiableCredential> _:c14n1 .\n"
        + "_:c14n0 <http://purl.org/dc/terms/created> \"2021-10-02T17:58:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime> _:c14n3 .\n"
        + "_:c14n0 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <https://w3id.org/security#JsonWebSignature2020> _:c14n3 .\n"
        + "_:c14n0 <https://w3id.org/security#jws> \"eyJiNjQiOmZhbHNlLCJjcml0IjpbImI2NCJdLCJhbGciOiJFZERTQSJ9..VA8VQqAerUT6AIVdHc8W8Q2aj12LOQjV_VZ1e134NU9Q20eBsNySPjNdmTWp2HkdquCnbRhBHxIbNeFEIOOhAg\" _:c14n3 .\n"
        + "_:c14n0 <https://w3id.org/security#proofPurpose> <https://w3id.org/security#assertionMethod> _:c14n3 .\n"
        + "_:c14n0 <https://w3id.org/security#verificationMethod> <did:example:123#key-0> _:c14n3 .\n"
        + "_:c14n2 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <https://www.w3.org/2018/credentials#VerifiableCredential> _:c14n1 .\n"
        + "_:c14n2 <https://w3id.org/security#proof> _:c14n3 _:c14n1 .\n"
        + "_:c14n2 <https://www.w3.org/2018/credentials#credentialSubject> <did:example:456> _:c14n1 .\n"
        + "_:c14n2 <https://www.w3.org/2018/credentials#evidence> <https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192d4231> _:c14n1 .\n"
        + "_:c14n2 <https://www.w3.org/2018/credentials#evidence> <https://example.edu/evidence/f2aeec97-fc0d-42bf-8ca7-0548192dxyzab> _:c14n1 .\n"
        + "_:c14n2 <https://www.w3.org/2018/credentials#issuanceDate> \"2021-01-01T19:23:24Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime> _:c14n1 .\n"
        + "_:c14n2 <https://www.w3.org/2018/credentials#issuer> <did:example:123> _:c14n1 .\n";

    InputStream inputStream = new ByteArrayInputStream(nQuads.getBytes(StandardCharsets.UTF_8));

    RdfDataset rdf = Rdf.createReader(MediaType.N_QUADS, inputStream).readDataset();
    RdfDataset canonical = RdfNormalize.normalize(rdf);
    StringWriter stringWriter = new StringWriter();
    RdfWriter rdfWriter = Rdf.createWriter(MediaType.N_QUADS, stringWriter);
    rdfWriter.write(canonical);

    String canonicalizationResult = stringWriter.toString();

    System.out.println(canonicalizationResult);
    assertEquals(expectedCanonizedOutput, canonicalizationResult);
  }
}
