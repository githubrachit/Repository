package com.mli.mpro.pasa.models;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
public class HashingUtility {

    public static String hash(String input, Optional<String> salt, Optional<String> algorithm) {
        try {
            String hashAlgorithm = algorithm.filter(a -> !a.isEmpty()).orElse("SHA-256");
            MessageDigest digest = MessageDigest.getInstance(hashAlgorithm);
            if (salt.isPresent() && !salt.get().isEmpty()) {
                input += salt.get();
            }
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error occurred while hashing", e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
