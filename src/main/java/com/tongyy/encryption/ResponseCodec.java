package com.tongyy.encryption;

import java.security.PrivateKey;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ResponseCodec {
	private byte[] decryptedAESKey;
	private byte[] iv; 
	public ResponseCodec(PrivateKey privateKey, String encryptedAESKey, String iv) throws Exception {
		this.decryptedAESKey = decryptAESKey(encryptedAESKey, privateKey);
		this.iv = Base64.getDecoder().decode(iv);
	}

	private byte[] decryptAESKey(String encryptedAESKey, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(Base64.getDecoder().decode(encryptedAESKey));
		
	}


	public String decryptText(String encryptedText) throws Exception {
		SecretKey originalKey = new SecretKeySpec(decryptedAESKey, 0, decryptedAESKey.length, "AES");
		Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		aesCipher.init(Cipher.DECRYPT_MODE, originalKey, new IvParameterSpec(iv));
		byte[] bytePlainText = aesCipher.doFinal(Base64.getDecoder().decode(encryptedText));
		return new String(bytePlainText,"UTF-8");
	}
}
