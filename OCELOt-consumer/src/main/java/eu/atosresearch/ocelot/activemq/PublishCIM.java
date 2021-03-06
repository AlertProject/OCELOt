package eu.atosresearch.ocelot.activemq;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



public class PublishCIM {



	public static void publish(String url,String topicName, String xml){

		Context jndiContext = null;
		TopicConnectionFactory topicConnectionFactory = null;
		TopicConnection topicConnection = null;
		TopicSession topicSession = null;
		Topic topic = null;
		TopicPublisher topicPublisher = null;
		TextMessage message = null;
		final int NUM_MSGS = 1;

		// * Create a JNDI API InitialContext object if none exists yet.

		try {
			Properties env = new Properties( );
			env.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
			env.setProperty(Context.PROVIDER_URL, url);
			env.setProperty("topic." + topicName, topicName);


			jndiContext = new InitialContext(env);
		}catch (NamingException e){
			System.out.println("Could not create JNDI API " + "context: " + e.toString());
			e.printStackTrace();
			System.exit(1);
		}

		// * Look up connection factory and topic. If either does not exist, exit.

		try {


			topicConnectionFactory = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory");
			topic = (Topic) jndiContext.lookup(topicName);
		}catch (NamingException e) {
			System.out.println("JNDI API lookup failed: " + e.toString());
			e.printStackTrace();
			System.exit(1);
		}
		/*
		 * Create connection.
		 * Create session from connection; false means session is not transacted.
		 * Create publisher and text message.
		 * Send messages, varying text slightly.
		 * Finally, close connection.
		 */
		try {
			topicConnection = topicConnectionFactory.createTopicConnection();
			topicSession = topicConnection.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
			topicPublisher = topicSession.createPublisher(topic);
			message = topicSession.createTextMessage();
			for (int i = 0; i < NUM_MSGS; i++) {
				message.setText(xml);
				//System.out.println("Publishing message: " + message.getText());
				java.util.Date date= new java.util.Date();
				//System.out.println("Publishing message at: " + new Timestamp(date.getTime()));
				topicPublisher.publish(message);
			}
		} catch (JMSException e){
			System.out.println("Exception occurred: " + e.toString());
		} finally {
			if(topicConnection != null){
				try{
					topicConnection.close();
				}catch (JMSException e){}
			}
		}
	}	

}
