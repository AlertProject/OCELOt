

/**
 * ConceptManagementService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package eu.atosresearch.conceptmanagement.service;

    /*
     *  ConceptManagementService java interface
     */

    public interface ConceptManagementService {
          

        /**
          * Auto generated method signature
          * 
                    * @param addNewConcept0
                
             * @throws eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundExceptionException : 
             * @throws eu.atosresearch.conceptmanagement.service.ConceptManagementServiceSQLExceptionException : 
             * @throws eu.atosresearch.conceptmanagement.service.ConceptManagementServiceIOExceptionException : 
         */

         
                     public eu.atosresearch.conceptmanagement.service.AddNewConceptResponse addNewConcept(

                        eu.atosresearch.conceptmanagement.service.AddNewConcept addNewConcept0)
                        throws java.rmi.RemoteException
             
          ,eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundExceptionException
          ,eu.atosresearch.conceptmanagement.service.ConceptManagementServiceSQLExceptionException
          ,eu.atosresearch.conceptmanagement.service.ConceptManagementServiceIOExceptionException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param addNewConcept0
            
          */
        public void startaddNewConcept(

            eu.atosresearch.conceptmanagement.service.AddNewConcept addNewConcept0,

            final eu.atosresearch.conceptmanagement.service.ConceptManagementServiceCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    