package com.mli.mpro.utils;

import com.mli.mpro.common.exception.UserHandledException;
import org.springframework.http.HttpStatus;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;

public class AESCBCAlgoCrypto {

    // Method to perform AES encryption
    public static String encrypt(String algorithm, String input, SecretKeySpec key, IvParameterSpec iv) throws UserHandledException {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] cipherText = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (Exception ex) {
            throw new UserHandledException(Collections.singletonList(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Method to perform AES decryption
    public static String decrypt(String algorithm, String cipherText, SecretKeySpec key, IvParameterSpec iv) throws UserHandledException {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
            return new String(plainText, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new UserHandledException(Collections.singletonList(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
