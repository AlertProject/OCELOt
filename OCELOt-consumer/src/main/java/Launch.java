import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import javax.jms.JMSException;
import javax.naming.NamingException;

import eu.atosresearch.ocelot.activemq.ConceptRatingListener;
import eu.atosresearch.ocelot.activemq.ConceptRatingProcessor;
import eu.atosresearch.ocelot.activemq.EventConsumer;
import eu.atosresearch.ocelot.activemq.GeneralListener;
import eu.atosresearch.ocelot.activemq.JSIConceptHandler;
import eu.atosresearch.ocelot.activemq.TestListener;
import eu.atosresearch.ocelot.statisticsstorage.StatisticsStorage;
import eu.atosresearch.saett.feeder.RemoveEmoticonsFilter;
import eu.atosresearch.saett.feeder.RemoveExtraSpacesFilter;
import eu.atosresearch.saett.feeder.RemoveURLFilter;
import eu.atosresearch.saett.feeder.RemoveXMLTagsFilter;
import eu.atosresearch.saett.matching.JENAConector;
import eu.atosresearch.saett.matching.RelationFinder;
import eu.atosresearch.saett.matching.SemanticMatcher;
import eu.atosresearch.saett.matching.SesameConector;
import eu.atosresearch.saett.nlp.StanfordNLP;
import eu.atosresearch.saett.nlp.Token;


public class Launch {

	/**
	 * @param args
	 * @throws JMSException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws NamingException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws JMSException, FileNotFoundException, IOException, NamingException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		
		Properties prop=new Properties();
		prop.load(new FileInputStream(/*"D:\\ATOS\\Proyectos\\ALERT\\workspace\\SAETT\\saett.properties"*/"saett.properties"));		
		
		EventConsumer ec=new EventConsumer("OCELOt",prop.getProperty("ocelot.activemq.url"),"org.apache.activemq.jndi.ActiveMQInitialContextFactory",Configuration.ACTIVEMQ_USER,Configuration.ACTIVEMQ_PASS,false);
		
		
		StanfordNLP nlp=new StanfordNLP(prop);
		
		//JENAConector jena=new JENAConector("http://dbpedia.org/sparql");
		//SemanticMatcher sm=new SemanticMatcher(jena,"D:\\ATOS\\Proyectos\\ALERT\\dev\\indexs\\dbpedia-tech");
		
		JENAConector jena=new JENAConector(prop.getProperty("saett.semanticmacher.endpoint"));
		SemanticMatcher sm=new SemanticMatcher(jena,prop.getProperty("saett.semanticmacher.indexfile"));
		
				
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/DataModelingLanguages");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/WebServices");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/XML-basedStandards");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/WebServiceSpecifications");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/WebPortals");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/InternetProtocols");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/InternetStandards");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/JavaSpecificationRequests");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/Encodings");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/InternetMailProtocols");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/CompilingTools");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/JavaDevelopmentTools");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/MicrosoftWindows");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/ProxyServers");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/Identifiers");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/WorldWideWebConsortiumStandards");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/OpenFormats");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/ApplicationLayerProtocols");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/WebBrowsers");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/DataSerializationFormats");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/X86-64LinuxDistributions");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/Debian-basedDistributions");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/SoftwareComponents");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/ComputingPlatforms");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/NamingConventions");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/ProgrammingLanguage106898352");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/VirtualMachines");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/JavaLibraries");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/Servers");
		
		sm.addFilter("http://purl.org/dc/terms/subject", "http://dbpedia.org/resource/Category:Free_software_programmed_in_Java");
		sm.addFilter("http://purl.org/dc/terms/subject", "http://dbpedia.org/resource/Category:Open_source_database_management_systems");
		sm.addFilter("http://purl.org/dc/terms/subject", "http://dbpedia.org/page/Category:Java_APIs");
		sm.addFilter("http://purl.org/dc/terms/subject", "http://dbpedia.org/resource/Category:Enterprise_application_integration");
		sm.addFilter("http://purl.org/dc/terms/subject", "http://dbpedia.org/resource/Category:Web_service_specifications");
		sm.addFilter("http://purl.org/dc/terms/subject", "http://dbpedia.org/resource/Category:World_Wide_Web_Consortium_standards");
		sm.addFilter("http://purl.org/dc/terms/subject", "http://dbpedia.org/resource/Category:Workflow_technology");

		sm.addTextRelation("http://www.w3.org/2000/01/rdf-schema#label");
		sm.addTextRelation("http://dbpedia.org/property/name");
		sm.addTextRelation("http://www.w3.org/2000/01/rdf-schema#comment");
		
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(prop.getProperty("ocelot.AnnotationOntology.terms"))));
		String line="";
		HashMap<String, String> terms=new HashMap<String, String>();
		while((line=br.readLine())!=null){
			if(!line.trim().equals("")){
				terms.put(line.toLowerCase(), "1");
			}
		}
		
		StatisticsStorage ss=new StatisticsStorage(prop.getProperty("ocelot.storage.server"), prop.getProperty("ocelot.storage.user"), prop.getProperty("ocelot.storage.password"), prop.getProperty("ocelot.storage.db"),terms);
		
		SemanticMatcher ao=new SemanticMatcher(new SesameConector("http://localhost:8080", "AO"), prop.getProperty("ocelot.AnnotationOntology.index") /*"D:\\ATOS\\Proyectos\\ALERT\\dev\\indexs\\annotation-ontologyM18"*/);
		ao.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://www.w3.org/2002/07/owl#Class");
		ao.addTextRelation("http://www.w3.org/2000/01/rdf-schema#label");
		ao.addTextRelation("http://ailab.ijs.si/alert/predicate/highlights");
		
		RelationFinder rf=new RelationFinder(jena, ao);
		rf.addTaxonomyRelation("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		rf.addTaxonomyRelation("http://www.w3.org/2000/01/rdf-schema#subClassOf");
		
		
		ConceptRatingListener ml=new ConceptRatingListener(nlp, sm,ConceptRatingProcessor.SCM_EVENT,ss,rf);
		ml.addSelectedPOSTag(Token.POS_NOUN);
		ml.addFilter(new RemoveEmoticonsFilter());
		ml.addFilter(new RemoveURLFilter());
		ml.addFilter(new RemoveXMLTagsFilter());
		ml.addFilter(new RemoveExtraSpacesFilter());

		
		ConceptRatingListener ml2=new ConceptRatingListener(nlp, sm,ConceptRatingProcessor.FORUM_EVENT,ss,rf);
		ml2.addSelectedPOSTag(Token.POS_NOUN);
		ml2.addFilter(new RemoveEmoticonsFilter());
		ml2.addFilter(new RemoveURLFilter());
		ml2.addFilter(new RemoveXMLTagsFilter());
		ml2.addFilter(new RemoveExtraSpacesFilter());
		


		ConceptRatingListener ml3=new ConceptRatingListener(nlp, sm,ConceptRatingProcessor.BTS_EVENT,ss,rf);
		ml3.addSelectedPOSTag(Token.POS_NOUN);
		ml3.addFilter(new RemoveEmoticonsFilter());
		ml3.addFilter(new RemoveURLFilter());
		ml3.addFilter(new RemoveXMLTagsFilter());
		ml3.addFilter(new RemoveExtraSpacesFilter());		
		
		
		ConceptRatingListener ml4=new ConceptRatingListener(nlp, sm,ConceptRatingProcessor.ML_EVENT,ss,rf);		
		ml4.addSelectedPOSTag(Token.POS_NOUN);
		ml4.addFilter(new RemoveEmoticonsFilter());
		ml4.addFilter(new RemoveURLFilter());
		ml4.addFilter(new RemoveXMLTagsFilter());
		ml4.addFilter(new RemoveExtraSpacesFilter());		
		
		ConceptRatingListener mlx=new ConceptRatingListener(nlp, sm,17,ss,rf);		
		mlx.addSelectedPOSTag(Token.POS_NOUN);
		mlx.addFilter(new RemoveEmoticonsFilter());
		mlx.addFilter(new RemoveURLFilter());
		mlx.addFilter(new RemoveXMLTagsFilter());
		mlx.addFilter(new RemoveExtraSpacesFilter());
		
		JSIConceptHandler jsi=new JSIConceptHandler(ss);
		
		TestListener tl=new TestListener(nlp, sm,ConceptRatingProcessor.BTS_EVENT,ss,rf);
		
		
		ec.addSubcriber("ALERT.OCELOt.Load", mlx);
		ec.addSubcriber("ALERT.Metadata.CommitNew.Stored", ml);
		ec.addSubcriber("ALERT.Metadata.ForumPostNew.Stored", ml2);
		ec.addSubcriber("ALERT.Metadata.IssueNew.Stored", ml3);
		//ec.addSubcriber("ALERT.Metadata.IssueNew.Stored", tl);
		//ec.addSubcriber("ALERT.*.IssueUpdate", new GeneralListener());
		ec.addSubcriber("ALERT.Metadata.MailNew.Stored", ml4);
		// De prueba
		ec.addSubcriber("ALERT.Metadata.WikiPostNew.Stored", new GeneralListener());
		ec.addSubcriber("ALERT.KEUI.ConceptNew", jsi);
		//ec.addSubcriber("ALERT.OCELOt.ConceptNew", new GeneralListener());
		
		ec.start();
		

	}

}
