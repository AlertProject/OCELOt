package eu.atosresearch.ocelot.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class GeneralListener implements MessageListener{

	//@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage txtMsg = (TextMessage) message;
			try {
				System.out.println("===========");
				String text=txtMsg.getText();
				System.out.println(text);
				System.out.println("===========");
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("Tipo raro");
			System.out.println(message.getClass().getCanonicalName());
		}		
	}
}
