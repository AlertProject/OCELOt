package eu.com.alert.wp3.gui.server;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import eu.atosresearch.conceptmanagement.service.AddNewConcept;
import eu.atosresearch.conceptmanagement.service.AddNewConceptResponse;
import eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundExceptionException;
import eu.atosresearch.conceptmanagement.service.ConceptManagementServiceIOExceptionException;
import eu.atosresearch.conceptmanagement.service.ConceptManagementServiceSQLExceptionException;
import eu.atosresearch.conceptmanagement.service.ConceptManagementServiceStub;
import eu.com.alert.wp3.gui.client.Term;

public class CommunicationWS 
{
	public CommunicationWS()
	{
		System.out.println(" *********** Communication with WS *********** ");
	}
	
	public boolean addNewConceptWS(Term addTerm) throws AxisFault, RemoteException, ConceptManagementServiceClassNotFoundExceptionException, ConceptManagementServiceSQLExceptionException, ConceptManagementServiceIOExceptionException
	{
		
		ConceptManagementServiceStub stub;
		
		ConfigDB config = new ConfigDB();
		String[] getEndPoint = config.readFile();

		
		String endPoint;
		endPoint = getEndPoint[4];
		//="http://192.168.252.18:8080/axis2/services/ConceptManagementService?wsdl";
		
		System.out.println(" *********** THE ENDPOINT *********** "+endPoint+" <----");
		
		stub = new ConceptManagementServiceStub(endPoint);
		
		AddNewConcept add=new AddNewConcept();
		
		String concept = addTerm.getTerm_term();
		add.setConcept(concept);
		
		String lemma = addTerm.getTerm_lemma();
		add.setLemma(lemma);
		
		String sameAsData = addTerm.getTerm_sameas();
		String[] sameAs = sameAsData.split(";");
		add.setSameAs(sameAs);
		
		String superData = addTerm.getSuperclass();
		String[] superClass = superData.split(";");
		add.setSuperClass(superClass);
		
		String subData = addTerm.getSuperclass();
		String[] subClass = subData.split(";");
		add.setSubClass(subClass);
		
		add.setUseLemma(true);
		
		System.out.println(" *********** ANADO LOS DATOS AL STUB*********** ");
		
		AddNewConceptResponse response = stub.addNewConcept(add);
		
		System.out.println(" *********** ENVIADO AL WS *********** ");
		
		boolean ws_return = response.get_return();
		
		System.out.println(" *********** OBTENIDO RESULTADO *********** "+ws_return);
		
		return ws_return;
	}
}
