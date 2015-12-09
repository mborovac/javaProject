/**
 * Package holding the Crypto class for the 6th Java homework
 */
package hr.fer.zemris.java.tecaj.hw6.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class used to compute the hash value (message digest) of a file, encrypt a file or decrypt it.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class Crypto {
	
	final protected static int MAX_READ = 4000;
	final protected static char[] HEX_DIGITS = "0123456789abcdef".toCharArray();
	
	/**
	 * Main method of the class, used to run the program.
	 * 
	 * @param args program parameters, can take 2 or 3 parameters, 1st parameter MUST be a command 
	 * (checksha, encrypt or decrypt), 2nd and 3rd must be paths to files
	 * 
	 * All of the possible thrown exceptions are self-explanatory
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException, 
			InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		if(args.length < 2 || args.length > 3) {
			throw new IllegalArgumentException("Expected 2 or 3 arguments");
		}
		if(args[0].equals("checksha")) {
			if(args.length != 2) {
				throw new IllegalArgumentException("Digesting only takes 1 additional argument");
			}
			digest(args[1]);
		} else if(args[0].equals("encrypt")) {
			if(args.length != 3) {
				throw new IllegalArgumentException("Encrypting takes 2 additional arguments");
			}
			encrypt(args[1], args[2]);
		} else if(args[0].equals("decrypt")) {
			if(args.length != 3) {
				throw new IllegalArgumentException("Decrypting takes 2 additional arguments");
			}
			decrypt(args[1], args[2]);
		} else {
			throw new IllegalArgumentException("1st argument can be \"checksha\", \"encrypt\" or \"decrypt\"");
		}
	}
	
	/**
	 * Method takes a hex-encoded message digest provided by user, computes its own message digest, 
	 * compares them and lets the user know whether they are the same. Method uses SHA-1 to compute
	 * the digest.
	 * 
	 * @param source the path to the file whose digest is to be computed
	 * @throws IOException if something is wrong with the data stream
	 * @throws NoSuchAlgorithmException if the selected digest algorithm does not exist
	 */
	private static void digest(String source) throws IOException, NoSuchAlgorithmException {
		System.out.println("Please provide expected sha signature for " + source + ":");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		String expectedSignature = br.readLine();
		byte[] signatureAsBytes = hexToByte(expectedSignature);
		MessageDigest sha = MessageDigest.getInstance("SHA-1");
		
		Path sourcePath = Paths.get(source);
		File sourceFile = sourcePath.toFile();
		if(!sourceFile.canRead()) {
			throw new IllegalArgumentException("Can not read the given file");
		}
		BufferedInputStream fileReader = new BufferedInputStream(new FileInputStream(sourceFile));
		long length = sourceFile.length();
		long currentByte = 0; // for keeping track of where we are in the file, as well as progress monitoring
		
		while (currentByte < length) { // while more of the file can be read
			int amountLeft = (int) (length - currentByte);
			int blockUnit = Math.min(MAX_READ, amountLeft);
			byte[] readBytes = new byte[blockUnit]; // read either MAX_READ, or however much is left in the file
			fileReader.read(readBytes);
			sha.update(readBytes);
			currentByte += blockUnit;
		}
		fileReader.close();
		
		byte[] digestedValue = sha.digest();
		if(compareByteArrays(digestedValue, signatureAsBytes)) {
			System.out.println("Digesting completed. Digest of " + sourceFile.getName() + " matches " +
					"expected digest.");
		} else {
			System.out.println("Digesting completed. Digest of " + sourceFile.getName() + " does not match " +
					"the expected digest. Digest was: \n" + bytesToHex(digestedValue));
		}
	}

	/**
	 * Method used to encrypt a file. Method takes user-given password and vector used in the encryption.
	 * 
	 * @param source the path to the file that is to be encrypted
	 * @param dest the path where the encrypted file should be stored
	 * @throws IOException if something is wrong with the data stream
	 * @throws InvalidKeyException if the given key is wrong
	 * @throws InvalidAlgorithmParameterException if the algorithm parameters are wrong
	 * @throws NoSuchAlgorithmException if the selected algorithm does not exist
	 * @throws NoSuchPaddingException if the selected padding does not exist
	 * @throws IllegalBlockSizeException if the block size is wrong
	 * @throws BadPaddingException if the selected padding is wrong
	 */
	private static void encrypt(String source, String dest) throws IOException, InvalidKeyException, 
			InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, 
			IllegalBlockSizeException, BadPaddingException {
		String pass = getPass();
		String vector = getVector();
		SecretKeySpec keySpec = new SecretKeySpec(hexToByte(pass), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(hexToByte(vector));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
		useCipher(cipher, keySpec, paramSpec, source, dest);
	}
	
	/**
	 * Method used to decrypt a file. Method takes user-given password and vector used in the decryption.
	 * 
	 * @param source the path to the file that is to be decrypted
	 * @param dest the path where the decrypted file should be stored
	 * @throws IOException if something is wrong with the data stream
	 * @throws InvalidKeyException if the given key is wrong
	 * @throws InvalidAlgorithmParameterException if the algorithm parameters are wrong
	 * @throws NoSuchAlgorithmException if the selected algorithm does not exist
	 * @throws NoSuchPaddingException if the selected padding does not exist
	 * @throws IllegalBlockSizeException if the block size is wrong
	 * @throws BadPaddingException if the selected padding is wrong
	 */
	private static void decrypt(String source, String dest) throws IOException, NoSuchAlgorithmException, 
			NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, 
			IllegalBlockSizeException, BadPaddingException {
		String pass = getPass();
		String vector = getVector();
		SecretKeySpec keySpec = new SecretKeySpec(hexToByte(pass), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(hexToByte(vector));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
		useCipher(cipher, keySpec, paramSpec, source, dest);
	}
	
	/**
	 * Private method used to get the user-given password.
	 * 
	 * @return returns the given password
	 * @throws IOException if something is wrong with the data stream
	 */
	private static String getPass() throws IOException {
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		return br.readLine();
	}
	
	/**
	 * Private method used to get the user-given vector.
	 * 
	 * @return returns the given vector
	 * @throws IOException if something is wrong with the data stream
	 */
	private static String getVector() throws IOException {
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
		return br.readLine();
	}
	
	/**
	 * Method used to transform a hexadecimal string in to an array of bytes.
	 * 
	 * @param hexText hexadecimal string
	 * @return returns an array of bytes representing the given hexadecimal string
	 */
	private static byte[] hexToByte(String hexText) {
		if((hexText.length()%2) == 1) {
			throw new IllegalArgumentException("Given string is not hex-encoded.");
		}
		int textLength = hexText.length();
	    byte[] byteArray = new byte[textLength / 2];
	    for (int i = 0; i < textLength; i += 2) {
	    	byteArray[i / 2] = (byte) ((Character.digit(hexText.charAt(i), 16) << 4)
	                             + Character.digit(hexText.charAt(i+1), 16));
	    }
		return byteArray;
	}
	
	/**
	 * Method used to transform an array of bytes in to a hexadecimal string.
	 * 
	 * @param byteArray a array of bytes
	 * @return returns a hexadecimal string representation of the given array of bytes
	 */
	public static String bytesToHex(byte[] byteArray) {
		int arraylength = byteArray.length;
	    char[] hexChars = new char[arraylength * 2];
	    for (int i = 0; i < arraylength; i++) {
	        int v = byteArray[i] & 0xFF;
	        hexChars[i * 2] = HEX_DIGITS[v >>> 4];
	        hexChars[i * 2 + 1] = HEX_DIGITS[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	/**
	 * Private method used to compare 2 arrays of bytes.
	 * 
	 * @param array1 1st array to be compared
	 * @param array2 2nd array to be compared
	 * @return returns true of the arrays hold exactly the same bytes, false otherwise
	 */
	private static boolean compareByteArrays(byte[] array1, byte[] array2) {
		if(array1.length != array2.length) {
			return false;
		} else {
			for(int i = 0; i < array1.length; i++) {
				if(array1[i] != array2[i]) {
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * Method used to encrypt/decrypt a file depending on the given cipher.
	 * 
	 * @param cipher cipher that will be run
	 * @param keySpec given secret key
	 * @param paramSpec given algorithm parameters
	 * @param source source file
	 * @param dest path where the new file will be stored
	 * @throws IOException if something is wrong with the data stream 
	 * @throws IllegalBlockSizeException if the block size is wrong
	 * @throws BadPaddingException if the selected padding is wrong
	 */
	private static void useCipher(Cipher cipher, SecretKeySpec keySpec, AlgorithmParameterSpec paramSpec, 
			String source, String dest) throws IOException, IllegalBlockSizeException, BadPaddingException {
		
		Path sourcePath = Paths.get(source);
		File sourceFile = sourcePath.toFile();
		BufferedInputStream fileReader = new BufferedInputStream(new FileInputStream(sourceFile));
		
		Path destPath = Paths.get(dest);
		File destFile = destPath.toFile();
		BufferedOutputStream fileWriter = new BufferedOutputStream(new FileOutputStream(destFile));
		long length = sourceFile.length();
		long currentByte = 0; // for keeping track of where we are in the file, as well as progress monitoring
		
		while (currentByte < length) { // while more of the file can be read
			int amountLeft = (int) (length - currentByte);
			int blockUnit = Math.min(MAX_READ, amountLeft);
			byte[] readBytes = new byte[blockUnit]; // read either MAX_READ, or however much is left in the file
			fileReader.read(readBytes);
			byte[] a = cipher.update(readBytes);
			fileWriter.write(a);
			currentByte += blockUnit;
		}
		fileReader.close();
		fileWriter.close();
		cipher.doFinal();
	}
}
