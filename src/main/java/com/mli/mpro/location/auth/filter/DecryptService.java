package com.mli.mpro.location.auth.filter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class DecryptService {
	
	@Value("${bypassToken.secretKey}")
	private String byPAssSecretKey;
	@Value("${bypass.header.decrypt.value}")
	private String byPAssEncryptHeader;
	
	private static final Logger log = LoggerFactory.getLogger(DecryptService.class);

	private static final String ENCRYPTION_ALGO = "AES/GCM/NoPadding";
	private static final int GCM_TAG_LENGTH = 16;
	private static final String SECRET_KEY_ALGO = "AES";
	
	public boolean validateHeader(String encrptData) {
		byte[] decryptData;
		try {
			decryptData = decrypt(Base64.getDecoder().decode(encrptData), Base64.getDecoder().decode(byPAssSecretKey));
		} catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException
				| InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {
			log.error("Exception while decrypting the header {}", Utility.getExceptionAsString(e));
			return false;
		}
		String decyptedString = new String(decryptData, StandardCharsets.UTF_8);
		log.info("decryption of client secret key is successful");
		return decyptedString.equals(byPAssEncryptHeader);
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

}
