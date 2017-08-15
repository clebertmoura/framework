#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * 
 */
package ${package}.core.manager;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Cleber Moura <cleber.t.moura@gmail.com>.t.moura
 *
 */
@Singleton
public class MailManager implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8638241699559098987L;

	private static final Logger logger = Logger.getLogger(MailManager.class.getName());

	@Resource(mappedName = "java:jboss/mail/Default")
	private Session mailSession;

	/**
	 * 
	 */
	public MailManager() {
	}

	/**
	 * Envia o e-mail.
	 * 
	 * @param to
	 * 	Lista de e-mails, separados por vírgula.
	 * @param from
	 * @param subject
	 * @param textContent
	 * @param htmlContent
	 * @throws MessagingException
	 */
	public void sendEmail(String to, String from, String subject, String textContent, String htmlContent) throws MessagingException {
		if (logger.isLoggable(Level.FINE)) {
			logger.fine(String.format("Enviando e-mail, de: %s, para: %s, assunto: %s", from, to, subject));
		}
		try {
			Message message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			if (htmlContent != null) {
				message.setContent(htmlContent, "text/html");
			} else if (textContent != null) {
				message.setContent(textContent, "text/plain");
			}
			
			Transport.send(message);

			if (logger.isLoggable(Level.FINE)) {
				logger.fine("E-mail enviado");
			}
		} catch (MessagingException e) {
			logger.log(Level.SEVERE, "Erro ao enviar e-mail.", e);
			throw e;
		}
	}

}
