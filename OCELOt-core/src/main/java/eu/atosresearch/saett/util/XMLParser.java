package eu.atosresearch.saett.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLParser {
	
	private Document doc;
	
	public XMLParser(File file) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		doc = db.parse(file);
		doc.getDocumentElement().normalize();		
	}
	
	public XMLParser(String xmltext) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xmltext));		
		doc = db.parse(is);
		doc.getDocumentElement().normalize();		
	}
	
	
	public String getTextValue(String tag){
		try{
			NodeList nodeLst = doc.getElementsByTagName(tag);
			for (int s = 0; s < nodeLst.getLength(); s++) {
				Node fstNode = nodeLst.item(s);
				if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eText = (Element) fstNode;
					NodeList nt = eText.getChildNodes();
					String text=((Node) nt.item(0)).getNodeValue();
					return text;
				}
			}
			return "";
		}catch(NullPointerException e){
			return "";
		}
	}
	
	public String getTextValue(String tag,String attribute,String value){
		NodeList nodeLst = doc.getElementsByTagName(tag);
		for (int s = 0; s < nodeLst.getLength(); s++) {
			Node fstNode = nodeLst.item(s);
			if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
				NamedNodeMap attributes = fstNode.getAttributes();
				String attr=attributes.getNamedItem(attribute).getNodeValue();
				if(attr.equals(value)){
					Element eText = (Element) fstNode;
					NodeList nt = eText.getChildNodes();
					String text=((Node) nt.item(0)).getNodeValue();
					return text;
				}
			}
		}
		return null;
	}
	
	public Vector<String> getTextValues(String tag){
		NodeList nodeLst = doc.getElementsByTagName(tag);
		Vector<String> result=new Vector<String>();
		for (int s = 0; s < nodeLst.getLength(); s++) {
			Node fstNode = nodeLst.item(s);
			if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eText = (Element) fstNode;
				NodeList nt = eText.getChildNodes();
				String text=((Node) nt.item(0)).getNodeValue();
				result.add(text);
			}
		}
		return result;		
	}


}
