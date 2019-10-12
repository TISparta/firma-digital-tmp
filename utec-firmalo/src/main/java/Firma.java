import com.itextpdf.io.IOException;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.signatures.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;

class Firma {

    static String pathBase = "/home/tisparta/Downloads/Information-Systems/Firmalo/";

    public static void sign(String pathIn, String pathCertificate, String output, float x, float y)
            throws GeneralSecurityException, IOException, java.io.IOException {
        String privateKey = "1234";
        String pathOutput = pathBase + output;

        BouncyCastleProvider provider = new BouncyCastleProvider();
        Security.addProvider(provider);

        KeyStore ks = KeyStore.getInstance("pkcs12");
        ks.load(new FileInputStream(pathCertificate), privateKey.toCharArray());
        String alias = (String) ks.aliases().nextElement();
        PrivateKey key = (PrivateKey) ks.getKey(alias, privateKey.toCharArray());
        Certificate[] chain = ks.getCertificateChain(alias);

        sign(pathIn, pathOutput, chain, key, DigestAlgorithms.SHA256, provider.getName(), PdfSigner.CryptoStandard.CMS, "Test 1", "UTEC-CSLab", x, y);
    }

    private static void sign(String src, String dest,
                             Certificate[] chain,
                             PrivateKey pk, String digestAlgorithm, String provider,
                             PdfSigner.CryptoStandard subfilter,
                             String reason, String location, float x, float y)
            throws GeneralSecurityException, IOException, java.io.IOException {
        // Creating the reader and the signer
        PdfReader reader = new PdfReader(src);
        PdfSigner signer = new PdfSigner(reader, new FileOutputStream(dest), new StampingProperties());
        // Creating the appearance
        PdfSignatureAppearance appearance = signer.getSignatureAppearance()
                .setReason(reason)
                .setLocation(location)
                .setReuseAppearance(false);
        Rectangle rect = new Rectangle(x, y - 100, 200, 100);
        appearance
                .setPageRect(rect)
                .setPageNumber(1);
        signer.setFieldName("sig");
        // Creating the signature
        IExternalSignature pks = new PrivateKeySignature(pk, digestAlgorithm, provider);
        IExternalDigest digest = new BouncyCastleDigest();
        signer.signDetached(digest, pks, chain, null, null, null, 0, subfilter);
    }
}
