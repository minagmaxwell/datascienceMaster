import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


import java.util.HashMap;

//import com.opencsv.CSVReader;

//class FlightScheduler {
//	
//	CSVReader csvReader;
//	HashMap<String, String[]> hm = new HashMap<String, String[]>();
//
//	int totalFlightCount;
//	
//	public FlightScheduler(){
//		totalFlightCount = 0;
//	}
//	
//	
//	void loadData(String flightDataFile) throws  IOException{
//		csvReader = new CSVReader (new FileReader(flightDataFile));
//		String[] record;
//	    record = csvReader.readNext();
//	    while ((record = csvReader.readNext()) != null) {
//	    	String flightNum = record[11];
//	    	hm.put(flightNum, record);
//	    }
//	}
//	
//	void reallocate (int day, int month, int year, String flightCode) {
//		
//	}
//	
//	boolean check(int day, int month, int year, String flightCode) {
//		return true;
//	}
//}

class PartThree {

	
	public static void main(String[] args) {
		
//		FlightScheduler FS = new FlightScheduler();
//		FS.loadData("D:\\EDX\\MA2\\reduced.csv");
//				
//		
//		String[] record;
//
//	    record = FS.csvReader.readNext();
//	    
//	    float totalCount=0;
//
//		while ((record = FS.csvReader.readNext()) != null) {
//			
//			boolean skip = false; 
//		    for(String cell : record ) {
//		    	if(cell.contentEquals("NA")) {
//		    		skip = true;
//		    	}
//		    }
//		    
//		    if(skip) {continue;}
//		    totalCount +=1 ;
//		    
//		    String carrier = record[10];
//		    String flightNum = record[11];
//		    String tailNum = record[12];
//		    String origin = record[13];
//		    String destination = record[14];
//	    	String month = record[2];
//
//
//		}
//		float allotacted = totalCount * 4/13;
//		allotacted = Math.round(allotacted);
//		System.out.println("totalCount:" + totalCount);
//		System.out.println("number of flights allocated to ECI:" + allotacted);
	}
}