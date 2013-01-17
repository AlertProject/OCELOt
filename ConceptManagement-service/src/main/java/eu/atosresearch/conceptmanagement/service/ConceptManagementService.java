package eu.atosresearch.conceptmanagement.service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import eu.atosresearch.conceptmanagement.activemq.PublishCIM;
import eu.atosresearch.conceptmanagement.tools.FileUtilities;
import eu.atosresearch.conceptmanagement.tools.JDBConector;
import eu.atosresearch.conceptmanagment.config.ConfigParameters;

public class ConceptManagementService {
	private JDBConector jdbc;


	public ConceptManagementService(){
		jdbc=new JDBConector(ConfigParameters.db_server, ConfigParameters.db_database, ConfigParameters.db_user, ConfigParameters.db_password);
	}

	public boolean addNewConcept(String concept,String lemma,String[] sameAs,String[] subClass,String[] superClass,boolean useLemma) throws IOException, ClassNotFoundException, SQLException{
		//public boolean addNewConcept(String concept,String lemma,String sameAs,String subClass,String superClass,String useLemma) throws IOException, ClassNotFoundException, SQLException{
		String event=FileUtilities.file2tring(ConceptManagementService.class.getResourceAsStream("/newconcept.template"));
		long milis=System.currentTimeMillis();

		event=event.replaceAll("#TIMESTAMP#", ""+milis);
		event=event.replaceAll("#EVENTID#", "OCELOt-"+milis);


		String relations="";

		boolean insert=false;

		if(useLemma){
			if(lemma!=null){
				relations+="<rdfs:label xml:lang=\"en\">"+lemma+"</rdfs:label>\n											";
				event=event.replaceAll("#CONCEPTURI#", "http://ailab.ijs.si/alert/resource/"+lemma);
				insert=true;
			}
		}else{
			if(lemma!=null){
				relations+="<rdfs:label xml:lang=\"en\">"+concept+"</rdfs:label>\n											";
				event=event.replaceAll("#CONCEPTURI#", "http://ailab.ijs.si/alert/resource/"+concept);
				insert=true;
			}
		}

		if(sameAs!=null){
			for(String sa:sameAs){
				if(!sa.trim().equals("")){
					relations+="<owl:sameAs rdf:resource=\""+sa+"\" />\n											";
				}
			}
		}
		
		if(subClass!=null){
			for(String sb:subClass){
				if(!sb.trim().equals("")){
					relations+="<rdfs:subClassOf rdf:resource=\""+sb+"\" />\n											";
				}
			}
		}
		


		if(superClass!=null){
			for(String sc:superClass){
				if(!sc.trim().equals("")){
					relations+="<rdfs:superClassOf rdf:resource=\""+sc+"\" />\n											";
				}
			}
		}

		relations=relations.trim();


		event=event.replaceAll("#RELATIONS#", relations);

		jdbc.connect();
		if(!isConceptIncluded(concept, lemma) && insert){
			PublishCIM.publish(ConfigParameters.newconcept_event, event);
			updateIncludedConcept(concept,lemma);

		}
		jdbc.disconnect();			
		return true;
	}

	private boolean isConceptIncluded(String concept,String lemma){
		try{
			ResultSet rs=jdbc.executeSQL("select term_id from ocelot_term where (term_term='"+concept.toLowerCase()+"' OR term_lemma='"+lemma.toLowerCase()+"') and term_isincluded=1");
			while(rs.next())
				return true;
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	private boolean updateIncludedConcept(String concept,String lemma){
		try{
			jdbc.executeUpdate("update ocelot_term set term_isincluded=1 where (term_term='"+concept.toLowerCase()+"' OR term_lemma='"+lemma.toLowerCase()+"')");
			return true;
		}catch (Exception e){
			return false;
		}
	}



}
