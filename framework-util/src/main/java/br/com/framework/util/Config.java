package br.com.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import javax.enterprise.inject.Vetoed;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe utilitária para carregamento de arquivo de propriedades.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
@Vetoed
public class Config implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
	
	private String propFileName;
	private Properties props;
	
	private static HashMap<String, Properties> mapInstances = new HashMap<>();
	

	/**
	 * Construtor
	 * 
	 * @param classLoader
	 * 		Referencia do classloader que contem o arquivo a ser carregado.
	 * @param propFileName
	 * 		Nome do arquivo a ser carregado.
	 */
	public Config(ClassLoader classLoader, String propFileName) {
		this.propFileName = propFileName;
		if (!mapInstances.containsKey(propFileName)) {
			initProps(classLoader);
		} else {
			props = mapInstances.get(propFileName);
		}
	}

	/**
	 * Inicializa o objeto de propriedades.
	 * 
	 * @param classLoader
	 * 		Referencia do classloader que contem o arquivo a ser carregado.
	 */
	private void initProps(ClassLoader classLoader) {
		Enumeration<URL> resources = null;
		try {
			resources = classLoader.getResources(propFileName);
		} catch (IOException e1) {
			LOGGER.error("Nenhum arquivo de propriedades encontrado com nome: {0}", propFileName);
		}
		if (resources != null) {
			props = new Properties();
			while (resources.hasMoreElements()) {
				URL url = resources.nextElement();
				try {
					InputStream inputStream = url.openStream();
					props.load(inputStream);
					mapInstances.put(propFileName, props);
					IOUtils.closeQuietly(inputStream);
				} catch (IOException e) {
					LOGGER.error("Nao foi possivel ler o arquivo de propriedades: {0}", url.toString());
				}
			}
		}
	}

	/**
	 * Retrona o valor da propriedade ou valorPadrao caso não exista.
	 * 
	 * @param nomePropriedade
	 * @param valorPadrao
	 * @return
	 */
	public String getPropriedade(String nomePropriedade, String valorPadrao) {
		return props.getProperty(nomePropriedade, valorPadrao);
	}

	/**
	 * Retrona o valor da propriedade ou nulo caso não exista.
	 * 
	 * @param nomePropriedade
	 * @return
	 */
	public String getPropriedade(String nomePropriedade) {
		return props.getProperty(nomePropriedade);
	}
}
