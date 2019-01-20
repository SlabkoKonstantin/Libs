package ru.mcb.common;

public class Crypter {

	private static final String empty = "";

	private Crypter() {
	}

	// get decrypted string
	public static String decrypt(String key, String value) {
        String result = empty;
        
        Crypto crypto = Crypto.getInstance(key);
        if (crypto != null) {
            if (!value.isEmpty()) {
                if (value.charAt(0) != '+') {
                    // строка зашифрована, надо ее расшифровать                
                    result = crypto.decrypt(value);
                } else {
                    result = value.substring(1); // выделяем сам пароль                
                }
            }
        }
        return result;
    }

	// get encrypted string
	public static String encrypt(String key, String value) {
		String result = empty;

		Crypto crypto = Crypto.getInstance(key);
		if (crypto != null) {
			if (!value.isEmpty()) {
				if (value.charAt(0) == '+') {
					// строка не зашифрована, надо ее зашифровать
					result = value.substring(1); // выделяем сам пароль
					result = crypto.encrypt(result);
				} else {
					result = value;
				}
			}
		} 
		return result;
	}

}
