package eu.com.alert.wp3.gui.client;

import java.io.Serializable;

//import com.google.gwt.user.client.rpc.IsSerializable;


public class Term  implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String term_id;
	private String term_term;
	private String term_lemma;
	private String term_postag;
	private String term_sameas;
	private String subclass;
	private String superclass;
	private int ocurrence;
	private String isInclude;
	private boolean useLemma;
	
	public Term()
	{}
	
	public Term(String term_id, String term_term, String term_lemma,String term_postag,String term_sameas,
			String subclass, String subclassLabel, String superclass, int ocurrence, String isInclude, boolean useLemma)
	{
		this.term_id = term_id;
		this.term_term= term_term;
		this.term_lemma = term_lemma;
		this.term_postag = term_postag;
		this.term_sameas = term_sameas;
		this.subclass = subclass;
		this.superclass = superclass;
		this.ocurrence = ocurrence;
		this.isInclude = isInclude;
		this.useLemma = useLemma;
		
	}

	public String getTerm_id() {
		return term_id;
	}

	public void setTerm_id(String term_id) {
		this.term_id = term_id;
	}

	public String getTerm_term() {
		return term_term;
	}

	public void setTerm_term(String term_term) {
		this.term_term = term_term;
	}

	public String getTerm_lemma() {
		return term_lemma;
	}

	public void setTerm_lemma(String term_lemma) {
		this.term_lemma = term_lemma;
	}

	public String getTerm_postag() {
		return term_postag;
	}

	public void setTerm_postag(String term_postag) {
		this.term_postag = term_postag;
	}

	public String getTerm_sameas() {
		return term_sameas;
	}

	public void setTerm_sameas(String term_sameas) {
		this.term_sameas = term_sameas;
	}

	public String getSubclass() {
		return subclass;
	}

	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}
	
	public String getSuperclass() {
		return superclass;
	}

	public void setSuperclass(String superclass) {
		this.superclass = superclass;
	}

	public int getOcurrence() {
		return ocurrence;
	}

	public void setOcurrence(int ocurrence) {
		this.ocurrence = ocurrence;
	}

	public String getIsInclude() {
		return isInclude;
	}

	public void setIsInclude(String isInclude) {
		this.isInclude = isInclude;
	}

	public boolean isUseLemma() {
		return useLemma;
	}

	public void setUseLemma(boolean useLemma) {
		this.useLemma = useLemma;
	}
}
