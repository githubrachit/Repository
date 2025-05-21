package com.mli.mpro.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionUtil {

    private static final String ENCRYPTION_ALGO = "AES";
	public static final String DEFAULT_CODING = "UTF-8";



    public static String encrypt(String messageToEncrypt, String key) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
	SecretKey secretKey = new SecretKeySpec(key.getBytes(DEFAULT_CODING), ENCRYPTION_ALGO);
	final Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGO);
	cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	byte[] cipherBytes = cipher.doFinal(messageToEncrypt.getBytes());
	return Base64.getEncoder().encodeToString(cipherBytes);
    }

}
