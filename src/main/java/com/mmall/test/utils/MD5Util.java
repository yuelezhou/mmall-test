package com.mmall.test.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    private static String byteArrayToHexString(byte b[]){
        StringBuffer resultsb = new StringBuffer();
        for(int i=0;i<b.length;i++){
            resultsb.append(byteToHexString(b[i]));
        }
        return resultsb.toString();

    }

    private static String byteToHexString(byte b){
        int n = b;
        if(n < 0){
            n +=256;
        }
        int d1 = n/16;
        int d2 = n%16;
        return hexDigits[d1]+hexDigits[d2];
    }


    private static String MD5Encoding(String origin, String charsetname){
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest messageDigest = null;
            messageDigest = MessageDigest.getInstance("MD5");
            if(charsetname == null || "".equals(charsetname)) {
                resultString = byteArrayToHexString(messageDigest.digest(resultString.getBytes()));
            }
            else {
                resultString = byteArrayToHexString(messageDigest.digest(resultString.getBytes(charsetname)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString.toUpperCase();
    }

    public static String MD5EncodingUTF8(String password){
        return MD5Encoding(password,"utf-8");
    }


    private static final String hexDigits[]={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
}
