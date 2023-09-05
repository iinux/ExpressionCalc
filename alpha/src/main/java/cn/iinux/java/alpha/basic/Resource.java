package cn.iinux.java.alpha.basic;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Properties;

public class Resource {
    public static void main(String[] args) throws IOException, CertificateException, NoSuchAlgorithmException, NoSuchProviderException, KeyStoreException {
        InputStream inputStream;
        Properties properties = new Properties();

        String file = "input/1.properties";

        URL url = Resource.class.getResource(file);
        System.out.println(url);

        if (url != null) {
            inputStream = url.openStream();
        } else {
            inputStream = new FileInputStream(file);
        }
        properties.load(inputStream);
        System.out.println(properties);
    }

    String pfxFile = "z.pfx";
    String pemFile = "private.pem";

    @Test
    public void ksPfx() throws NoSuchProviderException, KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());

        String type = "PKCS12";
        KeyStore ks = KeyStore.getInstance(type, "BC");

        InputStream inputStream = new FileInputStream(pfxFile);
        ks.load(inputStream, "123123".toCharArray());
        System.out.println(ks);
    }

    @Test
    public void ksPem() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        String keyStr = new String(Files.readAllBytes(Paths.get(pemFile)), StandardCharsets.UTF_8);
        keyStr = keyStr.replace("-----BEGIN PRIVATE KEY-----", "");
        keyStr = keyStr.replace("-----END PRIVATE KEY-----", "");
        keyStr = keyStr.trim();
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(keyStr);
        KeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        String signType = "DSA";
        KeyFactory keyFactory = KeyFactory.getInstance(signType);
        PrivateKey priKey = keyFactory.generatePrivate(keySpec);
        System.out.println(priKey);
    }

    void printFile(String file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            System.out.println(str);
        }
    }
}
