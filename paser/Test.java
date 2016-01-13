package paser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {
	
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
	
	public static void write(String x, String location) throws IOException{
	    FileWriter write = new FileWriter(location, false);
	    PrintWriter print = new PrintWriter(write);
	    print.printf("%s" + "%n",x);
	    print.close();
	  }

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FileReader file = initate("/Users/Amir/Documents/CS 2nd year/SW3/1.mxe");
		String hello = getString(file);
		file.close();
		System.out.print(hello);
		Parser test = new Parser(hello);
		test.getData();
		test.printComponents();
		test.findAndReplace("Lamp", "Off");
		hello=test.getString();
		System.out.print(hello);
		//write(hello, "/Users/Amir/Documents/CS 2nd year/SW3/1.mxe");
	}




}

