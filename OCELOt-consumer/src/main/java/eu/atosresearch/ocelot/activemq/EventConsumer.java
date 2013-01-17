package eu.atosresearch.ocelot.activemq;

import java.util.HashMap;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.network.DurableConduitBridge;

public class EventConsumer {
	private String clientid;
	private String url;
	private String user;
	private String driver;
	private String password;
	private HashMap<String, MessageListener> listeners;
	private Context jndiContext;
	private TopicConnectionFactory topicConnectionFactory;
	private TopicConnection topicConnection = null;
	private TopicSession topicSession = null;
	private boolean durable;

	public EventConsumer(String clientid,String url, String driver, String user, String password,boolean durable) throws NamingException, JMSException{
		this.clientid=clientid;
		this.url=url;
		this.driver=driver;
		this.user=user;
		this.password=password;
		listeners=new HashMap<String, MessageListener>();
		this.durable=durable;
	}

	public void addSubcriber(String topicName, MessageListener listener) throws JMSException, NamingException{
		listeners.put(topicName, listener);
	}

	public void start() throws JMSException, NamingException{
		Properties env = new Properties();
		env.setProperty(Context.INITIAL_CONTEXT_FACTORY,driver);
		env.setProperty(Context.PROVIDER_URL,url);
		for(String event:listeners.keySet()){
			env.setProperty("topic."+event,event);
		}
		jndiContext = new InitialContext(env);
		topicConnectionFactory = (TopicConnectionFactory) jndiContext.lookup("TopicConnectionFactory");
		topicConnection = topicConnectionFactory.createTopicConnection();
		topicConnection.setClientID(clientid);
		topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		
		for(String event:listeners.keySet()){
			Topic topic=(Topic)jndiContext.lookup(event);
			TopicSubscriber subscriber=null;
			if(durable)
				subscriber=topicSession.createDurableSubscriber(topic, clientid+"."+event);
			else
				subscriber=topicSession.createSubscriber(topic);
			subscriber.setMessageListener(listeners.get(event));
		}
		
		topicConnection.start();
	}

	public void stop() throws JMSException{
		topicConnection.stop();
	}



}
