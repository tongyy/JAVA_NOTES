package com.tongyy.encode;

import java.io.UnsupportedEncodingException;

public class EncodeByteDemo {
	public static void main (String[] args) throws UnsupportedEncodingException {
		String str= "你好123謝ABC";
		showHexString(str,"UTF-8");
		showHexString(str,"BIG5");
		showHexString(str,"IBM-937");
		
	}
	
	private static void showHexString(String str,String encode) throws UnsupportedEncodingException {
		System.out.println(encode + ":" +bytesToHex(str.getBytes(encode)));
		
		for(int i = 0; i < str.length(); i++) {
			String oneChar = str.substring(i, i+1);
			System.out.println(bytesToHex(oneChar.getBytes(encode)) + ":" + oneChar);
		}
		System.out.println("==============================");
	}
	
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	private static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
}
