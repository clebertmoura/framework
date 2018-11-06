package br.jus.framework.util.resource;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Classe de recursos utilizado nas mensagens de properties.
 * 
 * @author Unidade de Arquitetura de Software <setic.disis.uas@tjpe.jus.br>
 *
 */
public class MessageResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Nome do recurso a ser lido. */
    private static final String DEFAULT_BUNDLE = "Mensagens";
    
    /** Localização (I18N) default. */
    private static final Locale DEFAULT_LOCALE = new Locale("pt", "BR");

    /** ResourceBundle para controlar os recursos. */
    private ResourceBundle resourceBundle;

    /** O classloader. */
    /*private ClassLoader classLoader;*/
    
    /**
     * A localização (I18N) das mensagens
     */
    /*private Locale locale;*/
    
    
	/**
	 * Constroi um {@link MessageResource}
	 * 
	 * @param bundle
	 * @param language
	 * @param cLoader
	 */
	public MessageResource(String bundle, Locale locale,
			ClassLoader cLoader) {
		super();
		if (bundle == null) {
			bundle = DEFAULT_BUNDLE;
		}
		if (cLoader == null) {
			cLoader = getClass().getClassLoader();
		}
		/*this.classLoader = cLoader;*/
		if (locale == null) {
			locale = DEFAULT_LOCALE;
		} 
		/*this.locale = locale;*/
		
		// inicializa o resource bundle.
		this.resourceBundle = ResourceBundle.getBundle(bundle, locale, cLoader);
	}

	/**
     * Constroi um {@link MessageResource}
     */
    public MessageResource() {
        this(DEFAULT_BUNDLE);
    }
    
    /**
     * Constroi um {@link MessageResource}
     * @param bundle
     */
    public MessageResource(String bundle) {
    	this(bundle, DEFAULT_LOCALE, null);
    }

    /**
     * Constroi um {@link MessageResource}
     * @param classLoader
     */
    public MessageResource(ClassLoader classLoader) {
        this(DEFAULT_BUNDLE, DEFAULT_LOCALE, classLoader);
    }

    /**
     * Retorna uma mensagem a partir da chave passada.
     * 
     * @param key
     *            A chave para recuperar a mensagem.
     * @param params
     *            Parametros passados ao metodo.
     * @return A mensagem recuperada.
     */
    public String get(String key, Object... params) {
        try {
        	String message = MessageFormat.format(
        			this.resourceBundle.getString(key), params);
            return message;
        } catch (MissingResourceException e) {
            return key;
        }
    }
    
    /**
     * Retorna uma mensagem a partir da chave passada.
     * 
     * @param key
     * 	  A chave para recuperar a mensagem.
     * @return
     */
    public String get(String key) {
        return get(key, new Object[0]);
    }
}
