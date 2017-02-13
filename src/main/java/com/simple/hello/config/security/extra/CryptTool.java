package com.simple.hello.config.security.extra;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;


@Slf4j
public class CryptTool {
    private static final String UNICODE_FORMAT = "UTF-8";
    private static final String CIPHER = "AES/ECB/PKCS5Padding";

    private static SecretKeySpec secretKey;
    private static byte[] key;

    private CryptTool(String secretString) {
        Preconditions.checkNotNull(secretString);
        try {
            key = secretString.getBytes(UNICODE_FORMAT);
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            this.secretKey = new SecretKeySpec(key, "AES");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
    }

    public static CryptTool createCryptTool(String SECRET_KEY) {
        return new CryptTool(SECRET_KEY);
    }

    public String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(UNICODE_FORMAT)));
        } catch (Exception e) {
            log.error("Error while encrypting: ", e);
        }
        return null;
    }

    public String decrypt(String strToDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}
