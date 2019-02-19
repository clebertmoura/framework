package br.com.framework.model.error.api;

/**
 * Enum que indica a camada onde ocorreu o erro. 
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 */
public enum ErrorLayer {
	
	GLOBAL("Global"),
	BEAN_VALIDATION("Bean Validation"),
	PERSISTENCE("Persistence"),
	BUSINESS("Business");
	
	private String detail;

	private ErrorLayer(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return this.detail;
	}
	
}
