package MA2;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;


public class MA2 {

	 public static void main(String[] args)throws FileNotFoundException, IOException{
		 String pathToCsv = "D:\\EDX\\MA2\\flights.csv";
		 
		 String row;
		 int counter = 0;
		 
		 CSVReader csvReader = new CSVReader (new InputStreamReader(csvFile.getInputStream()));
		 while ((record = csvReader.readNext()) != null) {
		     // do something
		 }
		 
		 BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
//		 row = "aa";
//		 System.out.println(row.contentEquals("aa"));
		 while ((row = csvReader.readLine()) != null) {
		     String[] data = row.split(",");
		     System.out.println(data[13]); 
		     if(data[13].equals("EWR")) {
		    	 counter++;
		     }
		     System.out.println(counter);
		 }
		 System.out.println(counter);
		 csvReader.close();
		 
	 }
	
	
}
