package com.YuvrajSingh.chatApp.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface Encryption {

    public static String passwordEncrypt(String plainPassword) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(plainPassword.getBytes());
        // convert to hex
        StringBuilder sb = new StringBuilder(digest.length * 2);
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        String encryptedPassword = sb.toString();
        //System.out.println("Encrypted password: " + encryptedPassword);
        return encryptedPassword;
    }

    
}
