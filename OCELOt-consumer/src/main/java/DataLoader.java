import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import eu.atosresearch.ocelot.activemq.ConceptRatingListener;
import eu.atosresearch.ocelot.activemq.ConceptRatingProcessor;
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
import eu.atosresearch.saett.util.FileUtilities;


public class DataLoader {

	public static void main(String args[]) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{

		Properties prop=new Properties();

		prop.put("saett.nlp", "StanfordNLP");
		prop.put("saett.nlp.stanford.posmodel", "D:\\ATOS\\Proyectos\\ALERT\\dev\\Versions\\ocelot-consumer\\nlpresources\\left3words-wsj-0-18.tagger");
		prop.put("saett.nlp.POSTag", "Noun");

		StanfordNLP nlp=new StanfordNLP(prop);

		JENAConector jena=new JENAConector("http://dbpedia.org/sparql");
		SemanticMatcher sm=new SemanticMatcher(jena,"D:\\ATOS\\Proyectos\\ALERT\\dev\\Versions\\ocelot-consumer\\indexs\\dbpedia-techb");


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

		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("D:\\ATOS\\Proyectos\\ALERT\\dev\\Versions\\ocelot-consumer\\indexs\\annotation-ontologyM18\\terms.txt")));
		String line="";
		HashMap<String, String> terms=new HashMap<String, String>();
		while((line=br.readLine())!=null){
			if(!line.trim().equals("")){
				terms.put(line.toLowerCase(), "1");
			}
		}

		StatisticsStorage ss_optimis=new StatisticsStorage("localhost", "root", "", "ocelot-optimis",terms);
		StatisticsStorage ss_petals=new StatisticsStorage("localhost", "root", "", "ocelot-petals",terms);
		StatisticsStorage ss_kde=new StatisticsStorage("localhost", "root", "", "ocelot-kde",terms);

		SemanticMatcher ao=new SemanticMatcher(new SesameConector("http://localhost:8080", "AO"), "D:\\ATOS\\Proyectos\\ALERT\\dev\\Versions\\ocelot-consumer\\indexs\\annotation-ontologyM18");
		ao.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://www.w3.org/2002/07/owl#Class");
		ao.addTextRelation("http://www.w3.org/2000/01/rdf-schema#label");
		ao.addTextRelation("http://ailab.ijs.si/alert/predicate/highlights");

		RelationFinder rf=new RelationFinder(jena, ao);
		rf.addTaxonomyRelation("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		rf.addTaxonomyRelation("http://www.w3.org/2000/01/rdf-schema#subClassOf");



		ConceptRatingProcessor scm=new ConceptRatingProcessor(nlp, sm,ConceptRatingProcessor.SCM_EVENT,null,rf);
		scm.addSelectedPOSTag(Token.POS_NOUN);
		scm.addFilter(new RemoveEmoticonsFilter());
		scm.addFilter(new RemoveURLFilter());
		scm.addFilter(new RemoveXMLTagsFilter());
		scm.addFilter(new RemoveExtraSpacesFilter());


		ConceptRatingProcessor forum=new ConceptRatingProcessor(nlp, sm,ConceptRatingProcessor.FORUM_EVENT,null,rf);
		forum.addSelectedPOSTag(Token.POS_NOUN);
		forum.addFilter(new RemoveEmoticonsFilter());
		forum.addFilter(new RemoveURLFilter());
		forum.addFilter(new RemoveXMLTagsFilter());
		forum.addFilter(new RemoveExtraSpacesFilter());



		ConceptRatingProcessor bts=new ConceptRatingProcessor(nlp, sm,ConceptRatingProcessor.BTS_EVENT,null,rf);
		bts.addSelectedPOSTag(Token.POS_NOUN);
		bts.addFilter(new RemoveEmoticonsFilter());
		bts.addFilter(new RemoveURLFilter());
		bts.addFilter(new RemoveXMLTagsFilter());
		bts.addFilter(new RemoveExtraSpacesFilter());		


		ConceptRatingProcessor mail=new ConceptRatingProcessor(nlp, sm,ConceptRatingProcessor.ML_EVENT,null,rf);		
		mail.addSelectedPOSTag(Token.POS_NOUN);
		mail.addFilter(new RemoveEmoticonsFilter());
		mail.addFilter(new RemoveURLFilter());
		mail.addFilter(new RemoveXMLTagsFilter());
		mail.addFilter(new RemoveExtraSpacesFilter());		


		String root_folder="D:\\ATOS\\Proyectos\\ALERT\\DAta\\DataLoad\\CIM\\";
		rf.loadTaxonomyCacheFile(root_folder+"relationfinder-cache.jo");
/*
		//OPTIMIS
		bts.setStatisticsStorage(ss_optimis);
		mail.setStatisticsStorage(ss_optimis);
		System.out.println("BTS - OPTIMIS");
		loadFromDirectory(root_folder+"Optimis_ALERT.Metadata.IssueNew.Stored.v2", bts);
		loadFromDirectory(root_folder+"Optimis_ALERT.Metadata.IssueUpdate.Stored.v2", bts);
		System.out.println("MAIL - OPTIMIS");
		loadFromDirectory(root_folder+"Optimis_ALERT.Metadata.MailNew.Stored", mail);

		rf.saveTaxonomyCacheFile(root_folder+"relationfinder-cache.jo");


		//Petals
		bts.setStatisticsStorage(ss_petals);
		mail.setStatisticsStorage(ss_petals);
		scm.setStatisticsStorage(ss_petals);
		System.out.println("BTS - PETALS");
		loadFromDirectory(root_folder+"Petals_ALERT.Metadata.IssueNew.Stored.v2", bts);
		loadFromDirectory(root_folder+"Petals_ALERT.Metadata.IssueUpdate.Stored.v2", bts);
		System.out.println("MAIL - PETALS");
		loadFromDirectory(root_folder+"Petals_ALERT.Metadata.MailNew.Stored", mail);
		System.out.println("SCM - PETALS");
		loadFromDirectory(root_folder+"Petals_ALERT.Metadata.CommitNew.Stored", scm);

		rf.saveTaxonomyCacheFile(root_folder+"relationfinder-cache.jo");


		//KDE */
		bts.setStatisticsStorage(ss_kde);
		mail.setStatisticsStorage(ss_kde);
		scm.setStatisticsStorage(ss_kde);
		forum.setStatisticsStorage(ss_kde);
/*		
		System.out.println("BTS - KDE");
		loadFromDirectory(root_folder+"KDE_ALERT.Metadata.IssueNew.Stored.v2", bts);
		loadFromDirectory(root_folder+"KDE_ALERT.Metadata.IssueUpdate.Stored.v2", bts);

		System.out.println("MAIL - KDE");
		loadFromDirectory(root_folder+"KDE_ALERT.Metadata.MailNew.Stored", mail);

		System.out.println("FORUM - KDE");
		loadFromDirectory(root_folder+"KDE_ALERT.Metadata.ForumPostNew.Stored", forum);
*/
		System.out.println("SCM - KDE");
		loadFromDirectory(root_folder+"KDE_ALERT.Metadata.CommitNew.Stored\\StoredEvents01", scm);
		rf.saveTaxonomyCacheFile(root_folder+"relationfinder-cache.jo");
		loadFromDirectory(root_folder+"KDE_ALERT.Metadata.CommitNew.Stored\\StoredEvents02", scm);
		rf.saveTaxonomyCacheFile(root_folder+"relationfinder-cache.jo");
		loadFromDirectory(root_folder+"KDE_ALERT.Metadata.CommitNew.Stored\\StoredEvents03", scm);
		rf.saveTaxonomyCacheFile(root_folder+"relationfinder-cache.jo");
		loadFromDirectory(root_folder+"KDE_ALERT.Metadata.CommitNew.Stored\\StoredEvents04", scm);
		rf.saveTaxonomyCacheFile(root_folder+"relationfinder-cache.jo");
		loadFromDirectory(root_folder+"KDE_ALERT.Metadata.CommitNew.Stored\\StoredEvents05", scm);
		rf.saveTaxonomyCacheFile(root_folder+"relationfinder-cache.jo");
		loadFromDirectory(root_folder+"KDE_ALERT.Metadata.CommitNew.Stored\\StoredEvents06", scm);
		rf.saveTaxonomyCacheFile(root_folder+"relationfinder-cache.jo");
		loadFromDirectory(root_folder+"KDE_ALERT.Metadata.CommitNew.Stored\\StoredEvents07", scm);
		rf.saveTaxonomyCacheFile(root_folder+"relationfinder-cache.jo");
		loadFromDirectory(root_folder+"KDE_ALERT.Metadata.CommitNew.Stored\\StoredEvents08", scm);
		rf.saveTaxonomyCacheFile(root_folder+"relationfinder-cache.jo");
		loadFromDirectory(root_folder+"KDE_ALERT.Metadata.CommitNew.Stored\\StoredEvents09", scm);
		rf.saveTaxonomyCacheFile(root_folder+"relationfinder-cache.jo");
		loadFromDirectory(root_folder+"KDE_ALERT.Metadata.CommitNew.Stored\\StoredEvents10", scm);
		rf.saveTaxonomyCacheFile(root_folder+"relationfinder-cache.jo");
		loadFromDirectory(root_folder+"KDE_ALERT.Metadata.CommitNew.Stored\\StoredEvents11", scm);
		rf.saveTaxonomyCacheFile(root_folder+"relationfinder-cache.jo");
		loadFromDirectory(root_folder+"KDE_ALERT.Metadata.CommitNew.Stored\\StoredEvents12", scm);
		rf.saveTaxonomyCacheFile(root_folder+"relationfinder-cache.jo");
		loadFromDirectory(root_folder+"KDE_ALERT.Metadata.CommitNew.Stored\\StoredEvents13", scm);

		rf.saveTaxonomyCacheFile(root_folder+"relationfinder-cache.jo");
		System.out.println("THE END");
	}


	public static void loadFromDirectory(String directory,ConceptRatingProcessor cp){
		File dir = new File(directory);
		File[] files = dir.listFiles();
		System.out.println("Loading "+files.length+" events...");
		long ini=System.currentTimeMillis();
		long di=ini;
		for (int i=0; i<files.length; i++) {
			File file=files[i];
			try{
				cp.processString(FileUtilities.file2tring(file.getAbsolutePath()));
				System.out.print(".");
				if(i%1000==0){
					long dfin=System.currentTimeMillis();
					double speed=(double)(i+1)/((double)(dfin-di)/1000.0);
					System.out.println();
					System.out.println("="+new Date()+": Loaded "+(i+1)+" at "+speed+" events/sec");
					di=System.currentTimeMillis();
				}
			}catch (Exception e) {
			}
		}
		long fin=System.currentTimeMillis();
		double speed=(double)files.length/((double)(fin-ini)/1000.0);
		System.out.println("Loaded - "+speed+" events/sec");
	}

}
