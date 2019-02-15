package br.com.framework.model.util;

/**
 * Constants do Model.
 * 
 * @author Cleber Moura <cleber.t.moura@gmail.com>
 *
 */
public abstract class Constants {
	

	/**
	 * Nome do arquivo de configuração da aplicação. Este arquivo deve ser criado no projeto 'model' da aplicação.
	 */
	public static final String CONFIG_FILENAME = "config.properties";
	
	/**
	 * Propriedade que indica se o indexador deve ser ativado na aplicação. 
	 */
	public static final String INDEXER_ACTIVE = "indexer.active";
	/**
	 * Endereço do servidor de indexação
	 */
	public static final String INDEXER_SERVER_ADDRESS = "indexer.server.address";
	/**
	 * Nome da collection de Índices 
	 */
	public static final String INDEXER_COLLECTION_NAME = "indexer.collection.name";
	
}
