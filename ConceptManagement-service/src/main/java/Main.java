import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import eu.atosresearch.conceptmanagement.service.ConceptManagementService;


public class Main {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException{
		
		String[] sb=new String[]{"http://aaaaa.com#a1","http://aaaaa.com#a1"};
		
		new ConceptManagementService().addNewConcept("amarok","amarok", sb, sb, sb,true);
	}

}
