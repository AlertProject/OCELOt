package eu.atosresearch.saett.conceptextraction;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

import eu.atosresearch.saett.nlp.Token;

public class Concept implements Comparable<Concept>,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Token token;
	private int count;
	Vector<String> semanticConcepts;
	
	HashMap<String, Vector<String>> relations;
	
	public Concept(Token token){
		this.token=token;
		count=1;
		semanticConcepts=new Vector<String>();
		relations=new HashMap<String, Vector<String>>();
	}
	
	public void addCount(){
		count++;
	}
	
	public void addCount(int add){
		count+=add;
	}
	
	public void addSemanticConcept(String uri){
		if(!semanticConcepts.contains(uri))
			semanticConcepts.add(uri);
	}

	public Token getToken() {
		return token;
	}

	//@Override
	public int compareTo(Concept o) {
		if(o.getToken().getText().toLowerCase().equals(token.getText().toLowerCase()) && o.getToken().getPOSTag()==token.getPOSTag())
			return 0;
		else 
			if(o.getCount()==count)
				return 0;
			else
				if(o.getCount()>count)
					return -1;
				else
					return 1;
	}

	public int getCount() {
		return count;
	}

	public Vector<String> getSemanticConcepts() {
		return semanticConcepts;
	}
	
	public void addRelation(String relation,String concept){
		//System.out.println("Adding "+relation+" - "+concept);
		if(relations.containsKey(relation)){
			if(!relations.get(relation).contains(concept))
				relations.get(relation).add(concept);
		}else{
			Vector<String> c=new Vector<String>();
			c.add(concept);
			relations.put(relation, c);
		}
	}
	
	public Vector<String> getRelationsType(){
		Vector<String> r=new Vector<String>();
		for(String re:relations.keySet())
			r.add(re);
		return r;
	}
	
	public Vector<String> getRelationsByType(String relation){
		//System.out.println("REL:"+relations);
		if(relations.get(relation)!=null)
			return relations.get(relation);
		else
			return new Vector<String>();
	}
	

}
