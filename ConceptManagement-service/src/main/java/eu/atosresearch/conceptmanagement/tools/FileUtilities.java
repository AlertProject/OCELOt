package eu.atosresearch.conceptmanagement.tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtilities {
	
	public static String file2tring(String file) throws IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line="",text="";
		while((line=br.readLine())!=null){
			text+=line+"\n";
		}
		return text;
	}
	
	public static String file2tring(InputStream in) throws IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(in));
		String line="",text="";
		while((line=br.readLine())!=null){
			text+=line+"\n";
		}
		return text;
	}
	

}
