package eu.com.alert.wp3.gui.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("connectiondb")
public interface ConnectionDB extends RemoteService 
{
	public ArrayList<Term> getTerm(String s, String isInclude);
	public ArrayList<Graphic> dataGraphic(String date, String term);
	public String addAction(Term termAdd);
}