package eu.atosresearch.saett.nlp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

public class OpenNLP implements NLPTool{
	private Document doc;
	private SentenceDetectorME sdetector;
	private TokenizerME tokenizer;
	POSTaggerME tagger; 

	public OpenNLP(Properties prop) throws InvalidFormatException, FileNotFoundException, IOException{
		//sdetector=new SentenceDetectorME(new SentenceModel(new FileInputStream(prop.getProperty("ocelot.nlp.opennlp.sentencemodel"))));
		tokenizer=new TokenizerME(new TokenizerModel(new FileInputStream(prop.getProperty("ocelot.nlp.opennlp.tokernizermodel"))));
		tagger=new POSTaggerME(new POSModel(new FileInputStream(prop.getProperty("ocelot.nlp.opennlp.posmodel"))));
	}


	public void process() {
		// TODO Auto-generated method stub
		Sentence sen=new Sentence(doc.getText().toLowerCase());
		String[] tokens=tokenizer.tokenize(doc.getText());
		String[] tags=tagger.tag(tokens);
		for(int i=0;i<tokens.length;i++){
			Token t=new Token(tokens[i]);
			t.setPOSTag(tags[i]);
			t.setLemma(tokens[i]);
			sen.addToken(t);
		}
		doc.addSentence(sen);	
	}

	public void setTarget(Document doc) {
		// TODO Auto-generated method stub
		this.doc=doc;

	}

}
