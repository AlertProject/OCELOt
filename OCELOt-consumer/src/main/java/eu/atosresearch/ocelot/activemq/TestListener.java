package eu.atosresearch.ocelot.activemq;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import eu.atosresearch.ocelot.statisticsstorage.StatisticsStorage;
import eu.atosresearch.saett.matching.RelationFinder;
import eu.atosresearch.saett.matching.SemanticMatcher;
import eu.atosresearch.saett.nlp.NLPTool;
import eu.atosresearch.saett.util.FileUtilities;
import eu.atosresearch.saett.util.XMLParser;

public class TestListener implements MessageListener {
	private ConceptRatingListener cl;

	public TestListener(NLPTool nlp,SemanticMatcher sm,int event,StatisticsStorage storage,RelationFinder rf){
		cl=new ConceptRatingListener(nlp, sm, event, storage, rf);
	}

	public void onMessage(Message msg) {
		try {
			String file="D:\\ATOS\\Proyectos\\ALERT\\dev\\event\\newconcept.xml";
			cl.onMessage(msg);
			String event=FileUtilities.file2tring(file);
						
			TextMessage txtMsg = (TextMessage) msg;
			String text=txtMsg.getText();
			XMLParser parser=new XMLParser(text);
			String id=parser.getTextValue("ns1:eventId");
			
			event=event.replaceAll("#ID#", id);
			event=event.replaceAll("#RANDOM#", "OCELOT"+System.currentTimeMillis());
			
			PublishCIM.publish("tcp://93.87.17.115:61616", "ALERT.OCELOt.ConceptNew", event);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
