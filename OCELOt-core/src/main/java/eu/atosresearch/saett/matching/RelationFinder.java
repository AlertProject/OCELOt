package eu.atosresearch.saett.matching;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Vector;

import eu.atosresearch.saett.conceptextraction.Concept;

public class RelationFinder {

	private static int LOWER=0;
	private static int UPPER=1;

	private RemoteKB kb;
	private SemanticMatcher sm;
	private Vector<String> relations;

	private HashMap<String, String> uppertaxonomy_cache;
	private HashMap<String, String> lowertaxonomy_cache;

	private HashMap<String, String> labelsbyURI;

	public RelationFinder(RemoteKB kb){
		this.kb=kb;
		relations=new Vector<String>();
		uppertaxonomy_cache=new HashMap<String, String>();
		lowertaxonomy_cache=new HashMap<String, String>();
		labelsbyURI=new HashMap<String, String>();
	}

	public RelationFinder(RemoteKB kb,SemanticMatcher sm){
		this.kb=kb;
		this.sm=sm;
		relations=new Vector<String>();
		uppertaxonomy_cache=new HashMap<String, String>();
		lowertaxonomy_cache=new HashMap<String, String>();
		labelsbyURI=new HashMap<String, String>();

	}

	public void loadTaxonomyCacheFile(String cache) throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(cache));
		uppertaxonomy_cache=(HashMap<String, String>)ois.readObject();
		lowertaxonomy_cache=(HashMap<String, String>)ois.readObject();
		labelsbyURI=(HashMap<String, String>)ois.readObject();
	}

	public void saveTaxonomyCacheFile(String cache) throws FileNotFoundException, IOException{
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(cache));
		oos.writeObject(uppertaxonomy_cache);
		oos.writeObject(lowertaxonomy_cache);
		oos.writeObject(labelsbyURI);	
	}


	public void addTaxonomyRelation(String relation){
		/*if(!relations.containsKey(eqrelation))
			relations.put(eqrelation,kbrelation);
		else
			relations.put(eqrelation,relations.get(eqrelation)+";"+kbrelation);*/
		relations.add(relation);
	}

	/*public void findRelations(Concept c,int levels){
		if(c.getSemanticConcepts().size()>0){
			String uri=c.getSemanticConcepts().get(0);
			if((uppertaxonomy_cache.containsKey(uri) || lowertaxonomy_cache.containsKey(uri))){
				Vector<String> upper=new Vector<String>();
				upper.add(uri);
				Vector<String> lower=new Vector<String>();
				lower.add(uri);
				while(levels>0){
					Vector<String> nupper=new Vector<String>();
					Vector<String> nlower=new Vector<String>();
					for(String up:upper){
						if(uppertaxonomy_cache.containsKey(up)){
							String[] nup=uppertaxonomy_cache.get(up).split(";");
							for(String u:nup){
								nupper.add(u);
								c.addRelation("*rdfs:subClassOf", u);
							}							
						}
					}

					for(String lo:lower){
						if(lowertaxonomy_cache.containsKey(lo)){
							String[] nlo=uppertaxonomy_cache.get(lo).split(";");
							for(String u:nlo){
								nlower.add(u);
								c.addRelation("*rdfs:subClassOf", u);
							}							
						}

					}
					levels--;
					upper=nupper;
					lower=nlower;
				}
			}else{
				findRelations(c, levels);
			}
		}
	}*/

	private void addToHash(String key,String value,HashMap<String, String> hash){
		if(!hash.containsKey(key)){
			hash.put(key, value);
		}else{
			String[] parts=hash.get(key).split(";");
			boolean exists=false;
			for(String l:parts){
				if(l.toLowerCase().equals(value.toLowerCase())){
					exists=true;
					break;
				}
			}
			if(!exists)
				hash.put(key, hash.get(key)+";"+value);
		}			
	}

	private void addLabeltoURI(String uri,String label){
		addToHash(uri, label, labelsbyURI);
	}

	private void addURIRelation(String uri_a,String uri_b,int relation){
		if(relation==RelationFinder.LOWER){
			//System.out.println("Add L:"+uri_a+" "+uri_b);
			addToHash(uri_a, uri_b,lowertaxonomy_cache);
		}else{
			//System.out.println("Add U:"+uri_a+" "+uri_b);
			addToHash(uri_a, uri_b,uppertaxonomy_cache);
		}
	}

	void addToCache(int relation,HashMap<String, String> previous,HashMap<String, String> rs){
		for(int i=0;i<relations.size();i++){
			if(previous.containsKey(rs.get("op"+i))){
				addURIRelation(rs.get("op"+i), rs.get("c"), relation);
			}
		}
	}

	private void includeEmpty(String not_included, int cache){
		not_included=not_included.replaceAll(">", "");
		not_included=not_included.replaceAll("<", "");
		for(String uri:not_included.split(",")){
			if(cache==RelationFinder.LOWER){
				if(!lowertaxonomy_cache.containsKey(uri.trim())){
					lowertaxonomy_cache.put(uri, "NN");
				}
			}else{
				if(!uppertaxonomy_cache.containsKey(uri.trim())){
					uppertaxonomy_cache.put(uri, "NN");
				}
			}
		}
	}



	public void findRelations(Concept c,int levels){
		if(c.getSemanticConcepts().size()>0){

			String uri=c.getSemanticConcepts().get(0);
			HashMap<String,String> antecesor=new HashMap<String, String>();
			HashMap<String,String> sucesor=new HashMap<String, String>();

			HashMap<String,String> label=new HashMap<String, String>();

			String sparql_upper="select distinct ?c ?label where {?c <http://www.w3.org/2000/01/rdf-schema#label> ?label {";
			String sparql_lower="select distinct ?c ?label where {?c <http://www.w3.org/2000/01/rdf-schema#label> ?label {";
			boolean first=true;
			for(String r:relations){
				if(first){
					sparql_upper+="{<"+uri+"> <"+r+"> ?c}";
					sparql_lower+="{?c <"+r+"> <"+uri+">}";
					first=false;
				}else{
					sparql_upper+=" UNION {<"+uri+"> <"+r+"> ?c}";
					sparql_lower+=" UNION {?c <"+r+"> <"+uri+">}";
				}
			}
			sparql_lower+="} FILTER (lang(?label) = \"en\")}";
			sparql_upper+="} FILTER (lang(?label) = \"en\")}";

			if(lowertaxonomy_cache.containsKey(uri)){
				sucesor=new HashMap<String, String>();
				if(!lowertaxonomy_cache.get(uri).equals("NN")){
					String[] nlo=lowertaxonomy_cache.get(uri).split(";");
					for(String u:nlo){
						sucesor.put(u,"1");
						if(sm!=null){
							String[] lab=labelsbyURI.get(u).split(";");
							for(String l:lab){
								try{
									if(!label.containsKey(l)){
										Vector<String> v=sm.search(l.toLowerCase(), 1);
										if(v.size()>0)
											c.addRelation("rdfs:subClassOf*", v.get(0));								
									}
								}catch (Exception e){

								}
							}
						}else{
							c.addRelation("rdfs:subClassOf*", u);
						}
					}
				}
			}else{
				//System.out.println(uri);
				//System.out.println("SQ-LOWER");
				Vector<HashMap<String, String>> lower=kb.executeSPARQL(sparql_lower);
				sucesor=new HashMap<String, String>();
				for(HashMap<String, String> h:lower){
					sucesor.put(h.get("c"),"1");
					addURIRelation(uri,h.get("c"), RelationFinder.LOWER);
					boolean included=label.containsKey(h.get("label").toLowerCase());
					label.put(h.get("label").toLowerCase(),"1");
					try{
						addLabeltoURI(h.get("c"), h.get("label"));
						if(sm!=null){
							if(!included){
								Vector<String> v=sm.search(h.get("label").toLowerCase().replaceAll("@en", ""), 1);
								if(v.size()>0)
									c.addRelation("rdfs:subClassOf*", v.get(0));
							}
						}else{
							c.addRelation("rdfs:subClassOf*", h.get("c"));
						}
					}catch(Exception e){

					}
				}
				includeEmpty(uri, RelationFinder.LOWER);

			}

			if(uppertaxonomy_cache.containsKey(uri)){
				antecesor=new HashMap<String, String>();
				if(!uppertaxonomy_cache.get(uri).equals("NN")){
					String[] nlo=uppertaxonomy_cache.get(uri).split(";");
					for(String u:nlo){
						antecesor.put(u,"1");
						if(sm!=null){
							//System.out.println(u);
							String[] lab=labelsbyURI.get(u).split(";");
							for(String l:lab){
								try{
									if(!label.containsKey(l)){
										Vector<String> v=sm.search(l.toLowerCase(), 1);
										if(v.size()>0)
											c.addRelation("*rdfs:subClassOf", v.get(0));								
									}
								}catch(Exception e){

								}
							}
						}else{
							c.addRelation("*rdfs:subClassOf", u);
						}
					}
				}
			}else{
				//System.out.println(uri);
				//System.out.println("SQ-UPPER");
				Vector<HashMap<String, String>> upper=kb.executeSPARQL(sparql_upper);
				antecesor=new HashMap<String, String>();
				for(HashMap<String, String> h:upper){
					antecesor.put(h.get("c"),"1");
					addURIRelation(uri,h.get("c"), RelationFinder.UPPER);
					boolean included=label.containsKey(h.get("label").toLowerCase());
					label.put(h.get("label").toLowerCase(),"1");
					try{
						addLabeltoURI(h.get("c"), h.get("label"));
						if(sm!=null){
							if(!included){
								//System.out.println("Searching:"+h.get("label").toLowerCase().replaceAll("@en", ""));
								Vector<String> v=sm.search(h.get("label").toLowerCase().replaceAll("@en", ""), 1);
								if(v.size()>0)
									c.addRelation("*rdfs:subClassOf", v.get(0));
							}
						}else{
							c.addRelation("*rdfs:subClassOf", h.get("c"));
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				includeEmpty(uri, RelationFinder.UPPER);

			}


			for(int i=1;i<levels;i++){
				//System.out.println("NIVEL:"+i);
				sparql_upper="select distinct ?c ?label #OPTIONALVAR# where {?c <http://www.w3.org/2000/01/rdf-schema#label> ?label. #RELOPTIONAL# {";
				sparql_lower="select distinct ?c ?label #OPTIONALVAR# where {?c <http://www.w3.org/2000/01/rdf-schema#label> ?label. #RELOPTIONAL# {";
				first=true;
				boolean execute_sucesor=false;

				Vector<String> included_upper=new Vector<String>();
				Vector<String> included_lower=new Vector<String>();

				String not_included_upper="";
				String not_included_lower="";


				String optionalvar="";
				String reloptional_lower="";
				String reloptional_upper="";

				int kop=0;

				for(String relation:relations){
					optionalvar+=" ?op"+kop;
					reloptional_lower+=" OPTIONAL {?c <"+relation+"> ?op"+kop+". FILTER(?op"+kop+" in (#PREVIOUS#))}.";
					reloptional_upper+=" OPTIONAL {?op"+kop+" <"+relation+"> ?c. FILTER(?op"+kop+" in (#PREVIOUS#))}. ";
					kop++;
				}

				reloptional_lower=reloptional_lower.substring(0, reloptional_lower.length()-1);
				reloptional_upper=reloptional_upper.substring(0, reloptional_upper.length()-1);

				sparql_lower=sparql_lower.replaceAll("#OPTIONALVAR#", optionalvar);
				sparql_lower=sparql_lower.replaceAll("#RELOPTIONAL#", reloptional_lower);

				sparql_upper=sparql_upper.replaceAll("#OPTIONALVAR#", optionalvar);
				sparql_upper=sparql_upper.replaceAll("#RELOPTIONAL#", reloptional_upper);




				for(String u:sucesor.keySet()){
					if(!lowertaxonomy_cache.containsKey(u)){
						not_included_lower+=",<"+u+">";
						for(String relation:relations){
							if(first){
								sparql_lower+="{?c <"+relation+"> <"+u+">}";
								first=false;
							}else{
								sparql_lower+=" UNION {?c <"+relation+"> <"+u+">}";
							}
							execute_sucesor=true;
						}
					}else{
						included_lower.add(u);
					}
				}

				first=true;
				boolean execute_antecesor=false;
				for(String u:antecesor.keySet()){
					if(!uppertaxonomy_cache.containsKey(u)){
						not_included_upper+=",<"+u+">";
						for(String relation:relations){
							if(first){
								sparql_upper+="{<"+u+"> <"+relation+"> ?c}";
								first=false;
							}else{
								sparql_upper+=" UNION {<"+u+"> <"+relation+"> ?c}";
							}
							execute_antecesor=true;
						}
					}else{
						included_upper.add(u);
					}
				}

				//System.out.println(not_included_lower);
				//System.out.println(not_included_upper);

				sparql_lower+="} FILTER (lang(?label) = \"en\")}";
				sparql_upper+="} FILTER (lang(?label) = \"en\")}";

				HashMap<String, String> sucesorant=sucesor;
				sucesor=new HashMap<String, String>();
				if(execute_sucesor){
					//System.out.println("SQ-LOWER");
					sparql_lower=sparql_lower.replaceAll("#PREVIOUS#", not_included_lower.substring(1));
					Vector<HashMap<String, String>> lower=kb.executeSPARQL(sparql_lower);
					for(HashMap<String, String> h:lower){
						sucesor.put(h.get("c"),"1");
						addToCache(RelationFinder.LOWER, sucesorant, h);
						boolean included=label.containsKey(h.get("label").toLowerCase());
						label.put(h.get("label").toLowerCase(),"1");
						try{
							addLabeltoURI(h.get("c"), h.get("label"));
							if(sm!=null){
								if(!included){
									Vector<String> v=sm.search(h.get("label").toLowerCase().replaceAll("@en", ""), 1);
									if(v.size()>0)
										c.addRelation("rdfs:subClassOf*", v.get(0));
								}
							}else{
								c.addRelation("rdfs:subClassOf*", h.get("c"));
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					includeEmpty(not_included_lower, RelationFinder.LOWER);
				}

				for(String included:included_lower){
					if(lowertaxonomy_cache.containsKey(included)){
						if(!lowertaxonomy_cache.get(included).equals("NN")){
							String[] nlo=lowertaxonomy_cache.get(included).split(";");
							for(String u:nlo){
								sucesor.put(u,"1");
								if(sm!=null){
									String[] lab=labelsbyURI.get(included).split(";");
									for(String l:lab){
										try{
											if(!label.containsKey(l)){
												Vector<String> v=sm.search(l.toLowerCase(), 1);
												if(v.size()>0)
													c.addRelation("rdfs:subClassOf*", v.get(0));								
											}
										}catch (Exception e){

										}
									}
								}else{
									c.addRelation("rdfs:subClassOf*", u);
								}
							}
						}
					}
				}
				HashMap<String, String> antecesor_ant=antecesor;
				antecesor=new HashMap<String, String>();
				if(execute_antecesor){
					//System.out.println("SQ-UPPER");
					sparql_upper=sparql_upper.replaceAll("#PREVIOUS#", not_included_upper.substring(1));
					//System.out.println(sparql_upper);
					Vector<HashMap<String, String>> upper=kb.executeSPARQL(sparql_upper);
					for(HashMap<String, String> h:upper){
						antecesor.put(h.get("c"),"1");
						addToCache(RelationFinder.UPPER, antecesor_ant, h);
						boolean included=label.containsKey(h.get("label").toLowerCase());
						label.put(h.get("label").toLowerCase(),"1");
						try{
							addLabeltoURI(h.get("c"), h.get("label"));
							if(sm!=null){
								if(!included){
									Vector<String> v=sm.search(h.get("label").toLowerCase().replaceAll("@en", ""), 1);
									if(v.size()>0)
										c.addRelation("*rdfs:subClassOf", v.get(0));
								}
							}else{
								c.addRelation("*rdfs:subClassOf", h.get("c"));
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					includeEmpty(not_included_upper, RelationFinder.UPPER);
				}

				for(String included:included_upper){
					//System.out.println(included);
					if(uppertaxonomy_cache.containsKey(included)){
						if(!uppertaxonomy_cache.get(included).equals("NN")){
							String[] nlo=uppertaxonomy_cache.get(included).split(";");
							for(String u:nlo){
								antecesor.put(u,"1");
								if(sm!=null){
									String[] lab=labelsbyURI.get(included).split(";");
									for(String l:lab){
										try{
											if(!label.containsKey(l)){
												Vector<String> v=sm.search(l.toLowerCase(), 1);
												if(v.size()>0)
													c.addRelation("*rdfs:subClassOf", v.get(0));								
											}
										}catch(Exception e){

										}
									}
								}else{
									c.addRelation("*rdfs:subClassOf", u);
								}
							}
						}
					}
				}
			}
		}
	}

}
