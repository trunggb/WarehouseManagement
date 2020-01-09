package services;

import java.util.Base64;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EncryptionService{
	/**
	 * Encrypt a plain text and then encode using BASE64
	 * @param encryptKey
	 * @param plainText
	 * @return
	 */
	public static String encrypt(String plainText) {
		return Base64.getEncoder().encodeToString(plainText.getBytes());
	}
	
	/**
	 * Decrypt a cipherText text and then decode using BASE64
	 * @param encryptKey
	 * @param cipherText
	 * @return
	 */
	public static String decrypt(String cipherText) {
		byte[] decoded = Base64.getDecoder().decode(cipherText);
		return new String(decoded);
	}
}
