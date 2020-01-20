import java.io.*;
import java.util.*;

/**
 * Reads a file into an array of strings that are the high scores
 * 
 */
class FileReader {
   public String file;
   public LinkedList<String> lines;

   public FileReader (String f) {
	   file = f;
	   try {
		   // Open the file
		   FileInputStream fstream = new FileInputStream(file);
		   BufferedReader in = new BufferedReader(new InputStreamReader(fstream));
		   lines = new LinkedList<>();
		   
		   // Read lines while they keep coming
		   while (in.ready()) {
			   lines.add(in.readLine());
		   }
		   in.close();
        }
	    catch (Exception e) {
	    	System.err.println("File input error");
	    }
   	}
}
