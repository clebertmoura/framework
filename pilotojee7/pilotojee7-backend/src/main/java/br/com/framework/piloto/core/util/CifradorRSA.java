package br.com.framework.piloto.core.util;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * Responsável por criptogragar e decriptografar dados.
 * 
 * @author Unidade de Arquitetura de Software (UAS)
 *
 */
public class CifradorRSA {
	
	/**
	 * Criptografa dados utilizando a chave pública RSA.
	 * 
	 * @param rawText
	 * @param publicKey
	 * @param transformation
	 * @param encoding
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public static String encriptar(String rawText, PublicKey publicKey, String transformation, String encoding)
            throws IOException, GeneralSecurityException {
 
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
 
        return Base64.encodeBase64String(cipher.doFinal(rawText.getBytes(encoding)));
    }
 
	/**
	 * Decriptografa dados baseado na chave privada RSA.
	 * 
	 * @param cipherText
	 * @param privateKey
	 * @param transformation
	 * @param encoding
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
    public static String decriptar(String cipherText, PrivateKey privateKey, String transformation, String encoding)
            throws IOException, GeneralSecurityException {
 
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
 
        return new String(cipher.doFinal(Base64.decodeBase64(cipherText)), encoding);
    }

}