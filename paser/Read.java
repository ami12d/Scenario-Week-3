package paser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
public class Read {
	String data;

	private static char findVoltage(String toParse, int location){
		//location= location+6;
		char i = toParse.charAt(location);
		if (toParse.charAt(location)=='\"')  {
			location+=1;
			i=toParse.charAt(location);
			if (i >= '0' && i <= '9') {
				StringBuilder editied = new StringBuilder(toParse);
				editied.setCharAt(location, (char) (i+1));
				toParse=editied.toString();
				return i;
			}
			else return '0';
		}
		else return findVoltage(toParse, location+1);
		/*if ((i >= '0' && i <= '9')) return ((int)i);
		else return findVoltage(toParse,location+1);*/
	}

	private static FileReader initate (String location) throws IOException{
		Path fFilePath = Paths.get(location);
		FileReader  file = new FileReader(fFilePath.toString());
		return file;
	}

	private static String getString(FileReader file) throws IOException{
		String copiedString = new String();
		while(file.ready())
			copiedString+=((char)(file.read()));
		return copiedString;
	}

	private ArrayList<Integer> getLocations (String toFind){
		ArrayList<Integer> locations = new ArrayList<Integer>();
		int location = data.indexOf(toFind);
		while(location!=-1){
			locations.add(location);
			location = data.indexOf(toFind, location);
		}
		return locations;
	}
//e

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		//Scanner scanner =  new Scanner(fFilePath, ENCODING.name());
		FileReader file = initate("/Users/Amir/Downloads/amir.mxe");
		//String toParse = getString(file);
		data = getString(file);
		int location = toParse.indexOf("style=\"ellipse");
		location=toParse.indexOf("value", location);
		System.out.println(findVoltage(toParse,location+6));
		//System.out.println(location = toParse.indexOf("style=\"ellipse", location+6));
		//String hello = "I am the greatest I am the best I am the bs I am the greatest";
		//System.out.println(location=hello.indexOf("greatest", 0));
		//location+=1;
		//System.out.println(location=hello.indexOf("greatest", location));
		while (location!=-1){
			//System.out.print("inloop");
			location = toParse.indexOf("style=\"ellipse", location);
			if (location!=-1){
				location=toParse.indexOf("value", location);
				System.out.println(findVoltage(toParse,location+6));
			}
			else break;
		}
		//String line = file.toString();
		file.close();
		//System.out.print(line);
	}
}
