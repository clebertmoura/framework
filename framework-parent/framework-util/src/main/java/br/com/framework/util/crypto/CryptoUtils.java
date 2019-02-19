package br.com.framework.util.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;


/**
 * Classe utilitaria para criptografia
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public abstract class CryptoUtils {
	
	public final static int DEFAULT_READ_WRITE_BLOCK_BUFFER_SIZE = 1024;
	public final static String DEFAULT_CIPHER_TRANSFORMATION = "RSA/ECB/PKCS1Padding";
	
	/**
	 * Gera um hash SHA1 da string informada.
	 * @param value
	 * @return
	 */
	public static String getEncryptedSha1(String value) {
		return DigestUtils.sha1Hex(value);
	}
	
	/**
	 * Gera um hash SHA256 da string informada.
	 * 
	 * @param value
	 * @return
	 */
	public static String getEncryptedSha256(String value) {
		return DigestUtils.sha256Hex(value);
	}
	
	/**
	 * @param value
	 * @param salt
	 * @return
	 */
	public static String getEncryptedSha256(String value, String salt) {
		return DigestUtils.sha256Hex(value.concat(salt));
	}

	/**
	 * Criptografa o conteúdo informado utilizando a chave pública.
	 * 
	 * @param content
	 * @param transformation
	 * 	Tipo de transformação. Ex: RSA/ECB/PKCS1Padding. Veja mais opções no link: https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher
	 * @param publicKey
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static byte[] encrypt(byte[] content, String transformation, PublicKey publicKey) throws GeneralSecurityException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ByteArrayInputStream bais = new ByteArrayInputStream(content);
		byte[] encrypted = null;
		try {
			encrypt(bais, baos, transformation, publicKey);
			encrypted = baos.toByteArray(); 
		} finally {
			IOUtils.closeQuietly(bais);
			IOUtils.closeQuietly(baos);
		}
		return encrypted;
	}
	
	/**
	 * Criptografa o conteúdo informado utilizando a chave pública.
	 * Utiliza a transformação padrão: RSA/ECB/PKCS1Padding.
	 * 
	 * @param content
	 * @param publicKey
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static byte[] encrypt(byte[] content, PublicKey publicKey) throws GeneralSecurityException, IOException {
		return encrypt(content, DEFAULT_CIPHER_TRANSFORMATION, publicKey);
	}
	
	/**
	 * Criptografa o {@link InputStream} informado utilizando a chave pública.
	 *  
	 * @param decryptedInput
	 * @param encryptedOutput
	 * @param transformation
	 * 	Tipo de transformação. Ex: RSA/ECB/PKCS1Padding. Veja mais opções no link: https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher 
	 * @param publicKey
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static void encrypt(InputStream decryptedInput, OutputStream encryptedOutput, String transformation, PublicKey publicKey) throws GeneralSecurityException, IOException {
		cipher(decryptedInput, encryptedOutput, transformation, Cipher.ENCRYPT_MODE, publicKey);
	}
	
	/**
	 * Criptografa o {@link InputStream} informado utilizando a chave pública.
	 * Utiliza a transformação padrão: RSA/ECB/PKCS1Padding.
	 * 
	 * @param decryptedInput
	 * @param encryptedOutput
	 * @param publicKey
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static void encrypt(InputStream decryptedInput, OutputStream encryptedOutput, PublicKey publicKey) throws GeneralSecurityException, IOException {
		encrypt(decryptedInput, encryptedOutput, DEFAULT_CIPHER_TRANSFORMATION, publicKey);
	}
	
	/**
	 * Decriptografa o conteúdo informado utilizando a chave privada.
	 * 
	 * @param encryptedContent
	 * @param transformation
	 *  Tipo de transformação. Ex: RSA/ECB/PKCS1Padding. Veja mais opções no link: https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher
	 * @param privateKey
	 * @return
	 *  byte[] conteúdo decriptografado.
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static byte[] decrypt(byte[] encryptedContent, String transformation, PrivateKey privateKey) throws GeneralSecurityException, IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(encryptedContent);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			decrypt(bais, baos, transformation, privateKey);
		} finally {
			IOUtils.closeQuietly(bais);
			IOUtils.closeQuietly(baos);
		}
		return baos.toByteArray();
	}
	
	/**
	 * Decriptografa o conteúdo informado utilizando a chave privada.
	 * Utiliza a transformação padrão: RSA/ECB/PKCS1Padding.
	 * 
	 * @param encryptedContent
	 * @param privateKey
	 * @return
	 * 	byte[] conteúdo decriptografado.
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static byte[] decrypt(byte[] encryptedContent, PrivateKey privateKey) throws GeneralSecurityException, IOException {
		return decrypt(encryptedContent, DEFAULT_CIPHER_TRANSFORMATION, privateKey);
	}
	
	/**
	 * Decriptografa o {@link InputStream} informado utilizando a chave privada.
	 * 
	 * @param encryptedInput
	 * @param decryptedOutput
	 * @param transformation
	 * 	Tipo de transformation, e.g., DES/CBC/PKCS5Padding. See the Cipher section in the Java Cryptography Architecture Standard Algorithm Name Documentation for information about standard transformation names.
	 * @param privateKey
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static void decrypt(InputStream encryptedInput, OutputStream decryptedOutput, String transformation, PrivateKey privateKey) throws GeneralSecurityException, IOException {
		cipher(encryptedInput, decryptedOutput, transformation, Cipher.DECRYPT_MODE, privateKey);
	}
	
	/**
	 * Decriptografa o {@link InputStream} informado utilizando a chave privada.
	 * Utiliza a transformação padrão: RSA/ECB/PKCS1Padding.
	 * 
	 * @param encryptedInput
	 * @param decryptedOutput
	 * @param privateKey
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public static void decrypt(InputStream encryptedInput, OutputStream decryptedOutput, PrivateKey privateKey) throws GeneralSecurityException, IOException {
		decrypt(encryptedInput, decryptedOutput, DEFAULT_CIPHER_TRANSFORMATION, privateKey);
	}
	
	/**
	 * Criptografa/Decriptografa um conteudo de acordp com o cipherMode.
	 * 
	 * @param input
	 * @param output
	 * @param transformation
	 * @param cipherMode
	 * @param key
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	private static void cipher(InputStream input, OutputStream output, String transformation, int cipherMode, Key key) throws GeneralSecurityException, IOException {
		CipherOutputStream cos = null;
		try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(cipherMode, key);
            cos = new CipherOutputStream(output, cipher);
            int count = 0;
            byte[] buffer = new byte[DEFAULT_READ_WRITE_BLOCK_BUFFER_SIZE];
            while ((count = input.read(buffer)) >= 0) {
            	cos.write(buffer, 0, count);
            }
            cos.flush();
        } finally {
        	IOUtils.closeQuietly(cos);
        }
	}
	
	
}
