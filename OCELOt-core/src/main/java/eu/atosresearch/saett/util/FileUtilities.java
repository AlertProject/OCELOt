package eu.atosresearch.saett.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
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

}
