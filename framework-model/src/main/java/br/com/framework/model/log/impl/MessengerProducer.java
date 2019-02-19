package br.com.framework.model.log.impl;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class MessengerProducer {

	private static final Logger LOGGER = Logger.getLogger(MessengerProducer.class.getName());

	@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory cf;

	@Resource(mappedName = "java:/jms/queue/LogQueue")
	private Destination destination;

	private Connection connection;
	private Session session;
	private MessageProducer messageProducer;

	@PostConstruct
	public void init() {
		try {
			connection = cf.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			messageProducer = session.createProducer(destination);
		} catch (JMSException e) {
			LOGGER.info(e.getMessage());
		}
	}

	@PreDestroy
	public void destroy() {
		if (connection != null) {
			try {
				connection.close();
			} catch (JMSException e) {
				LOGGER.info(e.getMessage());
			}
		}
	}

	public void sendToJMSQueue(Object object) {
		TextMessage message = null;
		try {
			message = session.createTextMessage(object.toString());
			messageProducer.send(message);
		} catch (JMSException e) {
			LOGGER.info(e.getMessage());
		}
	}
}
