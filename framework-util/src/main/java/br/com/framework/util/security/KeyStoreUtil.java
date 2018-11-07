package br.com.framework.util.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.util.exception.InvalidKeystorePassword;
import br.com.framework.util.exception.InvalidPrivateKeyPassword;

/**
 * Classe utilitária para assinatura digital.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class KeyStoreUtil {

	public static final String DEFAULT_PROVIDER = BouncyCastleProvider.PROVIDER_NAME;
	public static final String DEFAULT_SIG_ALG = "SHA1withRSA";
	
	private static Logger LOGGER = LoggerFactory.getLogger(KeyStoreUtil.class);
	
	private static KeyStoreUtil INSTANCE = null;

	/**
	 * Retorna uma instancia singleton de {@link KeyStoreUtil}.
	 * @return
	 */
	public static KeyStoreUtil get() {
		if (INSTANCE == null){
			INSTANCE = new KeyStoreUtil();
		}
		return INSTANCE;
	}
	

	/**
	 * Carrega o {@link KeyStore}
	 * 
	 * @param keyStoreType
	 * 	Tipo do keystore (JKS, PKCS12, etc)
	 * @param keyStoreData
	 * 	Conteúdo do arquivo de keystore codificado em Base64.
	 * @param keystorePin
	 * 	Senha do keystore.
	 * @return
	 * @throws IOException
	 * 	Caso não seja possível carregar o arquivo de keystore.
	 * @throws InvalidKeystorePassword
	 * 	Caso a senha informada para o keystore esteja inválida.
	 */
	public KeyStore loadKeystore(String keyStoreType, String keyStoreData, char[] keystorePin) throws IOException, InvalidKeystorePassword {
		ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decodeBase64(keyStoreData));
		return loadKeystore(keyStoreType, bais, keystorePin);
	}
	
	
	/**
	 * Carrega o {@link KeyStore}
	 * 
	 * @param keyStoreType
	 * 	Tipo do keystore (JKS, PKCS12, etc)
	 * @param inStream
	 * 	InputStream
	 * @param keystorePin
	 * 	Senha do keystore.
	 * @return
	 * @throws IOException
	 * 	Caso não seja possível carregar o arquivo de keystore.
	 * @throws InvalidKeystorePassword
	 * 	Caso a senha informada para o keystore esteja inválida.
	 */
	public KeyStore loadKeystore(String keyStoreType, InputStream inStream, char[] keystorePin) throws IOException, InvalidKeystorePassword {
		KeyStore ks = null;
		try {
			ks = KeyStore.getInstance(keyStoreType);
			ks.load(inStream, keystorePin);
		} catch (KeyStoreException e) {
			LOGGER.error("Formato de keystore não suportado pelos providers.", e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Algorítimo não suportado pelo provider.", e);
		} catch (CertificateException e) {
			LOGGER.error("Certificado não pode ser lido do keystore.", e);
		} catch (IOException e) {
			Throwable cause = e.getCause();
			if (cause instanceof UnrecoverableKeyException || cause instanceof BadPaddingException) {
				throw new InvalidKeystorePassword(cause);
			} else {
				throw e;
			}
		}
		return ks;
	}
	
	/**
	 * Carrega um objeto da classe {@link KeyAndCertificate} que agrupa a chave privada e o certificado contido no keystore.
	 * 
	 * @param keyStoreType
	 * 	Tipo do keystore (JKS, PKCS12, etc)
	 * @param keyStoreData
	 * 	Conteúdo do arquivo de keystore codificado em Base64.
	 * @param keyStorePin
	 * 	Senha do {@link KeyStore}.
	 * @param privateKeyPin
	 * 	Senha da {@link PrivateKey}
	 * @return
	 * @throws InvalidKeystorePassword
	 * 	Caso a senha informada para o keystore esteja inválida.
	 * @throws KeyStoreException
	 * 	Caso o keyStore não tenha sido inicializado.
	 * @throws InvalidPrivateKeyPassword
	 * 	Caso a senha informada para a chave privada esteja inválida.
	 * @throws IOException
	 * 	Caso não seja possível carregar o arquivo de keystore.
	 * 
	 */
	public KeyAndCertificate loadKeyAndCertificate(String keyStoreType, String keyStoreData, char[] keyStorePin, char[] privateKeyPin) throws InvalidKeystorePassword, KeyStoreException, InvalidPrivateKeyPassword, IOException {
		KeyStore keystore = loadKeystore(keyStoreType, keyStoreData, keyStorePin);
		return loadKeyAndCertificate(keystore, keyStorePin, privateKeyPin);
	}
	
	/**
	 * Carrega um objeto da classe {@link KeyAndCertificate} que agrupa a chave privada e o certificado contido no keystore.
	 * 
	 * @param keyStore
	 * 	O {@link KeyStore} inicializado.
	 * @param keyStorePin
	 * 	Senha do {@link KeyStore}.
	 * @param privateKeyPin
	 * 	Senha da {@link PrivateKey}
	 * @return
	 * @throws KeyStoreException
	 * @throws InvalidPrivateKeyPassword
	 * @throws IOException
	 */
	public KeyAndCertificate loadKeyAndCertificate(KeyStore keyStore,
			char[] keyStorePin, char[] privateKeyPin) throws KeyStoreException, InvalidPrivateKeyPassword, IOException {
		KeyAndCertificate keyAndCertificate = null;
		PrivateKey privateKey = null;
		X509Certificate x509Cert = null;
		if (keyStore != null) {
			try {
				Enumeration<String> aliases = keyStore.aliases();
				while (aliases.hasMoreElements()) {
					String alias = aliases.nextElement();
					privateKey = loadPrivateKey(keyStore, alias, privateKeyPin);
					if (privateKey != null) {
						x509Cert = (X509Certificate) loadCertificate(keyStore, alias);
						break;
					}
				}
			} catch (KeyStoreException e) {
				LOGGER.error("O keystore não foi inicializado.", e);
				throw e;
			} catch (InvalidPrivateKeyPassword e) {
				LOGGER.error("A senha da chave privada está inválida.", e);
				throw e;
			}
		}
		if (privateKey != null && x509Cert != null) {
			keyAndCertificate = new KeyAndCertificate(privateKey, x509Cert);
		} else {
			throw new IOException("A chave não foi carregada.");
		}
		return keyAndCertificate;
	}
	
	/**
	 * Carrega a chave privada do keyStore.
	 * 
	 * @param keyStore
	 * 	O {@link KeyStore} inicializado.
	 * @param alias
	 * @param privateKeyPin
	 * 	Senha da {@link PrivateKey}
	 * @return
	 * @throws KeyStoreException
	 * @throws InvalidPrivateKeyPassword
	 */
	public PrivateKey loadPrivateKey(KeyStore keyStore, String alias, char[] privateKeyPin) throws KeyStoreException, InvalidPrivateKeyPassword {
		PrivateKey privateKey = null;
		try {
			Key key = keyStore.getKey(alias, privateKeyPin);
			if (key != null && key instanceof PrivateKey) {
				privateKey = (PrivateKey) key;
			}
		} catch (UnrecoverableKeyException e) {
			LOGGER.error("O pin informado está incorreto.", e);
			throw new InvalidPrivateKeyPassword(e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Algorítimo não suportado pelo provider.", e);
		}
		return privateKey;
	}
	
	/**
	 * Carrega a cadeia de certificação referente ao alias informado. 
	 * 
	 * @param keyStore
	 * 	O {@link KeyStore} inicializado.
	 * @param alias
	 * @return
	 * @throws KeyStoreException
	 * 	Caso o keystore não tenha sido inicializado.
	 */
	public Certificate[] loadCertificateChain(KeyStore keyStore, String alias) throws KeyStoreException {
		return keyStore.getCertificateChain(alias);
	}
	
	/**
	 * Carrega o certificado referente ao alias informado. 
	 * 
	 * @param keyStore
	 * 	O {@link KeyStore} inicializado.
	 * @param alias
	 * @return
	 * @throws KeyStoreException
	 * 	Caso o keystore não tenha sido inicializado.
	 */
	public Certificate loadCertificate(KeyStore keyStore, String alias) throws KeyStoreException {
		Certificate[] chain = loadCertificateChain(keyStore, alias);
		if (chain != null && chain.length > 0) {
			return chain[0];
		} else {
			return keyStore.getCertificate(alias);	
		}
	}
	
}
