import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Vector;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.util.InvalidFormatException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;

import com.hp.hpl.jena.sparql.util.PrintSerializable;

import eu.atosresearch.saett.conceptextraction.Concept;
import eu.atosresearch.saett.conceptextraction.ConceptRating;
import eu.atosresearch.saett.feeder.KDEDocSource;
import eu.atosresearch.saett.feeder.MultiXMLSource;
import eu.atosresearch.saett.feeder.RemoveEmoticonsFilter;
import eu.atosresearch.saett.feeder.RemoveExtraSpacesFilter;
import eu.atosresearch.saett.feeder.RemoveTextFilter;
import eu.atosresearch.saett.feeder.RemoveURLFilter;
import eu.atosresearch.saett.feeder.RemoveXMLTagsFilter;
import eu.atosresearch.saett.feeder.TextSelectionFilter;
import eu.atosresearch.saett.matching.JENAConector;
import eu.atosresearch.saett.matching.RelationFinder;
import eu.atosresearch.saett.matching.SemanticMatcher;
import eu.atosresearch.saett.matching.SesameConector;
import eu.atosresearch.saett.nlp.Document;
import eu.atosresearch.saett.nlp.OpenNLP;
import eu.atosresearch.saett.nlp.Sentence;
import eu.atosresearch.saett.nlp.StanfordNLP;
import eu.atosresearch.saett.nlp.Token;


public class Main {

	/**
	 * @param args
	 * @throws InvalidFormatException 
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ClassNotFoundException 
	 */
	
	
	public void testOpenNLP() throws InvalidFormatException, FileNotFoundException, IOException{
		Properties prop=new Properties();
		prop.setProperty("ocelot.nlp.opennlp.tokernizermodel", "D:\\ATOS\\Proyectos\\Buscamedia\\PT4\\tools\\opennlp-es\\es-token.bin");
		prop.setProperty("ocelot.nlp.opennlp.posmodel", "D:\\ATOS\\Proyectos\\Buscamedia\\PT4\\tools\\opennlp-es\\es-pos-maxent.bin");
		
		OpenNLP nlp=new OpenNLP(prop);
		Document doc=new Document("El Ejército calcula que las Farc tienen 8.147 guerrilleros en camuflado y 10.261 milicianos en las redes de apoyo, es decir, casi 20 mil personas en armas. Habría otros 10 mil en las redes de apoyo, casi siempre familias de guerrilleros que no están armados pero les suministran víveres, los alertan cuando el Ejército está cerca, les dan refugio.");
		nlp.setTarget(doc);
		nlp.process();
		Sentence s=doc.getSentences().get(0);
		for(Token t:s.getTokens()){
			System.out.println(t.getText()+":"+t.getPOSTag());
		}
	}
	
	public void create_BM_index() throws CorruptIndexException, IOException{
		JENAConector dbpedia=new JENAConector("http://dbpedia.org/sparql");
		SemanticMatcher sm=new SemanticMatcher(dbpedia,"D:\\ATOS\\Proyectos\\Buscamedia\\PT4\\dev\\indexs\\buscamedia");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/ontology/Country");
		//sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/ontology/Place");
		//sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/ontology/Person");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/ontology/Sport");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/LaLigaFootballers");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/CurrentNationalLeaders");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/LeadersOfPoliticalParties");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/ontology/FormulaOneRacer");
		sm.addFilter("http://purl.org/dc/terms/subject", "http://dbpedia.org/resource/Category:Formula_One_constructors");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/FormulaOneCircuits");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/ontology/BasketballPlayer");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/ontology/BasketballTeam");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/CapitalsInEurope");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/GeoclassCapitalOfAPoliticalEntity");
		sm.addFilter("http://purl.org/dc/terms/subject", "http://dbpedia.org/resource/Category:Autonomous_communities_of_Spain");
		sm.addFilter("http://purl.org/dc/terms/subject", "http://dbpedia.org/resource/Category:Provinces_of_Spain");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/GeoclassSeatOfAFirst-orderAdministrativeDivision");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/MediterraneanPortCitiesAndTownsInSpain");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/ontology/SoccerManager");
		sm.addFilter("http://purl.org/dc/terms/subject", "http://dbpedia.org/resource/Category:2000s_economic_history");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/PoliticalSystems");
		sm.addFilter("http://purl.org/dc/terms/subject", "http://dbpedia.org/resource/Category:Supraorganizations");
		sm.addFilter("http://purl.org/dc/terms/subject", "http://dbpedia.org/resource/Category:International_military_organizations");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/SpanishRegionalPresidents");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/SpanishWomenInPolitics");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/class/yago/SpanishPoliticians");
		
		
		sm.addTextRelation("http://www.w3.org/2000/01/rdf-schema#label");
		sm.index();				
	}
	
	public void createSO() throws FileNotFoundException{
		SesameConector ses=new SesameConector("http://localhost:8080/openrdf-sesame", "FIRST");
		Vector<HashMap<String, String>> rs=ses.executeSPARQL("select ?u ?l ?t where {?u <http://www.w3.org/2000/01/rdf-schema#label> ?l.?u <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?t}");
		PrintStream ps=new PrintStream("D:\\ATOS\\Proyectos\\FIRST\\WP5\\data\\ontologia\\sentimentobject.csv");
		for(HashMap<String, String> row:rs){
			ps.println(row.get("u")+";"+row.get("l")+";"+row.get("t"));
			ps.flush();
		}
		ps.close();
	}
	
	public void search(String s) throws CorruptIndexException, IOException, ParseException{
		/*SesameConector ses=new SesameConector("http://localhost:8080/openrdf-sesame", "AO");
		SemanticMatcher sm=new SemanticMatcher(ses,"D:\\ATOS\\Proyectos\\ALERT\\dev\\indexs\\annotation-ontologyM18");
		Vector<String> v=sm.search(s, 1);
		System.out.println(v.size());
		System.out.println(v.get(0));*/
		
		JENAConector dbpedia=new JENAConector("http://dbpedia.org/sparql");
		SemanticMatcher sm=new SemanticMatcher(dbpedia,"D:\\ATOS\\Proyectos\\ALERT\\dev\\indexs\\countries");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://dbpedia.org/ontology/Country");
		sm.addTextRelation("http://www.w3.org/2000/01/rdf-schema#label");
		sm.index();
		
		Vector<String> v=sm.search("Colombia", 2);
		for(String sas:v){
			System.out.println(sas);
		}
		
	}
	
	public void insertLabels(){	
		SesameConector ses=new SesameConector("http://localhost:8080/openrdf-sesame", "AO");
		String prefix="PREFIX ex:<http://example.org/>" +
				"PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>" +
				"PREFIX purl:<http://purl.org/dc/terms/>" +
				"PREFIX resource:<http://ailab.ijs.si/alert/resource/>" +
				"PREFIX owl:<http://www.w3.org/2002/27/owl#>" +
				"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
				"PREFIX ailab:<http://ailab.ijs.si/alert/predicate/>" +
				"PREFIX DBpedia:<http://dbpedia.org/ontology/>";
		Vector<HashMap<String, String>> rs=ses.executeSPARQL(prefix+" select ?c ?label where {?c rdfs:label ?label}");
		
		HashMap<String, HashMap<String, String>> terms=new HashMap<String, HashMap<String,String>>();
		
		for(HashMap<String, String> h:rs){
			if(terms.containsKey(h.get("c"))){
				terms.get("c").put(h.get("label"), "1");
			}else{
				HashMap<String, String> map=new HashMap<String, String>();
				map.put(h.get("label"), "1");
				terms.put(h.get("c"), map);
			}
		}		
		rs=ses.executeSPARQL(prefix+" select ?label where {?c ailab:highlights ?label}");
		//HashMap<String, String> terms=new HashMap<String, String>();
		for(HashMap<String, String> h:rs){
			if(terms.containsKey(h.get("c"))){
				terms.get("c").put(h.get("label"), "1");
			}else{
				HashMap<String, String> map=new HashMap<String, String>();
				map.put(h.get("label"), "1");
				terms.put(h.get("c"), map);
			}
		}
		
		for(String uri:terms.keySet()){
			HashMap<String, String> m=terms.get(uri);
			for(String label:m.keySet()){
				System.out.println(uri+":"+label);
			}
			
		}		
	}
	
	public void getTerms() throws FileNotFoundException{
		SesameConector ses=new SesameConector("http://localhost:8080/openrdf-sesame", "AO");
		String prefix="PREFIX ex:<http://example.org/>" +
				"PREFIX rdfs:<http://www.w3.org/2000/01/rdf-schema#>" +
				"PREFIX purl:<http://purl.org/dc/terms/>" +
				"PREFIX resource:<http://ailab.ijs.si/alert/resource/>" +
				"PREFIX owl:<http://www.w3.org/2002/27/owl#>" +
				"PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
				"PREFIX ailab:<http://ailab.ijs.si/alert/predicate/>" +
				"PREFIX DBpedia:<http://dbpedia.org/ontology/>";
		Vector<HashMap<String, String>> rs=ses.executeSPARQL(prefix+" select ?label where {?c rdfs:label ?label}");
		HashMap<String, String> terms=new HashMap<String, String>();
		for(HashMap<String, String> h:rs){
			terms.put(h.get("label").toLowerCase(), "1");
		}
		
		rs=ses.executeSPARQL(prefix+" select ?label where {?c ailab:highlights ?label}");
		//HashMap<String, String> terms=new HashMap<String, String>();
		for(HashMap<String, String> h:rs){
			terms.put(h.get("label").toLowerCase(), "1");
		}
		
		PrintStream ps=new PrintStream("D:\\ATOS\\Proyectos\\ALERT\\dev\\indexs\\annotation-ontologyM18\\terms.txt");
		for(String t:terms.keySet()){
			ps.println(t);
		}
		ps.close();
		System.out.println("Terms:"+terms.size());
		
	}
	
	public void indexOntology() throws CorruptIndexException, IOException{
		SesameConector ses=new SesameConector("http://localhost:8080/openrdf-sesame", "AO");
		SemanticMatcher sm=new SemanticMatcher(ses,"D:\\ATOS\\Proyectos\\ALERT\\dev\\indexs\\annotation-ontologyM18");
		sm.addFilter("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "http://www.w3.org/2002/07/owl#Class");
		sm.addTextRelation("http://www.w3.org/2000/01/rdf-schema#label");
		sm.addTextRelation("http://ailab.ijs.si/alert/predicate/highlights");
		sm.index();		
	}
	
	public void kde(){
		KDEDocSource source=new KDEDocSource("D:\\Documents\\Proyectos\\ALERT\\DAta\\kdeDocs\\kdeDocs");
		source.addFilter(new RemoveEmoticonsFilter());
		source.addFilter(new RemoveExtraSpacesFilter());
		source.addFilter(new RemoveURLFilter());
		source.addFilter(new RemoveXMLTagsFilter());
		source.extract();
		LinkedList<Document> docs=source.getDocs();

		for(Document doc:docs){
			System.out.println("Doc:"+doc.getText());
		}
		System.out.println(docs.size());
	}
	
	public void findRelations() throws FileNotFoundException, IOException, ClassNotFoundException{
		JENAConector jena=new JENAConector("http://dbpedia.org/sparql");
		SesameConector ses=new SesameConector("http://localhost:8080/openrdf-sesame", "AO");
		SemanticMatcher sm=new SemanticMatcher(ses,"D:\\ATOS\\Proyectos\\ALERT\\dev\\indexs\\annotation-ontologyM18");

		
		RelationFinder rf=new RelationFinder(jena,null);
		rf.addTaxonomyRelation("http://www.w3.org/1999/02/22-rdf-syntax-ns#type");
		rf.addTaxonomyRelation("http://www.w3.org/2000/01/rdf-schema#subClassOf");
		Concept c=new Concept(new Token("jejej"));
		c.addSemanticConcept("http://dbpedia.org/resource/Devil-Linux");
		
		Concept c1=new Concept(new Token("jejej"));
		c1.addSemanticConcept("http://dbpedia.org/resource/Devil-Linux");
		
		rf.loadTaxonomyCacheFile("D:\\ATOS\\Proyectos\\ALERT\\DAta\\DataLoad\\rf.jo");
		
		long ini,fin;
		ini=System.currentTimeMillis();
		rf.findRelations(c, 3);
		fin=System.currentTimeMillis();
		System.out.println(fin-ini);
		
		System.out.println(c.getRelationsByType("*rdfs:subClassOf").size());
		System.out.println(c.getRelationsByType("rdfs:subClassOf*").size());
		
		ini=System.currentTimeMillis();
		rf.findRelations(c1, 3);
		fin=System.currentTimeMillis();
		System.out.println(fin-ini);
		rf.saveTaxonomyCacheFile("D:\\ATOS\\Proyectos\\ALERT\\DAta\\DataLoad\\rf.jo");
		
		
		System.out.println(c1.getRelationsByType("*rdfs:subClassOf").size());
		System.out.println(c1.getRelationsByType("rdfs:subClassOf*").size());
		
		
	}
	
	public void execute() throws IOException, ParseException, ClassNotFoundException{
		/*
		Properties prop=new Properties();
		prop.load(this.getClass().getResourceAsStream("saett.properties"));
		
		System.out.println("Load data");
		
		MultiXMLSource source=new MultiXMLSource("D:\\Documents\\Proyectos\\ALERT\\DAta\\nabble-backup-Petals-ESB_001");
		source.addTextTag("field", "name", "subject");
		source.addTextTag("field", "name", "message");
		
		TextSelectionFilter tfil=new TextSelectionFilter();
		tfil.setFrom("Content-Transfer-Encoding: 7bit");
		tfil.setTo("-------------------- m2f --------------------");
		source.addFilter(tfil);
		RemoveTextFilter rt=new RemoveTextFilter(RemoveTextFilter.TO, "X-SA-Exim-Scanned:");
		source.addFilter(rt);
			
		source.addFilter(new RemoveEmoticonsFilter());
		source.addFilter(new RemoveExtraSpacesFilter());
		source.addFilter(new RemoveURLFilter());
		source.addFilter(new RemoveXMLTagsFilter());
		
		source.extract();
		
		//===============
		
		MultiXMLSource source2=new MultiXMLSource("D:\\Documents\\Proyectos\\ALERT\\DAta\\nabble-backup-Petals-ESB_002");
		source2.addTextTag("field", "name", "subject");
		source2.addTextTag("field", "name", "message");
		
		source2.addFilter(tfil);
		source2.addFilter(rt);
			
		source2.addFilter(new RemoveEmoticonsFilter());
		source2.addFilter(new RemoveExtraSpacesFilter());
		source2.addFilter(new RemoveURLFilter());
		source2.addFilter(new RemoveXMLTagsFilter());
		source2.extract();
		
		LinkedList<Document> docs=source.getDocs();
		docs.addAll(source2.getDocs());
		
		System.out.println("Rating");
		
		ConceptRating cr=new ConceptRating(docs);
		cr.setNLPTool(new StanfordNLP(prop));
		cr.addSelectedPOSTag(Token.POS_NOUN);
		cr.calculate();
		
		System.out.println("Index");
		*/				
		JENAConector jena=new JENAConector("http://dbpedia.org/sparql");
		SemanticMatcher sm=new SemanticMatcher(jena,"D:\\Documents\\Proyectos\\ALERT\\dev\\indexs\\dbpedia-tech");
				
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
		sm.index();
		
		/*
		System.out.println("Match");

		cr.setSemanticMatcher(sm);
		cr.match();
		
		cr.writeXML(System.out, false);
		*/
		
	}
	
	public static void main(String[] args) throws IOException, ParseException, ClassNotFoundException {
		// TODO Auto-generated method stub
		new Main().create_BM_index();
	}

}
