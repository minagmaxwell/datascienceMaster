package MA2_csvReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.opencsv.CSVReader;




public class CsvReader_backup {
	
	boolean isInArray(String[] arr, String toCheckValue) { 

	    for (String element : arr) { 
	        if (element == toCheckValue) { 
	            return true; 
	        } 
	    } 
	    return false;
	}

	public static void main(String[] args) throws  IOException{
		
		
		CSVReader csvReader = new CSVReader (new FileReader("D:\\EDX\\MA2\\flights.csv"));

		String[] record;
		int EWRcounter = 0;
		int JFKcounter = 0;
		int LGAcounter = 0;
		
		int LGA_minDistance = 1000000000;
		int LGA_maxDistance = -1;
		int temp;
		
        ArrayList<Integer> LGA_dists = new ArrayList<>();

		while ((record = csvReader.readNext()) != null) {
			boolean skip = false; 
		    for(String cell : record ) {
		    	if(cell.contentEquals("NA")) {
		    		skip = true;
		    	}
		    }
		    if(skip) {continue;}
		    String tailNum = record[12];
		    String carrier = record[10];
		    if(carrier.contentEquals("UA")) {
		    	System.out.println("found");
		    }
	    	System.out.println(carrier);
		    if(record[13].equals("EWR")) {
		    	EWRcounter++;
		    }
		    else if(record[13].equals("JFK")) {
		    	JFKcounter++;
		    }
		    else if(record[13].equals("LGA")) {
		    	temp = Integer.parseInt(record[16]);
		    	if(temp<LGA_minDistance) {
		    		LGA_minDistance = temp;
		    	};
		    	if(temp > LGA_maxDistance) {
		    		LGA_maxDistance =  temp;
		    	}
		    	LGA_dists.add(temp);
		    }
		}
		/*
		Collections.sort(LGA_dists);         
	    
	    double median;
	    int numElements = LGA_dists.size();
	    if (numElements % 2 == 0) {
	        int sumOfMiddleElements = LGA_dists.get(numElements / 2) + LGA_dists.get(numElements / 2 - 1);
	        // calculate average of middle elements
	        median = ((double) sumOfMiddleElements) / 2;
	     } else {
	        // get the middle element
	        median = (double) LGA_dists.get(numElements / 2);
	     }
	    int sum = 0;
	    for(int d : LGA_dists) {
	    	sum += d;
	    }
	    double mean = sum / LGA_dists.size();
		System.out.println("Min Distance :" + LGA_minDistance);
		System.out.println("Max Distance :" + LGA_maxDistance);
		System.out.println("Median :" + median);
		System.out.println("Mean :" + mean);
		*/
	}
	
}

