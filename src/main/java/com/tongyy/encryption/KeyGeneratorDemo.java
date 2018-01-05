package com.tongyy.encryption;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class KeyGeneratorDemo {

	private PrivateKey privateKey;
	private PublicKey publicKey;

	public KeyGeneratorDemo(int keylength, String publicKeyPath, String privateKeyPath)
			throws NoSuchAlgorithmException, NoSuchProviderException, IOException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(keylength);
		KeyPair pair = keyPairGenerator.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
		Files.write(Paths.get(publicKeyPath), Base64.getEncoder().encodeToString(getPublicKey().getEncoded()).getBytes());
		Files.write(Paths.get(privateKeyPath), Base64.getEncoder().encodeToString(getPrivateKey().getEncoded()).getBytes());

	}

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	public static void main(String[] args) {
		try {
			KeyGeneratorDemo keyGenerator = new KeyGeneratorDemo(2048, "KeyPair/publicKey", "KeyPair/privateKey");
			System.out.println(keyGenerator.getPublicKey());
			System.out.println(new String(keyGenerator.getPrivateKey().getEncoded()));

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}