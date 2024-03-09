package com.nhn.edu.springboot.minidooray.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SaltUtil {
    public static String getSalt() {

        SecureRandom sr = new SecureRandom();
        byte [] salt = new byte[20];

        sr.nextBytes(salt);

        StringBuffer sb = new StringBuffer();
        for(byte b :salt) {
            sb.append(String.format("%02x",b));
        }
        return sb.toString();
    }

    public static String getEncrypt(String password, String salt) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            //            System.out.println("PWD + SALT 적용 전 : " + pwd + salt);
            md.update((password + salt).getBytes());
            byte[] pwdSalt = md.digest();

            StringBuffer sb = new StringBuffer();
            for(byte b : pwdSalt) {
                sb.append(String.format("%02x", b));
            }

            result = sb.toString();
            //            System.out.println("PWD + SALT 적용 후 : " + result);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
