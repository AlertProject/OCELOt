package eu.atosresearch.ocelot.statisticsstorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import eu.atosresearch.ocelot.tools.AeSimpleMD5;
import eu.atosresearch.saett.conceptextraction.Concept;

public class StatisticsStorage {

	private JDBConector jdbc;
	private HashMap<String, String> current;

	public StatisticsStorage(String url,String user,String pass,String db,HashMap<String, String> current) throws ClassNotFoundException, SQLException{
		jdbc=new JDBConector(url, db, user, pass);
		jdbc.connect();
		this.current=current;
	}

	private int isAlreadyIncluded(Concept c){
		if(current.containsKey(c.getToken().getText().toLowerCase()) || current.containsKey(c.getToken().getLemma().toLowerCase()))
			return 1;
		else 
			return 0;
	}


	public synchronized void addConceptStatistic(Concept c,Date ocurrence_date) throws SQLException{
		//Mirar transacciones
		if(c.getToken().getText().toLowerCase().indexOf("'")<0 && c.getToken().getText().toLowerCase().indexOf("\\")<0 && c.getToken().getText().length()<=100){
			int isincluded=isAlreadyIncluded(c);
			Calendar cal=new GregorianCalendar();
			Calendar cal1=new GregorianCalendar();
			String date_ocurrence=null;
			
			
			String today=cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH);
			if(ocurrence_date!=null){
				cal1.setTime(ocurrence_date);
				date_ocurrence=cal1.get(Calendar.YEAR)+"/"+(cal1.get(Calendar.MONTH)+1)+"/"+cal1.get(Calendar.DAY_OF_MONTH);
			}else{
				date_ocurrence=today;
			}
			
			String md5=AeSimpleMD5.MD5(c.getToken().getPOSTag().charAt(0)+":"+c.getToken().getText().toLowerCase());
			ResultSet rs=jdbc.executeSQL("select term_id from ocelot_term where term_id='"+md5+"'");
			String sameas="";
			for(String s:c.getSemanticConcepts()){
				sameas+=";"+s;
			}
			if(!sameas.equals(""))
				sameas=sameas.substring(1);
			if(rs.next()){
				jdbc.executeUpdate("update ocelot_term set term_sameas='"+sameas+"',term_isincluded="+isincluded+" where term_id='"+md5+"'");
				ResultSet rs1=jdbc.executeSQL("select ocurrence_ocurrences from ocelot_ocurrence where ocurrence_term_id='"+md5+"' and ocurrence_date='"+date_ocurrence+"'");
				if(rs1.next()){
					int act=rs1.getInt("ocurrence_ocurrences");
					jdbc.executeUpdate("update ocelot_ocurrence set ocurrence_ocurrences="+(act+c.getCount())+" where ocurrence_term_id='"+md5+"' and ocurrence_date='"+date_ocurrence+"'");
				}else{
					jdbc.executeUpdate("insert into ocelot_ocurrence(ocurrence_term_id,ocurrence_date,ocurrence_ocurrences) values ('"+md5+"','"+date_ocurrence+"',"+c.getCount()+")");
				}
				storeRelations(md5, c, true);
			}else{
				jdbc.executeUpdate("insert into ocelot_term(term_id,term_term,term_lemma,term_postag,term_sameas,term_isincluded) values ('"+md5+"','"+c.getToken().getText().toLowerCase()+"','"+c.getToken().getLemma().toLowerCase()+"','"+c.getToken().getPOSTag()+"','"+sameas+"',"+isincluded+")");
				jdbc.executeUpdate("insert into ocelot_ocurrence(ocurrence_term_id,ocurrence_date,ocurrence_ocurrences) values ('"+md5+"','"+date_ocurrence+"',"+c.getCount()+")");
				storeRelations(md5, c, false);
			}
		}
	}

	private void storeRelations(String id,Concept c,boolean clean) throws SQLException{
		Calendar cal=new GregorianCalendar();
		String today=cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH);

		if(clean && c.getRelationsType().size()>0){
			jdbc.executeUpdate("delete from ocelot_relation where relation_subject='"+id+"'");
		}
		for(String r:c.getRelationsType()){
			for(String p:c.getRelationsByType(r)){
				//System.out.println("Insertando Relacion");
				jdbc.executeUpdate("insert into ocelot_relation(relation_subject,relation_predicate,relation_type) values ('"+id+"','"+p+"','"+r+"')");
			}
		}
		jdbc.executeUpdate("update ocelot_term set term_lastrelation_update='"+today+"' where term_id='"+id+"'");
	}

	public boolean isRequiredUpdate(Concept c,int deadline) throws SQLException{
		String md5=AeSimpleMD5.MD5(c.getToken().getPOSTag().charAt(0)+":"+c.getToken().getText().toLowerCase());
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.DATE, deadline*-1);
		String past=cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH);
		ResultSet rs=jdbc.executeSQL("select term_id from ocelot_term where term_id='"+md5+"' and term_lastrelation_update>='"+past+"'");
		if(rs.next())
			return false;
		else
			return true;
	}

	public boolean updateIncludedConcept(String concept,String lemma){
		try{
			ResultSet rs=jdbc.executeSQL("select term_id from ocelot_term where term_term='"+concept.toLowerCase()+"' OR term_lemma='"+lemma.toLowerCase()+"'");
			if(rs.next())
				jdbc.executeUpdate("update ocelot_term set term_isincluded=1 where (term_term='"+concept.toLowerCase()+"' OR term_lemma='"+lemma.toLowerCase()+"')");
			else{
				String md5=AeSimpleMD5.MD5("N:"+concept.toLowerCase());
				jdbc.executeUpdate("insert into ocelot_term(term_id,term_term,term_lemma,term_postag,term_sameas,term_isincluded) values ('"+md5+"','"+concept.toLowerCase()+"','"+lemma.toLowerCase()+"','NN','',1)");
			}
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}



}
