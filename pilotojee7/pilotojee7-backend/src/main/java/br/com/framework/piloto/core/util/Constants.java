/**
 * 
 */
package br.com.framework.piloto.core.util;

import br.com.framework.model.util.Constantes;

/**
 * Classe que contem as contantes do sistema.
 * 
 * @author Cleber
 *
 */
public abstract class Constants extends Constantes {
	
	public static final String CONFIG_FILENAME = "config.properties";

	/**
	 * Constants de tempo
	 */
	public static final long UM_MINUTO_EM_MILLIS = 60000;
	public static final long UMA_HORA_EM_MILLIS = 60 * UM_MINUTO_EM_MILLIS;
	public static final long UM_DIA_EM_MILLIS = 24 * UMA_HORA_EM_MILLIS;
	public static final long UMA_SEMANA_EM_MILLIS = 7 * UM_DIA_EM_MILLIS;

	/**
	 * Formatos de data
	 */
	public static final String FORMAT_ddMMyyyy_HHmmss = "ddMMyyyy HHmmss";

	/**
	 * Constants referentes ao login.
	 */
	public static final int LOGIN_MAX_TENTATIVAS = 3;
	public static final int LOGIN_MAX_TENTATIVAS_IP = 5;
	public static final long LOGIN_TEMPO_BLOQUEIO = 30 * UM_MINUTO_EM_MILLIS;
	public static final long LOGIN_TEMPO_BLOQUEIO_IP = 60 * UM_MINUTO_EM_MILLIS;
	public static final long LOGIN_TEMPO_RENOVA_SENHA = 90 * UM_DIA_EM_MILLIS;
	public static final String LOGIN_REGEX_PATTERN = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=[\\S]+$).{8,})";

	/**
	 * Constantes de processamento
	 */
	public static final String PROCESSAMENTO_PROP_BASEDIR = "app.basedir";
	public static final String PROCESSAMENTO_ENTRADA_DIR = "entrada";
	public static final String PROCESSAMENTO_A_PROCESSAR_DIR = "a_processar";
	public static final String PROCESSAMENTO_EM_PROCESSAMENTO_DIR = "em_processamento";
	public static final String PROCESSAMENTO_PROCESSADO_DIR = "processado";
	public static final String PROCESSAMENTO_RECIBOS_DIR = "recibos";
	public static final String PROCESSAMENTO_ERRO_DIR = "erro";

	public static final String EXTENSAO_ZIP = ".zip";
	public static final String EXTENSAO_SHA = ".sha";

	public static final String ENCODING_UTF8 = "UTF-8";

	/**
	 * Constantes das chaves pública e privada
	 */
	public static final String SERVER_KEYSTORE_BASEDIR = "server.keystore.basedir";
	public static final String SERVER_KEYSTORE_FILE = "server.keystore.file";
	public static final String SERVER_KEYSTORE_PASSWORD = "server.keystore.password";
	public static final String SERVER_KEYSTORE_KEYALIAS = "server.keystore.keyAlias";
	public static final String SERVER_KEYSTORE_KEYPASSWORD = "server.keystore.keyPassword";
	public static final String SERVER_KEYSTORE_KEYCERTIFICATE = "server.keystore.keyCertificate";

	/**
	 * URL do servico MNI de integração
	 */
	public static final String MNI_SERVICE_URL = "mniService.url";
	
	
	public static final String PACOTE_PROJETO = "pacote.projeto";

	// PARAMETROS

	// Timeout para cliente do mni receber resposta do pje
	public static final String PJE_CLIENT_RECEIVE_TIMEOUT = "PJE_CLIENT_RECEIVE_TIMEOUT";
	// Timeout para conexão do cliente com o webservice do mni
	public static final String PJE_CLIENT_CONNECT_TIMEOUT = "PJE_CLIENT_CONNECT_TIMEOUT";
	// reprocessamento de hashes de ações.
	public static final String RECONSTRUIR_ACOES_HASHES = "RECONSTRUIR_ACOES_HASHES";
	// quanto tempo espera para invalidar o ajuizamento no PJE
	public static final String DELAY_VERIFICACAO_PROCESSO = "DELAY_VERIFICACAO_PROCESSO";
	// horario de inicio para ajuizamento
	public static final String AJUIZAMENTO_HORARIO_INICIO = "AJUIZAMENTO_HORARIO_INICIO";
	// horario final para ajuizamento
	public static final String AJUIZAMENTO_HORARIO_FIM = "AJUIZAMENTO_HORARIO_FIM";
	// expressão para definir o horario inicial e final de processamento dos lotes
	public static final String AJUIZAMENTO_HORARIO_EXPRESSION = "AJUIZAMENTO_HORARIO_EXPRESSION";
	
	// parametro S/N para inicio do processamento  
	public static final String INICIAR_AJUIZAMENTO = "INICIAR_AJUIZAMENTO";
	// parametro de intervalo do timer de verificacao de recriação dos timers. EM MINUTOS.	
	public static final String  PERIODO_VERIFICAR_RECRIACAO = "PERIODO_VERIFICAR_RECRIACAO";
	// parametro para verificar necessidade de recriar timers
	public static final String RECRIAR_TIMERS = "RECRIAR_TIMERS";
	
	/**
	 * Constants de lote
	 */
	public static final String LOTE_PATH_DOCUMENTOS = "documentos";
	public static final String LOTE_FILE_DADOS = "dados.txt";
	public static final String LOTE_FILE_CABECALHO = "cabecalho.txt";

	// parametro S/N para inicio do processamento  
	public static final String MSG_INICIAR_AJUIZAMENTO = "Iniciado o ajuizamento da ação.";
}
