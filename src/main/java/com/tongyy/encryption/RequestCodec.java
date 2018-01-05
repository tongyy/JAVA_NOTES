package com.tongyy.encryption;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class RequestCodec {
	private byte[] originalAESKey;
	private String encryptedAESKey;
	private String iv;
	
	public RequestCodec(PublicKey publicKey) throws Exception {
		this.originalAESKey = generateAESKey(128);
		this.encryptedAESKey = encryptAESKey(originalAESKey, publicKey);
		this.iv = Base64.getEncoder().encodeToString(generateIV());
	}
	

	private byte[] generateAESKey(int aesKeySize) throws Exception {
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(aesKeySize);
		SecretKey secKey = generator.generateKey();
		return secKey.getEncoded();
	}

	public static PrivateKey getPrivateKey(String filename) throws Exception {
		byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	public static PublicKey getPublicKey(String filename) throws Exception {
		byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}

	public String encryptText(String plainText) throws Exception {	
		SecretKey originalKey = new SecretKeySpec(originalAESKey, 0, originalAESKey.length, "AES");
		Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		aesCipher.init(Cipher.ENCRYPT_MODE, originalKey, new IvParameterSpec(Base64.getDecoder().decode(iv)));
		byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes("UTF-8"));
		return Base64.getEncoder().encodeToString(byteCipherText);
	}

	private String encryptAESKey(byte[] plainAESKey, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return Base64.getEncoder().encodeToString(cipher.doFinal(plainAESKey));
	}

	private byte[] generateIV() {
		byte[] iv = new byte[128 / 8];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(iv);
		return iv;
	}
	
	public Map<String, String> getKeyAndIvMap() throws Exception {
		if (encryptedAESKey == null || iv == null)
			throw new Exception("generate key or iv fail");
		Map<String, String> map = new HashMap<String, String>();
		map.put("Cl-Aes128-Key", encryptedAESKey);
		map.put("Cl-Aes128-Iv", iv);
		return map;

	}
}
