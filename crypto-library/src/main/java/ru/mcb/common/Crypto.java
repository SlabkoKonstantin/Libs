package ru.mcb.common;

import java.util.Base64;

import ru.mcb.common.tea.TEA;

/**
 * Crypto Library For encrypting passwords in configuration files
 */
public class Crypto {
		
	TEA tea;

	private static volatile Crypto instance;

	public static Crypto getInstance(String key) {
		Crypto localInstance = instance;
		if (localInstance == null) {
			synchronized (Crypto.class) {
				localInstance = instance;
				if (localInstance == null) {
					instance = localInstance = new Crypto(key);
				}
			}
		}
		return localInstance;
	}

	private Crypto(String key) {
		super();
		this.tea = new TEA(key.getBytes());
	}

	public String encrypt(String original) {
		byte[] crypt = tea.encrypt(original.getBytes());
		return new String(Base64.getEncoder().encode(crypt));
	}

	public String decrypt(String encrypted) {
		byte[] result = tea.decrypt(Base64.getDecoder().decode(encrypted));
		return new String(result);
	}
	
}
