package eu.atosresearch.ocelot.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import eu.atosresearch.ocelot.statisticsstorage.StatisticsStorage;

public class JSIConceptHandler implements MessageListener {
	private StatisticsStorage ss;
	
	public JSIConceptHandler(StatisticsStorage ss){
		this.ss=ss;
	}

	//@Override
	public void onMessage(Message event) {
		TextMessage txtMsg = (TextMessage) event;
		try {
			String txt=txtMsg.getText();


			while(true){
				int ini=txt.indexOf("<rdf:Description rdf:about=");
				if(ini<0)
					break;		
				txt=txt.substring(ini+28);
				int fin=txt.indexOf("\"");
				String uri=txt.substring(0,fin);
				txt=txt.substring(fin+1);
				String term=uri.split("/")[5];
				ss.updateIncludedConcept(term, term);
			}

		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}



}
