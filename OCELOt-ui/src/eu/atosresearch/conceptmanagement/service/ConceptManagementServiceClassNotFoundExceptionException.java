
/**
 * ConceptManagementServiceClassNotFoundExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package eu.atosresearch.conceptmanagement.service;

public class ConceptManagementServiceClassNotFoundExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1344422950409L;
    
    private eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundException faultMessage;

    
        public ConceptManagementServiceClassNotFoundExceptionException() {
            super("ConceptManagementServiceClassNotFoundExceptionException");
        }

        public ConceptManagementServiceClassNotFoundExceptionException(java.lang.String s) {
           super(s);
        }

        public ConceptManagementServiceClassNotFoundExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public ConceptManagementServiceClassNotFoundExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundException msg){
       faultMessage = msg;
    }
    
    public eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundException getFaultMessage(){
       return faultMessage;
    }
}
    