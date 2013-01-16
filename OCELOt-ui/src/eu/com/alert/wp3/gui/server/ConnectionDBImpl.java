package eu.com.alert.wp3.gui.server;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundExceptionException;
import eu.atosresearch.conceptmanagement.service.ConceptManagementServiceIOExceptionException;
import eu.atosresearch.conceptmanagement.service.ConceptManagementServiceSQLExceptionException;
import eu.com.alert.wp3.gui.client.ConnectionDB;
import eu.com.alert.wp3.gui.client.Graphic;
import eu.com.alert.wp3.gui.client.Term;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class ConnectionDBImpl extends RemoteServiceServlet implements ConnectionDB, Serializable
{

	
	private static final long serialVersionUID = 1L;
	DBConnection conn = new DBConnection();
	
	/** *********************************** ConnectionDBImpl *********************************** **/

	/**
	 * Get the terms of the DB
	 *
	 * @param String date => date since which you want to include the terms
	 * @param String isInclude => define if the terms are already includes in the ontology
	 * @return ArrayList<Term> => ArrayList with the Terms returns by the DB
	 */
	public ArrayList<Term> getTerm(String date, String isInclude)
	{	
		ArrayList<Term> term = new ArrayList();
		String dateQuery = fecha(date);
		String term_id = "";
    	String term_term = "";
    	String term_lemma  = "";
    	String term_postag = "";
    	String term_sameas = "";
    	String term_isincluded ="";
    	int ocurrence =0;

		try
		{
			if (conn != null)
			{
				
				System.err.println("if antes de la query");
				
				Statement stm = conn.getConnection().createStatement();
				PreparedStatement pstm = conn.getConnection().prepareStatement("SELECT term_id, term_term, term_lemma, term_postag, term_sameas, term_isincluded, sum( ocurrence_ocurrences ) AS sum"+ 
			               " FROM ocelot_term, ocelot_ocurrence" +
			               " WHERE ocurrence_term_id = term_id"+
			               " AND ocelot_term.term_isincluded " + isInclude+
			               " AND ocelot_ocurrence.ocurrence_date >= '" +dateQuery+
			               "' GROUP BY term_id"+
			               " ORDER BY sum desc limit 100");
				 
				System.err.println("TERM QUERY ----- > "+pstm);
				
				ResultSet res = pstm.executeQuery();
				
				System.err.println("La respuesta es... "+res);  
				
				while (res.next())
		        {
					term_id = res.getString("term_id");
		        	term_term = res.getString("term_term");
		        	term_lemma  = res.getString("term_lemma");
		        	term_postag = res.getString("term_postag");
		        	term_sameas = res.getString("term_sameas");
		        	ocurrence = res.getInt("sum");
		        	term_isincluded = res.getString("term_isincluded");
		        	
		        	Statement stment = conn.getConnection().createStatement();
		        	PreparedStatement query = conn.getConnection().prepareStatement("SELECT *"+ 
					           " FROM ocelot_relation" +
					           " WHERE relation_subject = '" +term_id+"'");
		        			        	
		        	ResultSet resQuery = query.executeQuery();
		        		
		        	String relation_subclass ="";
		        	String relation_superclass="";
		      
		        	while (resQuery.next())
			        {
		        		String type = resQuery.getString("relation_type");
		        		String predicate = resQuery.getString("relation_predicate");

		        		if(type.compareTo("*rdfs:subClassOf")==0)
		        		{
		        			relation_subclass+=predicate;
		        			relation_subclass +=";";
		        		}
		        		else
		        		{
		        			relation_superclass+=predicate;
		        			relation_superclass +=";";
		        		}

			        }
		        	
		        	Term termino = new Term();
		        	termino.setTerm_id(term_id);
		        	termino.setTerm_term(term_term);
		        	termino.setTerm_lemma(term_lemma);
		        	termino.setTerm_postag(term_postag);
		        	termino.setTerm_sameas(term_sameas);
		        	termino.setSubclass(relation_subclass);
		        	termino.setSuperclass(relation_superclass);
		        	termino.setOcurrence(ocurrence);
		        	termino.setIsInclude(term_isincluded);
		        	termino.setUseLemma(false);
		        		        	
		        	term.add(termino);  	 				
		        }

				
			}
			else
			{
				System.err.println("NADA DE CONEXION.. " +conn);
			}		
		}
		catch(Exception e)
		{
			System.err.println("La exception... ");
            System.err.println(e.getMessage());
		}
		System.err.println("RETORNOOOOO AQUI .....");
		return term;
		
	}
	
	/** *********************************** dataGraphic *********************************** **/

	/**
	 * Get the data´s terms to create the graphics.
	 *
	 * @param String date => date since which you want to include the terms
	 * @param String term => term to search the ocurrence
	 * @return ArrayList<Term> => ArrayList with the information to create the chart
	 */
	public ArrayList<Graphic> dataGraphic(String date, String term)
	{
		ArrayList<Graphic> graphic = new ArrayList<Graphic>();
		String dateQuery = fecha(date);
		String dateGraphic = "";
    	int ocurrenceGraphic = 0;
		
		try
		{
			Statement stm = conn.getConnection().createStatement();
	         PreparedStatement pstm = conn.getConnection().prepareStatement("SELECT ocurrence_date, ocurrence_ocurrences" +
	           " FROM ocelot_ocurrence, ocelot_term" +
	           " WHERE term_id = ocurrence_term_id and term_id = '" +term+
	           "' and ocurrence_date >= '"+dateQuery+
	           "' order by ocurrence_date asc "
	           );
	        ResultSet res = pstm.executeQuery();
	        System.err.println("GRAPHIC QUERY  ----> "+pstm);
	        while (res.next())
	        {
	        	dateGraphic = res.getString("ocurrence_date");
	        	ocurrenceGraphic =res.getInt("ocurrence_ocurrences");
	        	Graphic grafico = new Graphic(dateGraphic, ocurrenceGraphic);
	        	
	        	graphic.add(grafico);
	           
	        }
		}
		catch (SQLException e) 
		{
			System.out.println("En el catch");
			e.printStackTrace();
		}
		System.err.println("Grafica.... :)");
		return graphic;
	}
	
	/** *********************************** addAction *********************************** **/

	/**
	 * Invocate the WS to add the term to the ontology
	 *
	 * @param Term termAdd => term that should be include in the ontology
	 * @return String name => name of the Term include to confirm to the user that the operation was performed successfully
	 */
	public String addAction(Term termAdd)
	{
		System.err.println("*********** ADD ACTION SERVICE ***********");
		String name = termAdd.getTerm_term();
		System.err.println(" --------------->"+termAdd.getTerm_sameas()+" <---------------");
		CommunicationWS communication = new CommunicationWS();
		boolean result = false;
		try 
		{
			result = communication.addNewConceptWS(termAdd);
		} 
		catch (RemoteException e) 
		{
			System.err.println("RemoteException"+e);
		} 
		catch (ConceptManagementServiceClassNotFoundExceptionException e) 
		{
			System.err.println("ConceptManagementServiceClassNotFoundExceptionException"+e);
		} 
		catch (ConceptManagementServiceSQLExceptionException e) 
		{
			System.err.println("ConceptManagementServiceSQLExceptionException"+e);
		}
		catch (ConceptManagementServiceIOExceptionException e) 
		{
			System.err.println("ConceptManagementServiceIOExceptionException"+e);
		}
		catch(NoClassDefFoundError e)
		{
			System.err.println("NoClassDefFoundError ----> "+e);
		}
		System.err.println("*********** ADD ACTION RESULT ***********  "+result);
		return name;
	}
	
	public String fecha(String days)
	{
		Calendar fecha = Calendar.getInstance();
		int restar = Integer.parseInt(days);
		fecha.add( Calendar.DATE, - restar );
		int day = fecha.get(Calendar.DAY_OF_MONTH);
		String diaQuery = ""+day;
		if(day <10)
		{
			diaQuery = "0"+day;
		}
		int month = fecha.get(Calendar.MONTH);
		String mesquery = ""+month;
		if(month <10)
		{
			int mes = month+1;
			mesquery = "0"+mes;
		}
		int year = fecha.get(Calendar.YEAR);
		String dateQuery = ""+year+"-"+mesquery+"-"+diaQuery;
		
		return dateQuery;
	}
}
