package com.tongyy.encryption;

import java.security.PrivateKey;
import java.security.PublicKey;

public class HybridCodecDemo {

	public static void main(String[] args) throws Exception {
		String plainText = "Hello World!";

		PrivateKey privateKey = RSAKeyReader.getPrivateKey("KeyPair/privateKey");
		PublicKey publicKey = RSAKeyReader.getPublicKey("KeyPair/publicKey");
		
		RequestCodec requestCodec = new RequestCodec(publicKey);
		String encryptedText = requestCodec.encryptText(plainText);
		String encryptedAESKeyString = requestCodec.getKeyAndIvMap().get("Cl-Aes128-Key");
		String ivBase64 = requestCodec.getKeyAndIvMap().get("Cl-Aes128-Iv");
		
		ResponseCodec responseCodec = new ResponseCodec(privateKey, encryptedAESKeyString, ivBase64);
		String decryptedText = responseCodec.decryptText(encryptedText);
		
		System.out.println("input:" + plainText);
		System.out.println("AES Key:" + encryptedAESKeyString);
		System.out.println("decrypted:" + decryptedText);

	}

}