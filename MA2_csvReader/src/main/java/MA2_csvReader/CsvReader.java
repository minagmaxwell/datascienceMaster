package MA2_csvReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.opencsv.CSVReader;

public class CsvReader {
	
	static boolean isInArray(ArrayList<String> uAAircrafts, String toCheckValue) { 
	    for (String element : uAAircrafts) { 	
	        if (element.equals(toCheckValue)) { 
	            return true; 
	        } 
	    }    
	    return false;
	}

	public static void main(String[] args) throws  IOException{
			
		CSVReader csvReader = new CSVReader (new FileReader("D:\\EDX\\MA2\\flights.csv"));

		String[] record;
	    

	    record = csvReader.readNext();
	    
	    float totalCount=0;

		while ((record = csvReader.readNext()) != null) {
			boolean skip = false; 
		    for(String cell : record ) {
		    	if(cell.contentEquals("NA")) {
		    		skip = true;
		    	}
		    }
		    
		    if(skip) {continue;}
		    totalCount +=1 ;
		    String tailNum = record[12];
		    String carrier = record[10];
		    String destination = record[14];
		    String origin = record[13];
	    	String month = record[2];

		}
		float allotacted = totalCount * 4/13;
		allotacted = Math.round(allotacted);
		System.out.println("totalCount:" + totalCount);
		System.out.println("number of flights allocated to ECI:" + allotacted);
	}
}


