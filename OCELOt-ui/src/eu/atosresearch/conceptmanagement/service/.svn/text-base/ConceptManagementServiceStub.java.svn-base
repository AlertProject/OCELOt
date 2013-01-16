
/**
 * ConceptManagementServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
        package eu.atosresearch.conceptmanagement.service;

        

        /*
        *  ConceptManagementServiceStub java implementation
        */

        
        public class ConceptManagementServiceStub extends org.apache.axis2.client.Stub
        implements ConceptManagementService{
        protected org.apache.axis2.description.AxisOperation[] _operations;

        //hashmaps to keep the fault mapping
        private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
        private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
        private java.util.HashMap faultMessageMap = new java.util.HashMap();

        private static int counter = 0;

        private static synchronized java.lang.String getUniqueSuffix(){
            // reset the counter if it is greater than 99999
            if (counter > 99999){
                counter = 0;
            }
            counter = counter + 1; 
            return java.lang.Long.toString(java.lang.System.currentTimeMillis()) + "_" + counter;
        }

    
    private void populateAxisService() throws org.apache.axis2.AxisFault {

     //creating the Service with a unique name
     _service = new org.apache.axis2.description.AxisService("ConceptManagementService" + getUniqueSuffix());
     addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[1];
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("http://service.conceptmanagement.atosresearch.eu", "addNewConcept"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[0]=__operation;
            
        
        }

    //populates the faults
    private void populateFaults(){
         
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://service.conceptmanagement.atosresearch.eu","ConceptManagementServiceClassNotFoundException"), "addNewConcept"),"eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundExceptionException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://service.conceptmanagement.atosresearch.eu","ConceptManagementServiceClassNotFoundException"), "addNewConcept"),"eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundExceptionException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://service.conceptmanagement.atosresearch.eu","ConceptManagementServiceClassNotFoundException"), "addNewConcept"),"eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundException");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://service.conceptmanagement.atosresearch.eu","ConceptManagementServiceSQLException"), "addNewConcept"),"eu.atosresearch.conceptmanagement.service.ConceptManagementServiceSQLExceptionException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://service.conceptmanagement.atosresearch.eu","ConceptManagementServiceSQLException"), "addNewConcept"),"eu.atosresearch.conceptmanagement.service.ConceptManagementServiceSQLExceptionException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://service.conceptmanagement.atosresearch.eu","ConceptManagementServiceSQLException"), "addNewConcept"),"eu.atosresearch.conceptmanagement.service.ConceptManagementServiceSQLException");
           
              faultExceptionNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://service.conceptmanagement.atosresearch.eu","ConceptManagementServiceIOException"), "addNewConcept"),"eu.atosresearch.conceptmanagement.service.ConceptManagementServiceIOExceptionException");
              faultExceptionClassNameMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://service.conceptmanagement.atosresearch.eu","ConceptManagementServiceIOException"), "addNewConcept"),"eu.atosresearch.conceptmanagement.service.ConceptManagementServiceIOExceptionException");
              faultMessageMap.put(new org.apache.axis2.client.FaultMapKey(new javax.xml.namespace.QName("http://service.conceptmanagement.atosresearch.eu","ConceptManagementServiceIOException"), "addNewConcept"),"eu.atosresearch.conceptmanagement.service.ConceptManagementServiceIOException");
           


    }

    /**
      *Constructor that takes in a configContext
      */

    public ConceptManagementServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
       java.lang.String targetEndpoint)
       throws org.apache.axis2.AxisFault {
         this(configurationContext,targetEndpoint,false);
   }


   /**
     * Constructor that takes in a configContext  and useseperate listner
     */
   public ConceptManagementServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
        java.lang.String targetEndpoint, boolean useSeparateListener)
        throws org.apache.axis2.AxisFault {
         //To populate AxisService
         populateAxisService();
         populateFaults();

        _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext,_service);
        
	
        _serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(
                targetEndpoint));
        _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);
        
            //Set the soap version
            _serviceClient.getOptions().setSoapVersionURI(org.apache.axiom.soap.SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);
        
    
    }

    /**
     * Default Constructor
     */
    public ConceptManagementServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {
        
                    this(configurationContext,"http://172.24.55.76:8080/axis2/services/ConceptManagementService.ConceptManagementServiceHttpSoap12Endpoint/" );
                
    }

    /**
     * Default Constructor
     */
    public ConceptManagementServiceStub() throws org.apache.axis2.AxisFault {
        
                    this("http://172.24.55.76:8080/axis2/services/ConceptManagementService.ConceptManagementServiceHttpSoap12Endpoint/" );
                
    }

    /**
     * Constructor taking the target endpoint
     */
    public ConceptManagementServiceStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(null,targetEndpoint);
    }



        
                    /**
                     * Auto generated method signature
                     * 
                     * @see eu.atosresearch.conceptmanagement.service.ConceptManagementService#addNewConcept
                     * @param addNewConcept2
                    
                     * @throws eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundExceptionException : 
                     * @throws eu.atosresearch.conceptmanagement.service.ConceptManagementServiceSQLExceptionException : 
                     * @throws eu.atosresearch.conceptmanagement.service.ConceptManagementServiceIOExceptionException : 
                     */

                    

                            public  eu.atosresearch.conceptmanagement.service.AddNewConceptResponse addNewConcept(

                            eu.atosresearch.conceptmanagement.service.AddNewConcept addNewConcept2)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundExceptionException
                        ,eu.atosresearch.conceptmanagement.service.ConceptManagementServiceSQLExceptionException
                        ,eu.atosresearch.conceptmanagement.service.ConceptManagementServiceIOExceptionException{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
              _operationClient.getOptions().setAction("urn:addNewConcept");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    addNewConcept2,
                                                    optimizeContent(new javax.xml.namespace.QName("http://service.conceptmanagement.atosresearch.eu",
                                                    "addNewConcept")), new javax.xml.namespace.QName("http://service.conceptmanagement.atosresearch.eu",
                                                    "addNewConcept"));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             eu.atosresearch.conceptmanagement.service.AddNewConceptResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (eu.atosresearch.conceptmanagement.service.AddNewConceptResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addNewConcept"))){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addNewConcept"));
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addNewConcept"));
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundExceptionException){
                          throw (eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundExceptionException)ex;
                        }
                        
                        if (ex instanceof eu.atosresearch.conceptmanagement.service.ConceptManagementServiceSQLExceptionException){
                          throw (eu.atosresearch.conceptmanagement.service.ConceptManagementServiceSQLExceptionException)ex;
                        }
                        
                        if (ex instanceof eu.atosresearch.conceptmanagement.service.ConceptManagementServiceIOExceptionException){
                          throw (eu.atosresearch.conceptmanagement.service.ConceptManagementServiceIOExceptionException)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                if (_messageContext.getTransportOut() != null) {
                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                }
            }
        }
            
                /**
                * Auto generated method signature for Asynchronous Invocations
                * 
                * @see eu.atosresearch.conceptmanagement.service.ConceptManagementService#startaddNewConcept
                    * @param addNewConcept2
                
                */
                public  void startaddNewConcept(

                 eu.atosresearch.conceptmanagement.service.AddNewConcept addNewConcept2,

                  final eu.atosresearch.conceptmanagement.service.ConceptManagementServiceCallbackHandler callback)

                throws java.rmi.RemoteException{

              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
             _operationClient.getOptions().setAction("urn:addNewConcept");
             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              


              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env=null;
              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();

                    
                                    //Style is Doc.
                                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    addNewConcept2,
                                                    optimizeContent(new javax.xml.namespace.QName("http://service.conceptmanagement.atosresearch.eu",
                                                    "addNewConcept")), new javax.xml.namespace.QName("http://service.conceptmanagement.atosresearch.eu",
                                                    "addNewConcept"));
                                                
        // adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // create message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message context to the operation client
        _operationClient.addMessageContext(_messageContext);


                    
                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
                            try {
                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
                                
                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
                                                                         eu.atosresearch.conceptmanagement.service.AddNewConceptResponse.class,
                                                                         getEnvelopeNamespaces(resultEnv));
                                        callback.receiveResultaddNewConcept(
                                        (eu.atosresearch.conceptmanagement.service.AddNewConceptResponse)object);
                                        
                            } catch (org.apache.axis2.AxisFault e) {
                                callback.receiveErroraddNewConcept(e);
                            }
                            }

                            public void onError(java.lang.Exception error) {
								if (error instanceof org.apache.axis2.AxisFault) {
									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
									org.apache.axiom.om.OMElement faultElt = f.getDetail();
									if (faultElt!=null){
										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addNewConcept"))){
											//make the fault by reflection
											try{
													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addNewConcept"));
													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
													//message class
													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"addNewConcept"));
														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
															new java.lang.Class[]{messageClass});
													m.invoke(ex,new java.lang.Object[]{messageObject});
													
													if (ex instanceof eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundExceptionException){
														callback.receiveErroraddNewConcept((eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundExceptionException)ex);
											            return;
										            }
										            
													if (ex instanceof eu.atosresearch.conceptmanagement.service.ConceptManagementServiceSQLExceptionException){
														callback.receiveErroraddNewConcept((eu.atosresearch.conceptmanagement.service.ConceptManagementServiceSQLExceptionException)ex);
											            return;
										            }
										            
													if (ex instanceof eu.atosresearch.conceptmanagement.service.ConceptManagementServiceIOExceptionException){
														callback.receiveErroraddNewConcept((eu.atosresearch.conceptmanagement.service.ConceptManagementServiceIOExceptionException)ex);
											            return;
										            }
										            
					
										            callback.receiveErroraddNewConcept(new java.rmi.RemoteException(ex.getMessage(), ex));
                                            } catch(java.lang.ClassCastException e){
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddNewConcept(f);
                                            } catch (java.lang.ClassNotFoundException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddNewConcept(f);
                                            } catch (java.lang.NoSuchMethodException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddNewConcept(f);
                                            } catch (java.lang.reflect.InvocationTargetException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddNewConcept(f);
                                            } catch (java.lang.IllegalAccessException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddNewConcept(f);
                                            } catch (java.lang.InstantiationException e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddNewConcept(f);
                                            } catch (org.apache.axis2.AxisFault e) {
                                                // we cannot intantiate the class - throw the original Axis fault
                                                callback.receiveErroraddNewConcept(f);
                                            }
									    } else {
										    callback.receiveErroraddNewConcept(f);
									    }
									} else {
									    callback.receiveErroraddNewConcept(f);
									}
								} else {
								    callback.receiveErroraddNewConcept(error);
								}
                            }

                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
                                onError(fault);
                            }

                            public void onComplete() {
                                try {
                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
                                } catch (org.apache.axis2.AxisFault axisFault) {
                                    callback.receiveErroraddNewConcept(axisFault);
                                }
                            }
                });
                        

          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
        if ( _operations[0].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
          _operations[0].setMessageReceiver(
                    _callbackReceiver);
        }

           //execute the operation client
           _operationClient.execute(false);

                    }
                


       /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
       private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
            org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
            returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
       return returnMap;
    }

    
    
    private javax.xml.namespace.QName[] opNameArray = null;
    private boolean optimizeContent(javax.xml.namespace.QName opName) {
        

        if (opNameArray == null) {
            return false;
        }
        for (int i = 0; i < opNameArray.length; i++) {
            if (opName.equals(opNameArray[i])) {
                return true;   
            }
        }
        return false;
    }
     //http://172.24.55.76:8080/axis2/services/ConceptManagementService.ConceptManagementServiceHttpSoap12Endpoint/
            private  org.apache.axiom.om.OMElement  toOM(eu.atosresearch.conceptmanagement.service.AddNewConcept param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(eu.atosresearch.conceptmanagement.service.AddNewConcept.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(eu.atosresearch.conceptmanagement.service.AddNewConceptResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(eu.atosresearch.conceptmanagement.service.AddNewConceptResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundException param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundException.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(eu.atosresearch.conceptmanagement.service.ConceptManagementServiceSQLException param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(eu.atosresearch.conceptmanagement.service.ConceptManagementServiceSQLException.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(eu.atosresearch.conceptmanagement.service.ConceptManagementServiceIOException param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(eu.atosresearch.conceptmanagement.service.ConceptManagementServiceIOException.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, eu.atosresearch.conceptmanagement.service.AddNewConcept param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(eu.atosresearch.conceptmanagement.service.AddNewConcept.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (eu.atosresearch.conceptmanagement.service.AddNewConcept.class.equals(type)){
                
                           return eu.atosresearch.conceptmanagement.service.AddNewConcept.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (eu.atosresearch.conceptmanagement.service.AddNewConceptResponse.class.equals(type)){
                
                           return eu.atosresearch.conceptmanagement.service.AddNewConceptResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundException.class.equals(type)){
                
                           return eu.atosresearch.conceptmanagement.service.ConceptManagementServiceClassNotFoundException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (eu.atosresearch.conceptmanagement.service.ConceptManagementServiceSQLException.class.equals(type)){
                
                           return eu.atosresearch.conceptmanagement.service.ConceptManagementServiceSQLException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (eu.atosresearch.conceptmanagement.service.ConceptManagementServiceIOException.class.equals(type)){
                
                           return eu.atosresearch.conceptmanagement.service.ConceptManagementServiceIOException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    
   }
   