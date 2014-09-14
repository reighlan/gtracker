package psych.server.data.util;

import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Used to Encrypt and Decrypt properties and anything else that happens to use
 * the default encryption. The Decrypt class is is also found in this package.
 * This is not a security risk as the purpose is to hide the keys in the
 * database. The ability to encrypt and decrypt from the app is necessary. This
 * class uses a unique salt with multiple hashing iterations for each encryption
 * which makes rainbow table attacks less effective. It is still password based
 * and thus if an attacker has the code they can decrypt anything. The purpose
 * of this class is to encrypt content in the datastore/database.
 * 
 * @author tobyboyd
 * 
 */
public class PropertyEncryptUtil {

	private static final Log log = LogFactory.getLog(PropertyEncryptUtil.class);

	private final static String password = "ThisIsNot$%#A!Se)r||t";

	/**
	 * Encrypt a string and return in Base64 encoded
	 * 
	 * @param str
	 *            String to be encrypted in UTF8
	 * @return Encrypted String Base64 encoded, not URL friendly
	 * @throws Exception
	 */
	public static String encryptString(String str) throws Exception {
		log.trace("encryptString");

		byte[] salt = SecureRandom.getSeed(8);
		Cipher cipher = initCipher(salt, null, Cipher.ENCRYPT_MODE);
		AlgorithmParameters params = cipher.getParameters();
		// Saved as Base64
		byte[] ciphertext = cipher.doFinal(str.getBytes("UTF-8"));
		byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
		String base64Str = Base64.encodeBase64String(ciphertext);
		String saltStr = Base64.encodeBase64String(salt);
		String returnString = base64Str + "|" + Base64.encodeBase64String(iv) + "|" + saltStr;

		log.debug("Encoded string  base64encodedString|base64encded Initialization Vector|salt:" + returnString);
		return returnString;
	}

	/**
	 * Decyrpt an encrypted property string
	 * 
	 * @param encryptedBase64String
	 *            Encrypted string in the following format. encrypted
	 *            string|initalization vector
	 * @return Unecrypted string
	 * @throws Exception
	 * 		Thrown if the decryption fails
	 */
	public static String decryptString(String encryptedBase64String) throws Exception {
		log.trace("decryptString");
		//
		String[] encryptParts = encryptedBase64String.split("\\|");
		if (encryptParts.length != 3) {
			throw new Exception(
					"Encryption string must be in this format base64encodedString|base64encded Initialization Vector|salt");
		}
		// Convert Base64 Strings to byte arrays
		byte[] byteArray = Base64.decodeBase64(encryptParts[0]);
		byte[] ivByteArray = Base64.decodeBase64(encryptParts[1]);
		byte[] salt = Base64.decodeBase64(encryptParts[2]);

		Cipher cipher = initCipher(salt, new IvParameterSpec(ivByteArray), Cipher.DECRYPT_MODE);
		String plaintext = new String(cipher.doFinal(byteArray), "UTF-8");
		log.debug("Decrypted string:" + plaintext);

		return plaintext;
	}

	/**
	 * Abstraction to reduce changes ot the Cipher getting initlized differently
	 * from copy paste code
	 * 
	 * @param salt
	 *            Salt to be used in the encrypt of decrypt
	 * @param iv
	 *            Only needed for Cipher.DECRYPT_MODE
	 * @param encryptMode
	 *            Cipher.DECRYPT_MODE and Cipher.ENCRYPT_MODE
	 * @return Initialized Cipher object
	 * @throws Exception
	 *             Thrown if the Cipher cannot be initialized
	 */
	private static synchronized Cipher initCipher(byte[] salt, IvParameterSpec iv, int encryptMode) throws Exception {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 6500, 128);
		SecretKey tmp = factory.generateSecret(spec);
		// 128 bit key
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
		/* Encrypt the message. */
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		if (encryptMode == Cipher.DECRYPT_MODE) {
			cipher.init(encryptMode, secret, iv);
		} else {
			cipher.init(encryptMode, secret);
		}
		return cipher;

	}

}
