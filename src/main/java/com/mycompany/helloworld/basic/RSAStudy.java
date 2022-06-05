package com.mycompany.helloworld.basic;

import javax.crypto.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class RSAStudy {
    public static void main(String[] args) throws ClassNotFoundException, BadPaddingException, IllegalBlockSizeException,
            IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        /*
         * encryption - Encrypt and decrypt large string in java using RSA ...
         *
         * You cannot use an RSA encryption decryption on more than approx 128
         * bytes at a time. You must split up the data and do it in a loop pretty
         * much writing the bytes to String/Array as you go. If your only problem
         * is the size of the data, you probably don't have much more to go. Just
         * splitting the data.
         *
         * A great example, possibly more complete for you, dealing with strings
         * larger than 128 bytes: http:coding.westreicher.org/?p=23
         *
         * If you need more explanation on RSA encryption in general:
         *
         * The following code demonstrates how to use KeyPairGenerator to
         * generate an RSA key-pair in Java:
         */

        // Get an instance of the RSA key generator
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        // Generate the keys — might take sometime on slow computers
        KeyPair myPair = kpg.generateKeyPair();

        /*
         * This will give you a KeyPair object, which holds two keys: a private
         * and a public. In order to make use of these keys, you will need to
         * create a Cipher object, which will be used in combination with
         * SealedObject to encrypt the data that you are going to end over the
         * network. Here’s how you do that:
         */

        // Get an instance of the Cipher for RSA encryption/decryption
        Cipher c = Cipher.getInstance("RSA");
        // Initiate the Cipher, telling it that it is going to Encrypt, giving it the public key
        c.init(Cipher.ENCRYPT_MODE, myPair.getPublic());

        /*
         * After initializing the Cipher, we’re ready to encrypt the data.
         * Since after encryption the resulting data will not make much sense if
         * you see them “naked”, we have to encapsulate them in another
         * Object. Java provides this, by the SealedObject class. SealedObjects
         * are containers for encrypted objects, which encrypt and decrypt their
         * contents with the help of a Cipher object.
         *
         * The following example shows how to create and encrypt the contents of
         * a SealedObject:
         */

        // Create a secret message
        String myMessage = new String("Secret Message");
        // Encrypt that message using a new SealedObject and the Cipher we created before
        SealedObject myEncryptedMessage= new SealedObject( myMessage, c);

        /*
         * The resulting object can be sent over the network without fear, since
         * it is encrypted. The only one who can decrypt and get the data, is the
         * one who holds the private key. Normally, this should be the server. In
         * order to decrypt the message, we’ll need to re-initialize the Cipher
         * object, but this time with a different mode, decrypt, and use the
         * private key instead of the public key.
         *
         * This is how you do this in Java:
         */

        // Get an instance of the Cipher for RSA encryption/decryption
        Cipher dec = Cipher.getInstance("RSA");
        // Initiate the Cipher, telling it that it is going to Decrypt, giving it the private key
        dec.init(Cipher.DECRYPT_MODE, myPair.getPrivate());

        /*
         * Now that the Cipher is ready to decrypt, we must tell the SealedObject
         * to decrypt the held data.
         */

        // Tell the SealedObject we created before to decrypt the data and return it
        String message = (String) myEncryptedMessage.getObject(dec);
        System.out.println("foo = "+message);

        /*
         * Beware when using the getObject method, since it returns an instance
         * of an Object (even if it is actually an instance of String), and not
         * an instance of the Class that it was before encryption, so you’ll
         * have to cast it to its prior form.
         *
         * The above is from: http:andreas.louca.org/2008/03/20/java-rsa-
         * encryption-an-example/
         *
         * [msj121] [so/q/13500368] [cc by-sa 3.0]
         */
    }
}
