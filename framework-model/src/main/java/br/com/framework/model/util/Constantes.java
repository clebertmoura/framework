package br.com.framework.model.util;

/**
 * Constants do Model.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public abstract class Constantes {
	

	/**
	 * Nome do arquivo de configuraÃ§Ã£o da aplicaÃ§Ã£o. Este arquivo deve ser criado no projeto 'model' da aplicaÃ§Ã£o.
	 */
	public static final String CONFIG_FILENAME = "config.properties";
	
	/**
	 * Propriedade que indica se o indexador deve ser ativado na aplicaÃ§Ã£o. 
	 */
	public static final String INDEXER_ACTIVE = "indexer.active";
	/**
	 * EndereÃ§o do servidor de indexaÃ§Ã£o
	 */
	public static final String INDEXER_SERVER_ADDRESS = "indexer.server.address";
	/**
	 * Nome da collection de Ã­ndices 
	 */
	public static final String INDEXER_COLLECTION_NAME = "indexer.collection.name";
	
}
