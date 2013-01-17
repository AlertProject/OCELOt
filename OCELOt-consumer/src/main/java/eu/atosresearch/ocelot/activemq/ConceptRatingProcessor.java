package eu.atosresearch.ocelot.activemq;

import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;

import eu.atosresearch.ocelot.statisticsstorage.StatisticsStorage;
import eu.atosresearch.ocelot.tools.DateTools;
import eu.atosresearch.saett.conceptextraction.Concept;
import eu.atosresearch.saett.conceptextraction.ConceptRating;
import eu.atosresearch.saett.feeder.Filter;
import eu.atosresearch.saett.matching.RelationFinder;
import eu.atosresearch.saett.matching.SemanticMatcher;
import eu.atosresearch.saett.nlp.Document;
import eu.atosresearch.saett.nlp.NLPTool;
import eu.atosresearch.saett.util.XMLParser;

public class ConceptRatingProcessor {
	public final static int FORUM_EVENT=0;
	public final static int BTS_EVENT=1;
	public final static int WIKI_EVENT=2;
	public final static int ML_EVENT=3;
	public final static int SCM_EVENT=4;
	
	public final static int deadline=35;


	private NLPTool nlp;
	private SemanticMatcher matcher;
	private LinkedList<Filter> filters;
	private Vector<Integer> selectedPOSTag;
	private int event;
	private StatisticsStorage storage;
	private RelationFinder rf;
	
	
	public ConceptRatingProcessor(NLPTool nlp,SemanticMatcher sm,int event,StatisticsStorage storage,RelationFinder rf){	
		this.nlp=nlp;
		this.matcher=sm;
		this.event=event;
		this.storage=storage;
		filters=new LinkedList<Filter>();
		selectedPOSTag=new Vector<Integer>();
		this.rf=rf;	
	}
	
	public void setStatisticsStorage(StatisticsStorage storage){
		this.storage=storage;
	}
	
	public void addFilter(Filter filter){
		filters.add(filter);
	}

	public void addSelectedPOSTag(int pos){
		selectedPOSTag.add(pos);
	}
	
	public String applyFilters(String text){
		if(text!=null){
			for(Filter fil:filters){
				text=fil.filter(text);
			}
			return text;
		}
		return null;
	}	
	
	public void processString(String text){
		String txt="";
		int total=0;
		int relation_search=0;		
		long ini=System.currentTimeMillis();
		XMLParser parser;
		Date date_data=null;
		try{

			switch(event){
				case SCM_EVENT:
					parser=new XMLParser(text);
					txt=applyFilters(parser.getTextValue("s:commitMessageLog"));
					date_data=DateTools.parseSCMDate((parser.getTextValue("s:commitDate")));
				break;
				case FORUM_EVENT:
					parser=new XMLParser(text);
					txt=applyFilters(parser.getTextValue("r:subject"))+" "+applyFilters(parser.getTextValue("r:body"));
					date_data=DateTools.parseForumDate((parser.getTextValue("r:time")));
				break;
				case BTS_EVENT:
					parser=new XMLParser(text);
					txt=applyFilters(parser.getTextValue("s:commentText"));
					date_data=DateTools.parseBTSDate((parser.getTextValue("s:commentDate")));
				break;
				case ML_EVENT:
					parser=new XMLParser(text);
					txt=applyFilters(parser.getTextValue("r1:subject"))+" "+applyFilters(parser.getTextValue("r1:content"));
					date_data=DateTools.parseMailDate((parser.getTextValue("r1:date")));
				break;
				default:
					txt=text;
				break;
				
			}//OJO
			txt=txt.replaceAll("=", " ");
			txt=txt.replaceAll("-", " ");
			Document document=new Document(applyFilters(txt));
			LinkedList<Document> documents=new LinkedList<Document>();
			documents.add(document);
			ConceptRating rat=new ConceptRating(documents);
			for(int pos: selectedPOSTag)
				rat.addSelectedPOSTag(pos);
			rat.setNLPTool(nlp);
			rat.setSemanticMatcher(matcher);
			rat.calculate();
			rat.match();
			for(Concept c:rat.getRatingByToken()){
				try{
					if(storage.isRequiredUpdate(c,deadline)){
						rf.findRelations(c, 2);
						relation_search++;
					}
					storage.addConceptStatistic(c,date_data);
					total++;
				}catch(Exception e){
					System.out.println("Store Error:"+c.getToken().getText());
				}
			}

			//System.out.println("All concepts stored");
			
		}catch (Exception e){
			System.out.println("ERROR:");
			System.out.println(txt);
			e.printStackTrace();
		}
		long fin=System.currentTimeMillis();
		//System.out.println("Event "+event+";"+((double)(fin-ini)/1000.0)+";"+((double)relation_search/(double)total));

		
	}	
}
