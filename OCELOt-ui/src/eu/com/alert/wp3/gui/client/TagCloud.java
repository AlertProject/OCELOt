package eu.com.alert.wp3.gui.client;


import java.util.ArrayList;
import java.util.Collections;

import com.extjs.gxt.charts.client.Chart;
import com.extjs.gxt.charts.client.model.ChartModel;
import com.extjs.gxt.charts.client.model.Legend;
import com.extjs.gxt.charts.client.model.Legend.Position;
import com.extjs.gxt.charts.client.model.axis.XAxis;
import com.extjs.gxt.charts.client.model.axis.YAxis;
import com.extjs.gxt.charts.client.model.charts.LineChart;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;





/**
 * Entry point classes define <code>onModuleLoad()</code>.
 *
 * @author Alejandra Trujillo (ATOS)
 * @last modification 28/06/2012 
 */

public class TagCloud implements EntryPoint 
{
	
	
	HorizontalSplitPanel  mainPanel;
	VerticalPanel leftPanel;
	FlowPanel tagCloudPanel;
	HorizontalPanel termInfoPanel;
	HorizontalPanel panelDays;
	HorizontalPanel panelOcurrences;
	FlowPanel grafica;
	FlowPanel configPanel;
	
	double maxFrequency    = 0;  
    double minFrequency    = 0;  
  
    final int MIN_FONT_SIZE = 10;  
    final int MAX_FONT_SIZE = 35;  
    
    String diasConsulta ="100";
    
    public String[] colores = new String[]{"color1", "color2", "color3", "color4", "color5", "color6", "color7", "color8", "color9", "color10"};
    
    final Button includeTerm = new Button("Included Terms"); 
	final Button excludeTerm = new Button("Non Included Terms");
	final Button allTerm = new Button("All Terms");
	
	RootLayoutPanel rp = RootLayoutPanel.get();
	
	public void onModuleLoad()
	{
		
		mainPanel = new HorizontalSplitPanel();
		leftPanel = new VerticalPanel();
		
				
		termInfoPanel = new HorizontalPanel();
		panelDays = new HorizontalPanel();
		panelOcurrences = new HorizontalPanel();
		tagCloudPanel = new FlowPanel(); 
		grafica = new FlowPanel();
		configPanel = new FlowPanel();
		
		mainPanel.setSplitPosition("650px");
		
		mainPanel.add(leftPanel);
		mainPanel.add(termInfoPanel);
				
		leftPanel.add(panelDays);
		leftPanel.add(tagCloudPanel);
		leftPanel.add(panelOcurrences);
		
		panelDays.setStylePrimaryName("days");
		tagCloudPanel.setStylePrimaryName("cloud1");
		panelOcurrences.setStylePrimaryName("ocurrences");
		leftPanel.setStylePrimaryName("cloudPanel");
		mainPanel.setStylePrimaryName("mainPanel");
			
		
	    rp.add(mainPanel);
		rp.forceLayout();	    
		
		optionSearch();
		serverCommunicationTERMS(diasConsulta, "in (0, 1)");

	}
	
/** *********************************** OPTION OF SEARCH *********************************** **/
	
	/**
	 * Print the search options (number of days and button for include/exclude terms)
	 *
	 * @param 
	 * @return void
	 */
	
	public void optionSearch()
	{
		includeTerm.setStyleName("inactive");
    	excludeTerm.setStyleName("inactive");
    	allTerm.setStyleName("active");
    	
		//* OBTAIN DAYS FOR SEARCH OF TERMS *//
		
		 Label noDays = new Label("Include terms of last days:");
		 panelDays.add(noDays);
		
		final TextBox days = new TextBox();
		days.setWidth("70px");
		days.setText(diasConsulta);
		panelDays.add(days);
		
		
		ClickListener daysListener =new ClickListener()
	    {
	        public void onClick(Widget sender)
	        {
	        	diasConsulta = days.getText();
	        	serverCommunicationTERMS(diasConsulta, "in (0, 1)");
	        }
	    };
	   Button getDays = new Button("Search", daysListener);
	   
	   panelDays.add(getDays);
	   
/* --------------------------------------------------------------------------------------------------------- */
       
       
       //*BUTTONS TO DEFINE INCLUDE/EXCLUDE TERMS *//
	   

	  ClickListener includeListener =new ClickListener()
	    {
	        public void onClick(Widget sender)
	        {
	        	serverCommunicationTERMS(diasConsulta, " = 1");
	        	includeTerm.setStyleName("active");
	        	excludeTerm.setStyleName("inactive");
	        	allTerm.setStyleName("inactive");
	        }
	    };
	    includeTerm.addClickListener(includeListener);
	   panelOcurrences.add(includeTerm);
	   
	   ClickListener excludeListener =new ClickListener()
	    {
	        public void onClick(Widget sender)
	        {
	        	serverCommunicationTERMS(diasConsulta, " = 0");
	        	includeTerm.setStyleName("inactive");
	        	excludeTerm.setStyleName("active");
	        	allTerm.setStyleName("inactive");
	        }
	    };
	  excludeTerm.addClickListener(excludeListener);
	   panelOcurrences.add(excludeTerm);
	   
	   ClickListener allListener =new ClickListener()
	    {
	        public void onClick(Widget sender)
	        {
	        	serverCommunicationTERMS(diasConsulta, " in (0, 1)");
	        	includeTerm.setStyleName("inactive");
	        	excludeTerm.setStyleName("inactive");
	        	allTerm.setStyleName("active");
	        }
	    };
	   allTerm.addClickListener(allListener);	   
	   panelOcurrences.add(allTerm);
	   
/* --------------------------------------------------------------------------------------------------------- */
	}
	
	/** *********************************** CREATE TAG CLOUD *********************************** **/
	
	/**
	 * Print the search options (number of days and button for include/exclude terms)
	 *
	 * @param ArrayList<Term> terms => ArrayList with Terms' info 
	 * @return void
	 */
	
	public void createTagCloud(ArrayList<Term> terms)
    {   
		tagCloudPanel.clear();	
		int tamano = terms.size();
	   
	   Term firstOcurrence = terms.get(0);   
	   Term lastOcurrence = terms.get(tamano-1);  
	   
		maxFrequency = firstOcurrence.getOcurrence();  
        minFrequency = lastOcurrence.getOcurrence();
        
        int[] suffle = desordenar(tamano);
	   
	   for (int i = 0; i < terms.size(); i++)
	   {
		   int idOrder= suffle[i];
		    final Term termino = terms.get(idOrder);
		   
		    String term_id;
	        String term_term;
	        String term_lemma;
	        int ocurrence=0;
		        
	        term_id = termino.getTerm_id();
	        term_term = termino.getTerm_term();
	        ocurrence = termino.getOcurrence();

	        
		    ClickListener updateInfoTerm = new ClickListener()
	        {

				@Override
				public void onClick(Widget sender) 
				{
					infoTerm(termino);
				}
	        	
	        };
	        						
			Hyperlink tagLink = new Hyperlink(); 
			tagLink.setText(term_term);
			String id= term_id;	
			
			tagLink.addClickListener(updateInfoTerm);
			Style linkStyle = tagLink.getElement().getStyle(); 
			int intColor = (int) (Math.random()*9);
			String color = colores[intColor];
			tagLink.setStylePrimaryName(color);
			tagLink.getElement().setId(id);
			int tagFreg = ocurrence;
			linkStyle.setProperty("fontSize",getLabelSize(tagFreg));  
			
			tagCloudPanel.add(tagLink);
			
			//*************************************************************************//
	   }
    } 
	
	


/** *********************************** INFO TERM *********************************** **/
	
	/**
	 * Print the search options (number of days and button for include/exclude terms)
	 *
	 * @param Term term => Object Term
	 * @return void
	 */
	
	public void infoTerm(Term term)
	{
		termInfoPanel.clear();
		grafica.clear();
        TabPanel tabpanel = new TabPanel();
        tabpanel.setStyleName("tabPanelStyle");
        
        VerticalPanel termDetail = new VerticalPanel();
        VerticalPanel graphiPanel = new VerticalPanel();
        tabpanel.add(termDetail, "Term Information");
        tabpanel.selectTab(0);
        tabpanel.add(graphiPanel, "Graphic of Ocurrences");
        
        termInfoPanel.add(tabpanel);
        
        final String term_term = term.getTerm_term();
        final String term_lemma = term.getTerm_lemma();
        int term_ocurrence = term.getOcurrence();
        String term_postTag = term.getTerm_postag();
        final String term_sameAs = term.getTerm_sameas();
        final String term_subClass = term.getSubclass();
        final String term_superClass = term.getSuperclass();
        String isIncluded = term.getIsInclude();
        final String term_id = term.getTerm_id();
        
        
        HTML termName = new HTML ("<b>Name:</b> "+term_term);
        HTML termLemma = new HTML ("<b>Lemma:</b> "+term_lemma);
        HTML termOcurrence = new HTML ("<b>Ocurrence:</b> "+term_ocurrence);
        HTML termPostTag = new HTML ("<b>Postag:</b> "+term_postTag);
        
        String sameAs ="";
        if (term_sameAs.length()>0)
		{
        	String [] campos = term_sameAs.split(";");
        	sameAs+="<br/>";
        	sameAs+= "<b>Potential SameAs relations:</b><br />";
			 
        	for (int j = 0; j < campos.length; j++)
			 {
				 
				 String tmpDatos = ""+campos[j];
				 String  tmp = tmpDatos.replace("\"", "");
				 String link = "";
				 if(tmp.startsWith("http://ailab.ijs.si"))
				 {
					 link ="<input type='checkbox' name='sameAs' value='same@"+j+"'/>"+tmp;
				 }
				 else
				 {
					 link ="<input type='checkbox' name='sameAs' value='same@"+j+"'/><a href='"+tmp+"' target='_blank'>"+tmp+"</a>";
				 }
				 sameAs+=link;
				 sameAs+="<br/>";
			 }
		 }
        
        String subClass =""; 
        if (term_subClass.length()>0)
		{
        	 subClass += "<br/>";
        	 subClass+= "<b>Potential SubClass of:</b><br />";
			 String [] timeoff = term_subClass.split(";");
			
			 
			 for (int j = 0; j < timeoff.length; j++)
			 {
				 
				 String tmpDatos = ""+timeoff[j];
				 String  tmp = tmpDatos.replace("\"", "");
				 String link = "";
				 if(tmp.startsWith("http://ailab.ijs.si"))
				 {
					 link ="<input type='checkbox' name='SubClass' value='same@"+j+"'/>"+tmp;
				 }
				 else
				 {
					 link ="<input type='checkbox' name='SubClass' value='sub@"+j+"'/><a href='"+tmp+"' target='_blank'>"+tmp+"</a>";
				 }
				 subClass +=link;
				 subClass += "<br/>";
			 }
		 }
        
        String superClass =""; 
        if (term_superClass.length()>0)
		{
        	 superClass += "<br/>";
        	 superClass+= "<b>Potential SuperClass of:</b><br />";
			 String [] timeoff = term_superClass.split(";");
			 for (int j = 0; j < timeoff.length; j++)
			 {
				 
				 String tmpDatos = ""+timeoff[j];
				 String  tmp = tmpDatos.replace("\"", "");
				 String link = "";
				 if(tmp.startsWith("http://ailab.ijs.si"))
				 {
					 link ="<input type='checkbox' name='SuperClass' value='same@"+j+"'/>"+tmp;
					 
				 }
				 else
				 {
					 link ="<input type='checkbox' name='SuperClass' value='super@"+j+"'/><a href='"+tmp+"' target='_blank'>"+tmp+"</a>";
				 }
				 superClass +=link;
				 superClass += "<br/>";
			 }
		 }
        
        HTML potenciaSameAs = new HTML(""+sameAs);       
        HTML termSubClass = new HTML(""+subClass);
        HTML termSuperClass = new HTML(""+superClass);
        termSubClass.getElement().setId("subclassDIV");
        termSuperClass.getElement().setId("superclassDIV");
        potenciaSameAs.getElement().setId("sameAsDIV");
        
        termDetail.add(termName);
        termDetail.add(termLemma);
        termDetail.add(termOcurrence);
        termDetail.add(termPostTag);
        termDetail.add(potenciaSameAs);
        termDetail.add(termSubClass);
        termDetail.add(termSuperClass);
        CheckBox cb = new CheckBox("Use Lemma to include the term");
        cb.addStyleName("styleCheck");
 	   	final boolean useLemma = cb.isChecked();
        
        ClickListener AddListener =new ClickListener()
	    {
	        public void onClick(Widget sender)
	        {
     
	        	String subClassChecked = getCheckBox("subclassDIV");
	        	String superClassChecked = getCheckBox("superclassDIV");
	        	String sameAsChecked = getCheckBox("sameAsDIV");
	        	
	        	String[] termSameAS = term_sameAs.split(";");
	        	String[] termSuperClass = term_superClass.split(";");
	        	String[] termsubClass = term_subClass.split(";");
	        	
	        	String[] idSelectSameAS = sameAsChecked.split(";");
	        	String[] idSelectSubClass = subClassChecked.split(";");
	        	String[] idSelectSupreClass = superClassChecked.split(";");
	        	
	        	String relationSameAsCheck="";
	        	String relationSubClassCheck="";
	        	String relationSuperClassCheck="";
	        	
	        	if(sameAsChecked !="")
	        	{
	        		for (int i = 0; i < idSelectSameAS.length; i++)
					{   
		        		String idTerm = idSelectSameAS[i];
		        		int id = Integer.parseInt(idTerm);
		        		String textTerm = termSameAS[id];
		        		
		        		relationSameAsCheck+=textTerm;
		        		relationSameAsCheck+=";";
					}
	        	}
	        	
	        	
	        	if(subClassChecked != "")
	        	{
		        	for (int j = 0; j < idSelectSubClass.length; j++)
					{
		        		String idTerm = idSelectSubClass[j];
		        		int id = Integer.parseInt(idTerm);
		        		String textTerm = termsubClass[id];
		        		
		        		relationSubClassCheck+=textTerm;
		        		relationSubClassCheck+=";";
					}
	        	}
	        	
	        	if(superClassChecked != "")
	        	{
		        	for (int j = 0; j < idSelectSupreClass.length; j++)
					{
		        		String idTerm = idSelectSupreClass[j];
		        		int id = Integer.parseInt(idTerm);
		        		String textTerm = termSuperClass[id];
		        		
		        		relationSuperClassCheck+=textTerm;
		        		relationSuperClassCheck+=";";
					}
	        	}
	        	
	        	Term addTerm = new Term();
	        	addTerm.setTerm_id(term_id);
	        	addTerm.setTerm_term(term_term);
	        	addTerm.setTerm_lemma(term_lemma);
	        	addTerm.setTerm_sameas(relationSameAsCheck);
	        	addTerm.setSubclass(relationSubClassCheck);
	        	addTerm.setSuperclass(relationSuperClassCheck);
	        	addTerm.setUseLemma(useLemma);
	        	serverCommunicationAddButtom(addTerm);
	        		        	
	        }
	    };
	  
	   Button addButton = new Button("Add", AddListener);
	   if(term_sameAs.length()<=0 && term_superClass.length()<=0 && term_subClass.length()<=0)
	   {
		   addButton.setEnabled(false);
		   cb.setEnabled(true);
	   }
	   HTML br = new HTML("<br/>");
	   termDetail.add(br);
	   termDetail.add(br);
	   termDetail.add(cb);
	   termDetail.add(addButton);

	   
	   
	   
	   String graficaText ="<b>Graphic of ocurrences: </b>";
	   HTML graText = new HTML(""+graficaText+" "+term_term);
	   graphiPanel.add(graText);
	   
	   graphiPanel.add(grafica);
	   serverCommunicationGRAPHIC(diasConsulta, term_id);
	}
    
	
/** *********************************** GET LABEL SIZE *********************************** **/
	
	/**
	 * Print the search options (number of days and button for include/exclude terms)
	 *
	 * @param double frequency => Frequency of appearance of each term
	 * @return String => CSS fontsize that should have the term according the frequency
	 */
	
    public String getLabelSize(double frequency)
    {  
        double weight = (Math.log(frequency) - Math.log(minFrequency)) / (Math.log(maxFrequency) - Math.log(minFrequency));  
        int fontSize = MIN_FONT_SIZE + (int)Math.round((MAX_FONT_SIZE - MIN_FONT_SIZE) * weight);  
        return Integer.toString(fontSize) + "pt";  
    }  
    
    
/** *********************************** CREATE GRAPHIC *********************************** **/
	
	/**
	 * Create the chart that display the term frequency
	 *
	 * @param ArrayList<Graphic> datosGrafic => data for create the chart
	 * @return void
	 */
    
    public void createGraphic(ArrayList<Graphic> datosGrafic)
    {  
    	int GraphicSize  = datosGrafic.size();
    	int panelSize = GraphicSize*30;
    	if(panelSize < 300)
    	{
    		panelSize = 300;
    	}
    	ContentPanel cp = new ContentPanel();  
	    cp.setHeading("");  
	    cp.setFrame(true);  
	    cp.setSize(panelSize, 400);  
	    cp.setLayout(new FitLayout());  
	      
	    String url = "resources/chart/open-flash-chart.swf";  
	      
	    final Chart chart = new Chart(url);  
	    chart.setBorders(true);  
	    chart.setChartModel(getLineChartData(datosGrafic));
	    cp.add(chart);
	    
	    
	    grafica.add(cp);


    } 
    
/** *********************************** COMMUNICATION WITH SERVER TO GET THE TERMS *********************************** **/
	
	/**
	 * Communication with the server to get the Terms
	 *
	 * @param String restar => Last days to include terms
	 * @param String include => Define type of terms to display (include, exclude, both)
	 * @return void
	 */
    
    private void serverCommunicationTERMS(String restar, String include) 
	{
    	ConnectionDBAsync connectionService = (ConnectionDBAsync) GWT.create(ConnectionDB.class);

		AsyncCallback<ArrayList<Term>> callback = new AsyncCallback<ArrayList<Term>>()
		 {
			    public void onFailure(Throwable caught) 
			    {
			    	System.err.println("FAILURE...." +caught);
			    	Window.alert("En el Fail :S" +caught);
			    }

				public void onSuccess(ArrayList<Term> result) 
				{
					createTagCloud(result);	
				}
		 };
		 
		 connectionService.getTerm(restar,  include, callback);

	}
    
/** ****************************** COMMUNICATION WITH SERVER TO GET INFO OF GRAPHIC ********************************* **/
	
	/**
	 * Communication with the server to get the Chart data
	 *
	 * @param String date => date to create the chart
	 * @param String id => identification of term which generates the graph
	 * @return void
	 */
    
    private void  serverCommunicationGRAPHIC(String date, String id) 
	{
    	ConnectionDBAsync connectionService = (ConnectionDBAsync) GWT.create(ConnectionDB.class);

		AsyncCallback<ArrayList<Graphic>> callback = new AsyncCallback<ArrayList<Graphic>>()
		 {
			    public void onFailure(Throwable caught) 
			    {
			    	System.err.println("FAILURE...." +caught);
			    	Window.alert("En el Fail :S" +caught);
			    }

				public void onSuccess(ArrayList<Graphic> result) 
				{
					createGraphic(result);	
				}
		 };
		 
		 connectionService.dataGraphic(date,  id, callback);

	}
    
/** *********************************** COMMUNICATION WITH SERVER TO ADD BUTTOM ACTION *********************************** **/
	
	/**
	 * Communication with the server the add action 
	 *
	 * @param Term addTerm 
	 * @return void
	 */
    
    private void serverCommunicationAddButtom(Term addTerm) 
	{
    	ConnectionDBAsync connectionService = (ConnectionDBAsync) GWT.create(ConnectionDB.class);

		AsyncCallback<String> callback = new AsyncCallback<String>()
		 {
			    public void onFailure(Throwable caught) 
			    {
			    	System.err.println("ERROR...." +caught);
			    	Window.alert("Error to send the term to addAction" +caught);
			    }

				@Override
				public void onSuccess(String result) 
				{
					Window.alert("Term has been added successfully: "+result);		
					serverCommunicationTERMS(diasConsulta, "in (0, 1)");
				}
		 };
		 
		 connectionService.addAction(addTerm, callback);

	}    
    
/** ****************************** GET LINE CHART DATA ********************************* **/
	
	/**
	 * Define the x-axis and y-axis of the Graph
	 *
	 * @param ArrayList<Graphic> datosGrafic => ArrayList with data of Graphic
	 * @return void
	 */
    
    private ChartModel getLineChartData(ArrayList<Graphic> datosGrafic) 
    {  
        ChartModel cm = new ChartModel("",  
            "font-size: 14px; font-family: Verdana; text-align: center;");  
        cm.setBackgroundColour("#fffff5");  
        Legend lg = new Legend(Position.RIGHT, true);  
       
        lg.setPadding(10);  
        cm.setLegend(lg);  
          
        LineChart line = new LineChart();
        XAxis xa = new XAxis();
        YAxis yAxis = new YAxis();
        ArrayList<Number> chartMsgCount = new ArrayList<Number>();
        
        int ejeY = 0;
        
        for(int j = 0; j < datosGrafic.size(); j++)
        {
        	Graphic graphic = new Graphic();
        	Graphic grafic = datosGrafic.get(j);
        	com.extjs.gxt.charts.client.model.axis.Label label = new com.extjs.gxt.charts.client.model.axis.Label(grafic.getDate(), 45); 
        	
        	xa.addLabels(label);
        	
        	int ocurrenciaTerm = grafic.getOcurrence();
        	chartMsgCount.add(ocurrenciaTerm);
        	if(ejeY<ocurrenciaTerm)
        	{
        		ejeY = ocurrenciaTerm;
        	}       	
        }
        
        cm.setXAxis(xa);
        
        yAxis.setMax(ejeY);
        yAxis.setSteps(10);
        cm.setYAxis(yAxis);
        
        
        line.addValues(chartMsgCount);

      
        cm.addChartConfig(line);  
        return cm;  
      } 
    
/** ****************************** GET CHECKBOX ********************************* **/
	
	/**
	 * Get the checked checkboxes
	 *
	 * @param ArrayList<Graphic> datosGrafic => ArrayList with data of Graphic
	 * @return String seleccionados => String with id of the checkboxes selected
	 */
    public String getCheckBox(String idDiv)
    {
    	Element subClassCheck = DOM.getElementById(""+idDiv);
    	NodeList<Node> subClassChilds = subClassCheck.getChildNodes();
    	NodeList<com.google.gwt.dom.client.Element> itemsInput = subClassCheck.getElementsByTagName("input");
    	
    	String seleccionados = "";
    	 for (int i = 0; i < itemsInput.getLength(); i++)
    	 {
    		 com.google.gwt.dom.client.Element check = itemsInput.getItem(i);
    		 Boolean checked = check.getPropertyBoolean("checked");
    		 String textcheck = check.getInnerText();
    		 
    		 if(checked)
    		 {
    			 seleccionados+=""+i+";";
    		 }
    	 }
		return seleccionados;
    }
    
/** ****************************** desordenar ********************************* **/
	
	/**
	 * Get the checked checkboxes
	 *
	 * @param int tamano => Number of terms includes in TagCloud
	 * @return int[] => Random order to display the terms
	 */
    
    private int[] desordenar(int tamano) 
    {
    	int[] arraySuffle = new int[tamano];
    	for(int i=0; i<tamano; i++)
    	{
    		arraySuffle[i]  = i;
    	}
    	
    	int aleatorio,aux;
    	
    	for(int j=0; j<tamano; j++)
    	{
    		aleatorio= (int)(Math.random()*tamano-1);
    		aux=arraySuffle[j];
    		arraySuffle[j]=arraySuffle[aleatorio];
    		arraySuffle[aleatorio]=aux;
    	}


    	return arraySuffle;
    }
}