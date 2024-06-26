package cn.iinux.java.alpha.net;

import javax.net.ssl.*;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;

public class CertCheck {
    public static void checkCertificate(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.connect();

            Certificate[] certs = conn.getServerCertificates();
            if (certs.length > 0 && certs[0] instanceof X509Certificate) {
                X509Certificate cert = (X509Certificate) certs[0];

                Date notBefore = cert.getNotBefore();
                Date notAfter = cert.getNotAfter();
                Date currentDate = new Date();

                System.out.println("Certificate validity period:");
                System.out.println("Not Before: " + notBefore);
                System.out.println("Not After: " + notAfter);
                System.out.println("Current Time: " + currentDate);

                if (currentDate.after(notBefore) && currentDate.before(notAfter)) {
                    System.out.println("The certificate is currently valid.");
                } else {
                    System.out.println("The certificate is not currently valid.");
                }
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String url = "https://www.example.com/";
        checkCertificate(url);
    }
}
