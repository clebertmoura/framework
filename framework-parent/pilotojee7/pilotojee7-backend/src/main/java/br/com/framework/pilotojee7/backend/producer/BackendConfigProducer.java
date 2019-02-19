/**
 * 
 */
package br.com.framework.pilotojee7.backend.producer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.framework.model.qualifiers.AppConfig;
import br.com.framework.pilotojee7.core.util.Constants;

/**
 * Responsável pela criação do {@link Properties} de configuração da aplicação.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public class BackendConfigProducer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BackendConfigProducer.class);

	/**
	 * 
	 */
	public BackendConfigProducer() {
		// TODO Auto-generated constructor stub
	}
	
	@Produces
	@ApplicationScoped @AppConfig
	public Properties producerConfig() {
		Properties properties = new Properties();
		File userConfig = new File(System.getProperty("user.home"), Constants.CONFIG_FILENAME);
		InputStream inStream = null;
		if (userConfig.exists()) {
			try {
				inStream = new FileInputStream(userConfig);
			} catch (FileNotFoundException e) {
				LOGGER.error(String.format("Erro ao carregar as configurações do arquivo: %s", 
						userConfig.getAbsolutePath()), e);
			}
		} else {
			inStream = getClass().getClassLoader().getResourceAsStream("/" + Constants.CONFIG_FILENAME);
		}
		if (inStream != null) {
			try {
				properties.load(inStream);
				IOUtils.closeQuietly(inStream);
			} catch (IOException e) {
				LOGGER.error("Não foi possível carregar as configurações da aplicação.", e);
			}
		} else {
			LOGGER.error("Arquivo de configuração não foi encontrado.");
		}
		return properties;
	}

}
