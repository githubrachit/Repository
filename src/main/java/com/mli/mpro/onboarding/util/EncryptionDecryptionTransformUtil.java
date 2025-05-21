package com.mli.mpro.onboarding.util;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class EncryptionDecryptionTransformUtil {
    private static final String ENCRYPTION_ALGO = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 16;
    private static final String SECRET_KEY_ALGO = "AES";

    private EncryptionDecryptionTransformUtil() {
        throw new UnsupportedOperationException(
                "Instance can't be created.");
    }
    public static String encrypt(String plainText, String key) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] decodeKey = Base64.getDecoder().decode(key);
        return encrypt(plainText,decodeKey);
    }


    public static String decrypt(String encryptedText, String key) throws GeneralSecurityException {
        byte[] cipheredBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decodeKey = Base64.getDecoder().decode(key);
        return new String(decrypt(cipheredBytes, decodeKey), StandardCharsets.UTF_8);
    }

    public static byte[] decrypt(byte[] cipherMessage, byte[] key)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(cipherMessage);
        byte[] iv = new byte[12];
        byteBuffer.get(iv);
        byte[] cipherText = new byte[byteBuffer.remaining()];
        byteBuffer.get(cipherText);
        final Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, SECRET_KEY_ALGO),
                new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv));
        return cipher.doFinal(cipherText);
    }

    public static String encrypt(String messageToEncrypt, byte[] key)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] plainBytes = messageToEncrypt.getBytes();
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[12];
        secureRandom.nextBytes(iv);
        SecretKey secretKey = new SecretKeySpec(key, SECRET_KEY_ALGO);
        final Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGO);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
        byte[] cipherText = cipher.doFinal(plainBytes);
        ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + cipherText.length);
        byteBuffer.put(iv);
        byteBuffer.put(cipherText);
        byte[] cipherMessage = byteBuffer.array();
        return Base64.getEncoder().encodeToString(cipherMessage);
    }
}
