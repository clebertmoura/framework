package br.com.framework.util.security;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class KeyAndCertificate implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private PrivateKey privateKey;
	private X509Certificate certificate;

	public KeyAndCertificate() {
		super();
	}

	public KeyAndCertificate(PrivateKey privateKey, X509Certificate certificate) {
		super();
		this.privateKey = privateKey;
		this.certificate = certificate;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public X509Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(X509Certificate certificate) {
		this.certificate = certificate;
	}
}