package paser;

import java.io.FileReader;
import java.io.IOException;
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

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FileReader file = initate("/Users/Amir/Documents/CS 2nd year/SW3/test.mxe");
		String hello = getString(file);
		file.close();
		Parser test = new Parser(hello);
		test.getAndPrint();
		
	}




}

