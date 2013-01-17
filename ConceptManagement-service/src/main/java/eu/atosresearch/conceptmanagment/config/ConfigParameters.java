package eu.atosresearch.conceptmanagment.config;

import java.util.Properties;

import eu.atosresearch.conceptmanagement.service.ConceptManagementService;

public class ConfigParameters {
	
	private static Properties prop;

	static{
		try{
			prop=new Properties();
			prop.load(ConceptManagementService.class.getResourceAsStream("/ocelot-service.properties"));
		}catch(Exception e){

		}
	}

	public static final String db_server=prop.getProperty("ocelot.storage.server").trim();//"localhost";
	public static final String db_database=prop.getProperty("ocelot.storage.db").trim();
	public static final String db_user=prop.getProperty("ocelot.storage.user").trim();
	public static final String db_password=prop.getProperty("ocelot.storage.password").trim();

	public static final String activemq_url=prop.getProperty("ocelot.activemq.url").trim();
	public static final String activemq_context=prop.getProperty("ocelot.activemq.context").trim();
	public static final String newconcept_event=prop.getProperty("ocelot.activemq.event").trim();

}
