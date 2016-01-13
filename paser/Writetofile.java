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
import java.util.Scanner;

public class Writetofile {
	

	

	 public String toWrite;
	 public String location;

	  public Writetofile(String location){
	    this.location=location;
	    toWrite = new String();
	  }

	  public Writetofile(String toWrite, String location){
	    this.toWrite=toWrite;
	    this.location=location;
	  }

	/*  public static void changeString(String newString){
	    toWrite=newString;
	  }*/

	  public void write() throws IOException{
		  
	    FileWriter write = new FileWriter(location, false);
	    PrintWriter print = new PrintWriter(write);
	    print.printf("%s" + "%n",toWrite);
	    print.close();
	  }

}
