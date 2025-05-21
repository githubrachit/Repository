package com.mli.mpro.onboarding.util;

import com.mli.mpro.utils.EncryptionDecryptionUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AESEncryptDecryptUtil {

    private AESEncryptDecryptUtil() {
        throw new IllegalStateException("Encrypt Decrypt Util");
    }

    private static final String CIPHER_TRANSFORMATION = "AES/CBC/";
//      private static final String CIPHER_TRANSFORMATION = "AES/CBC/NoPadding";
    private static final String AES_ENCRYPTION_ALGORITHM = "AES";

    public static String encrypt(String plainText, String key) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] plainTextbytes = plainText.getBytes(StandardCharsets.UTF_8);
        byte[] intialVector = new byte[16];
        return Base64.getEncoder().encodeToString(encrypt(plainTextbytes, key.getBytes(), intialVector));
    }

    public static String decrypt(String encryptedText, String key) throws GeneralSecurityException {
        byte[] cipheredBytes = Base64.getDecoder().decode(encryptedText);
        byte[] intialVector = new byte[16];
        return new String(decrypt(cipheredBytes, key.getBytes(), intialVector), StandardCharsets.UTF_8);
    }

    public static byte[] encrypt(byte[] plainText, byte[] key, byte[] initialVector)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION + EncryptionDecryptionUtil.getPKCSPadding());
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, AES_ENCRYPTION_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        plainText = cipher.doFinal(plainText);
        return plainText;
    }

    public static byte[] decrypt(byte[] cipherText, byte[] key, byte[] initialVector)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION + EncryptionDecryptionUtil.getPKCSPadding());
        SecretKeySpec secretKeySpecy = new SecretKeySpec(key, AES_ENCRYPTION_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initialVector);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpecy, ivParameterSpec);
        cipherText = cipher.doFinal(cipherText);
        return cipherText;
    }
    
//    public static void main(String[] args) throws Exception {
//    	
//		String encStr = AESEncryptDecryptUtil.encrypt(
//				"{\"replacementSalePayload\":{\"proposerClientId\":\"\",\"insuredClientId\":\"\",\"currentPolicySignDate\":\"25/10/2023\",\"uinNo\":\"104N076V18\",\"currentPolicyNumber\":\"192269231\"},\"sourceChannel\":\"xyz\"}",
//				"AuKkUbX706U9wFC3lIo2aaI95cK/vjg=");
//    	
//    	System.out.println(encStr);
//    	
////    	String token = "eyJraWQiOiJiOUxMcWM3V2lMbGc1NjErVVwvU3JxV3JsbFU1RkM2V1kzY1RrXC9NVFRUVGM9IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiI3ZjB0NGF1ZGIzbWt0aDJqMjF1cXZtYjdnOSIsInRva2VuX3VzZSI6ImFjY2VzcyIsInNjb3BlIjoibXByby1waGFub3NcL3JlYWQgbXByby1waGFub3NcL3dyaXRlIiwiYXV0aF90aW1lIjoxNjU3NjM4NjI1LCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAuYXAtc291dGgtMS5hbWF6b25hd3MuY29tXC9hcC1zb3V0aC0xXzFicDl2NUI2ciIsImV4cCI6MTY1NzY0MjIyNSwiaWF0IjoxNjU3NjM4NjI1LCJ2ZXJzaW9uIjoyLCJqdGkiOiIxNjAwYzAzZC1kNWIyLTQ1MzEtYWIwMi0yNjE3MWIxNjI4OGIiLCJjbGllbnRfaWQiOiI3ZjB0NGF1ZGIzbWt0aDJqMjF1cXZtYjdnOSJ9.FrSICVD6woE1BKwgBMHEJjw7jp3eTgYzDd6lTgHb5xp_iU9teiwgWxfIokgfxDAX8rZERYAu53-ukkbmKWG7gaeJdHgKaEVE_ptM5Is3wXj_4P4MuUjUaF3VUaMcaaR6Oist2pVTVchh2xLpdjAJ9q49cT03oVrJzyHqypundREMKSkXwwrhMinPtD_7VwCJ7PdtxtIhKdQwxnscUSjnE_J7pTPBx2-Ko3N5hwi_Xw7fm5n80F0C-G_wQIm59AWyl4101NStGRg6oCQEEQqob4jArkmv2G-JmxEbl2l6PCo3y8VGlzGIhdHUWF8tD8ko_EEm1VxLIvPDDBlAOZG-QQ";
////    	String secret = "Mpro-Broker-Channel-Login-Agent360-Oauth-Token-FuLlFILlMenT2.0-Secret-Key";
////    	
////    	try {
////    		Claims claims = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
////    	System.out.println();
////    	} catch (Exception e) {
////			// TODO: handle exception
////    		e.printStackTrace();
////		}
//    	
//    	System.out.println("DEcrypt : " + AESEncryptDecryptUtil.decrypt(encStr, "AuKkUbX706U9wFC3lIo2aaI95cK/vjg="));
//    
//    }

}
