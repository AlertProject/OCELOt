package eu.atosresearch.ocelot.activemq;

import java.io.PrintStream;
import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedList;

import java.util.Vector;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import eu.atosresearch.ocelot.statisticsstorage.StatisticsStorage;
import eu.atosresearch.saett.conceptextraction.Concept;
import eu.atosresearch.saett.conceptextraction.ConceptRating;
import eu.atosresearch.saett.feeder.Filter;
import eu.atosresearch.saett.matching.RelationFinder;
import eu.atosresearch.saett.matching.SemanticMatcher;
import eu.atosresearch.saett.nlp.Document;
import eu.atosresearch.saett.nlp.NLPTool;
import eu.atosresearch.saett.util.XMLParser;



public class ConceptRatingListener implements MessageListener {

	private ConceptRatingProcessor processor;
	

	public ConceptRatingListener(NLPTool nlp,SemanticMatcher sm,int event,StatisticsStorage storage,RelationFinder rf){	
		processor=new ConceptRatingProcessor(nlp,sm,event,storage,rf);
	}

	public void addFilter(Filter filter){
		processor.addFilter(filter);
	}

	public void addSelectedPOSTag(int pos){
		processor.addSelectedPOSTag(pos);
	}
	

	//@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			TextMessage txtMsg = (TextMessage) message;
			try {
				processor.processString(txtMsg.getText());

			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
