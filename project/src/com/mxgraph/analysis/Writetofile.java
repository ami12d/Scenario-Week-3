package com.mxgraph.analysis;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	  
	  public String getString(){return toWrite;}
	  public void setString(String a){toWrite=a;}

	  public void write() {
		  
	    FileWriter write;
		try {
			write = new FileWriter(location, false);
			 PrintWriter print = new PrintWriter(write);
			    print.printf("%s" + "%n",toWrite);
			    print.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	  }
	  
		private FileReader initate (String location) throws IOException{
			Path fFilePath = Paths.get(location);
			FileReader  file = new FileReader(fFilePath.toString());
			return file;
		}

		private String getString(String location){
			FileReader file;
			String copiedString = new String();
			try {
				file = initate(location);
			
			
			while(file.ready())
				copiedString+=((char)(file.read()));
			file.close();
			//return copiedString;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				return copiedString;
			
		}


}
