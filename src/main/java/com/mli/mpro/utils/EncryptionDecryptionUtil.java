package com.mli.mpro.utils;

import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import com.mli.mpro.common.exception.UserHandledException;
import com.mli.mpro.productRestriction.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionDecryptionUtil {
	private static final String PKCSPADDING = "PKCS5Padding";
	private static final String CIPHER_TRANSFORMATION = "AES/CBC/";
	private static final String AES_ENCRYPTION_ALGORITHM = "AES";
	private static final String CIPHER_ECB_TRANSFORMATION = "AES/ECB/PKCS5Padding";

    private static final Logger logger = LoggerFactory.getLogger(EncryptionDecryptionUtil.class);

    public static String getPKCSPadding() {
        return PKCSPADDING;
    }

	private EncryptionDecryptionUtil() {

	}

	public static String encrypt(String plainText, String key) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] plainTextbytes = plainText.getBytes(StandardCharsets.UTF_8);
		byte[] keyBytes = getKeyBytes(key);
		return Base64.getEncoder().encodeToString(encrypt(plainTextbytes, keyBytes, keyBytes));
	}

	public static String decrypt(String encryptedText, String key) throws GeneralSecurityException {
		byte[] cipheredBytes = Base64.getDecoder().decode(encryptedText);
		byte[] keyBytes = getKeyBytes(key);
		return new String(decrypt(cipheredBytes, keyBytes, keyBytes), StandardCharsets.UTF_8);
	}

	public static byte[] decrypt(byte[] cipherText, byte[] key, byte[] initialVector)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION + getPKCSPadding());
		SecretKeySpec secretKeySpecy = new SecretKeySpec(key, AES_ENCRYPTION_ALGORITHM);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
		cipherText = cipher.doFinal(cipherText);
		return cipherText;
	}

	public static byte[] encrypt(byte[] plainText, byte[] key, byte[] initialVector)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION + getPKCSPadding());
		SecretKeySpec secretKeySpec = new SecretKeySpec(key, AES_ENCRYPTION_ALGORITHM);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		plainText = cipher.doFinal(plainText);
		return plainText;
	}

	private static byte[] getKeyBytes(String key) {
		byte[] keyBytes = new byte[16];
		byte[] parameterKeyBytes = key.getBytes(StandardCharsets.UTF_8);
		System.arraycopy(parameterKeyBytes, 0, keyBytes, 0, Math.min(parameterKeyBytes.length, keyBytes.length));
		return keyBytes;
	}

	public static byte[] doEncryption(String s, String secretKey) throws Exception {

		Cipher c=Cipher.getInstance(CIPHER_ECB_TRANSFORMATION);
		// Initialize the cipher for encryption
		c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(secretKey.getBytes(), "AES"));

		//sensitive information
		byte[] text = s.getBytes();

		// Encrypt the text
		byte[] textEncrypted = c.doFinal(text);

		return (org.apache.commons.codec.binary.Base64.encodeBase64(textEncrypted));

	}
	public static String doDecryption(byte[] encResponse, String secretKey) throws Exception {

        Cipher c=Cipher.getInstance(CIPHER_ECB_TRANSFORMATION  + getPKCSPadding());
        // Initialize the same cipher for decryption
        c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(secretKey.getBytes(), "AES"));
        // Decrypt the text
        byte[] textDecrypted = c.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(new String(encResponse)));
        return (new String(textDecrypted));
    }

    public static String encrypt(String payload, String key, byte[] FIXED_IV) throws UserHandledException {
        logger.info("Actual key: {} iv: {}",key,FIXED_IV);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), AppConstants.ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(FIXED_IV);
		logger.info("Response to be encrypted is : {}", payload);
        return AESCBCAlgoCrypto.encrypt(AppConstants.CBC_ALGO, payload, keySpec, ivParameterSpec);
    }

}
