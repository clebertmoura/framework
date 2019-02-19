package br.com.framework.util.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSProcessableFile;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe utilitária para assinatura digital.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class SignerUtil {

	public static final String DEFAULT_PROVIDER = BouncyCastleProvider.PROVIDER_NAME;
	public static final String DEFAULT_SIG_ALG = "SHA1withRSA";
	
	private static Logger LOGGER = LoggerFactory.getLogger(SignerUtil.class);
	
	private static SignerUtil INSTANCE = null;

	public SignerUtil() {
		if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
			Security.addProvider(new BouncyCastleProvider());
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Registrando o provider do BouncyCastle.");
			}
		}
	}
	
	/**
	 * Retorna uma instancia singleton de {@link SignerUtil}.
	 * @return
	 */
	public static SignerUtil get() {
		if (INSTANCE == null){
			INSTANCE = new SignerUtil();
		}
		return INSTANCE;
	}
	

	/**
	 * Gera a assinatura do arquivo para o assinante informado.
	 * 
	 * @param streamToSign
	 * @param encapsulate
	 * 	indica se o conteúdo do arquivo deve ser encapsulado no container PKCS7.
	 * @param signatureAlgorithm
	 * @param providerName
	 * @param privateKey
	 * @param certificate
	 * @return
	 * @throws IOException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws CertStoreException
	 * @throws CMSException
	 */
	public byte[] sign(InputStream streamToSign, boolean encapsulate, String signatureAlgorithm, String providerName, PrivateKey privateKey, X509Certificate certificate) throws IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CertStoreException, CMSException {
		byte[] signedContent = null;
		File userDir = FileUtils.getUserDirectory();
		if (userDir != null && userDir.canWrite()) {
			String tempFileName = UUID.randomUUID().toString() + ".sig";
			File tempFile = new File(userDir, tempFileName);
			FileUtils.touch(tempFile);
			try {
				FileUtils.copyInputStreamToFile(streamToSign, tempFile);
				signedContent = sign(tempFile, encapsulate, signatureAlgorithm, providerName, privateKey, certificate);
			} finally {
				IOUtils.closeQuietly(streamToSign);
				FileUtils.deleteQuietly(tempFile);
			}
		} else {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(streamToSign, baos);
			signedContent = sign(baos.toByteArray(), encapsulate, signatureAlgorithm, providerName, privateKey, certificate);
			IOUtils.closeQuietly(streamToSign);
			IOUtils.closeQuietly(baos);
		}
		return signedContent;
	}
	
	/**
	 * Gera a assinatura do arquivo para o assinante informado.
	 * 
	 * @param streamToSign
	 * @param encapsulate
	 * 	indica se o conteúdo do arquivo deve ser encapsulado no container PKCS7.
	 * @param privateKey
	 * @param certificate
	 * @return
	 * @throws CMSException 
	 * @throws IOException 
	 * @throws CertStoreException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 */
	public byte[] sign(InputStream streamToSign, boolean encapsulate, PrivateKey privateKey, X509Certificate certificate) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CertStoreException, IOException, CMSException {
		return sign(streamToSign, encapsulate, DEFAULT_SIG_ALG, DEFAULT_PROVIDER, privateKey, certificate);
	}
	
	/**
	 * Gera a assinatura do arquivo para o assinante informado.
	 *  
	 * @param dataToSign
	 * @param encapsulate
	 * 	indica se o conteúdo do arquivo deve ser encapsulado no container PKCS7.
	 * @param signatureAlgorithm
	 * @param providerName
	 * @param privateKey
	 * @param certificate
	 * @return
	 * @throws CertStoreException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws CertificateEncodingException
	 * @throws OperatorCreationException
	 * @throws CMSException
	 * @throws IOException
	 */
	public byte[] sign(byte[] dataToSign, boolean encapsulate, String signatureAlgorithm, String providerName, PrivateKey privateKey, X509Certificate certificate) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CertStoreException, CMSException, IOException  {
		CMSProcessable data = new CMSProcessableByteArray(dataToSign);
		return sign(data, encapsulate, signatureAlgorithm, providerName, privateKey, certificate);
	}
	
	/**
	 * Gera a assinatura do arquivo para o assinante informado.
	 *  
	 * @param dataToSign
	 * @param encapsulate
	 * 	indica se o conteúdo do arquivo deve ser encapsulado no container PKCS7.
	 * @param privateKey
	 * @param certificate
	 * @return
	 * @throws IOException 
	 * @throws CMSException 
	 * @throws CertStoreException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 */
	public byte[] sign(byte[] dataToSign, boolean encapsulate, PrivateKey privateKey, X509Certificate certificate) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CertStoreException, CMSException, IOException {
		return sign(dataToSign, encapsulate, DEFAULT_SIG_ALG, DEFAULT_PROVIDER, privateKey, certificate);
	}

	/**
	 * Gera a assinatura do arquivo para o assinante informado.
	 * 
	 * @param fileToSign
	 * @param encapsulate
	 * 	indica se o conteúdo do arquivo deve ser encapsulado no container PKCS7.
	 * @param signatureAlgorithm
	 * @param providerName
	 * @param privateKey
	 * @param certificate
	 * @return
	 * @throws IOException 
	 * @throws CMSException 
	 * @throws CertStoreException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 */
	public byte[] sign(File fileToSign, boolean encapsulate, String signatureAlgorithm, String providerName, PrivateKey privateKey, X509Certificate certificate) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CertStoreException, CMSException, IOException {
		CMSProcessable data = new CMSProcessableFile(fileToSign);
		return sign(data, encapsulate, signatureAlgorithm, providerName, privateKey, certificate);
	}
	
	/**
	 * Gera a assinatura do arquivo para o assinante informado.
	 * 
	 * @param fileToSign
	 * @param encapsulate
	 * 	indica se o conteúdo do arquivo deve ser encapsulado no container PKCS7.
	 * @param privateKey
	 * @param certificate
	 * @return
	 * @throws CertStoreException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws CertificateEncodingException
	 * @throws OperatorCreationException
	 * @throws CMSException
	 * @throws IOException
	 */
	public byte[] sign(File fileToSign, boolean encapsulate, PrivateKey privateKey, X509Certificate certificate) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CertStoreException, CMSException, IOException {
		return sign(fileToSign, encapsulate, DEFAULT_SIG_ALG, DEFAULT_PROVIDER, privateKey, certificate);
	}
	
	/**
	 * Gera a assinatura para o conteúdo encapsulado no {@link CMSTypedData} para o assinante informado.
	 * 
	 * @param cmsProcessable
	 * @param encapsulate
	 * 	indica se o conteúdo do arquivo deve ser encapsulado no container PKCS7.
	 * @param signatureAlgorithm
	 * @param providerName
	 * @param privateKey
	 * @param certificate
	 * @return
	 * @throws CMSException 
	 * @throws CertStoreException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws IOException 
	 */
	public byte[] sign(CMSProcessable cmsProcessable, boolean encapsulate, String signatureAlgorithm, String providerName, PrivateKey privateKey, X509Certificate certificate) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CertStoreException, CMSException, IOException  {
		CMSSignedDataGenerator sigDataGen = new CMSSignedDataGenerator();
		addSignerInfo(sigDataGen, signatureAlgorithm, providerName, privateKey, certificate);
		return sign(sigDataGen, cmsProcessable, encapsulate);
	}
	
	/**
	 * Gera a assinatura para o conteúdo encapsulado no {@link CMSTypedData}. 
	 * O {@link CMSSignedDataGenerator} deve ter sido inicializado previamente com os assinantes.
	 * 
	 * @param sigDataGen
	 * @param cmsProcessable
	 * @param encapsulate
	 * 	indica se o conteúdo do arquivo deve ser encapsulado no container PKCS7.
	 * @return
	 * @throws CMSException
	 * @throws IOException
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 */
	public byte[] sign(CMSSignedDataGenerator sigDataGen, CMSProcessable cmsProcessable, boolean encapsulate) throws CMSException, IOException, NoSuchAlgorithmException, NoSuchProviderException {
		CMSSignedData sigData = sigDataGen.generate(cmsProcessable, encapsulate, DEFAULT_PROVIDER);
		return sigData.getEncoded();
	}
	
	/**
	 * Adiciona as informações do assinante ao {@link CMSSignedDataGenerator}.
	 * 
	 * @param sigDataGen
	 * @param signatureAlgorithm
	 * @param providerName
	 * @param privateKey
	 * @param certificate
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws CMSException 
	 * @throws CertStoreException 
	 * 
	 */
	public void addSignerInfo(CMSSignedDataGenerator sigDataGen, String signatureAlgorithm, String providerName, 
			PrivateKey privateKey, X509Certificate certificate) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, CertStoreException, CMSException {
		sigDataGen.addSigner(privateKey, certificate, signatureAlgorithm);
		CertStore certStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(Arrays.asList(certificate)), providerName);
		sigDataGen.addCertificatesAndCRLs(certStore);
	}
	
	
	/**
	 * Verifica se a assinatura foi realizada pelo certificado informado.
	 *  
	 * @param signedData
	 * @param signerCert
	 * @param providerName
	 * @return
	 * @throws CMSException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 */
	public boolean verify(byte[] signedData, X509Certificate signerCert, String providerName) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
		InputStream is = new ByteArrayInputStream(signedData);
		try {
			return verify(is, signerCert, providerName);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
	
	/**
	 * Verifica se a assinatura foi realizada pelo certificado informado.
	 * 
	 * @param signedData
	 * @param signerCert
	 * @return
	 * @throws CMSException 
	 * @throws NoSuchProviderException 
	 * @throws NoSuchAlgorithmException 
	 */
	public boolean verify(byte[] signedData, X509Certificate signerCert) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
		return verify(signedData, signerCert, DEFAULT_PROVIDER);
	}
	
	/**
	 *  Verifica se a assinatura foi realizada pelo certificado informado.
	 *  
	 * @param signedStream
	 * @param signerCert
	 * @param providerName
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException  
	 * @throws CMSException 
	 */
	@SuppressWarnings("rawtypes")
	public boolean verify(InputStream signedStream, X509Certificate signerCert, String providerName) throws NoSuchAlgorithmException, NoSuchProviderException, CMSException {
		CMSSignedData s = new CMSSignedData(signedStream);
		SignerInformationStore signers = s.getSignerInfos();
		Iterator signersIt = signers.getSigners().iterator();
		boolean verify = false;
		while (signersIt.hasNext()) {
			SignerInformation signer = (SignerInformation) signersIt.next();
			try {
				verify = signer.verify(signerCert, providerName);
			} catch (CertificateNotYetValidException e) {
				LOGGER.error("Certificado ainda não era válido no momento da assinatura.", e);
			} catch (CertificateExpiredException e) {
				LOGGER.error("Certificado expirado no momento da assinatura.", e);
			}
			if (verify) {
				break;
			}
		}
		return verify;
	}
	
}
