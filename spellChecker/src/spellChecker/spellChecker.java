package spellChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class spellChecker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f = new File("words_alpha.txt");
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
    	int lineCounter = 0;
    	try {
    	    Scanner sc = new Scanner(f);


    	    while( sc.hasNextLine() ) {
    	    	lineCounter++;
    	        String word = sc.nextLine();	        
    	        
	    		hm.put(word.hashCode(), word);
//	    		System.out.println(hm.get(word.hashCode()));
    	    }
    	} 
    	catch (FileNotFoundException ex) {
    	    System.out.println("File "+f+" not found.");
    	}
		String test = "naame";
//		System.out.println(hm.get(test.hashCode()));
//    	System.out.println(lineCounter);
    	System.out.println("**********************");
    	
    	
// 		now reading shakespear    	
		f = new File("pg100.txt");
		HashSet<String> hs = new HashSet<String>();
    	lineCounter = 0;
    	
    	try {
    	    Scanner sc = new Scanner(f);
    	    while( sc.hasNextLine() ) {
    	    	String[] words ;
    	    	lineCounter++;
    	    	
    	        String lineFromFile = sc.nextLine();
//    	        if(lineCounter<174) {continue;}
    	        words = lineFromFile.split("\\W");
//    	        System.out.println(String.join(" ", words));
    	        for(String x : words) {
    	        	if(x.length()>1) {
    	        		System.out.println(x);
    	        		String word = x.toLowerCase();
    	        		hs.add(word);    	        	

    	        	}
    	        }
    	    }

    	} catch (FileNotFoundException ex) {
    	    System.out.println("File "+f+" not found.");
    	}
//    	System.out.println(lineCounter);
    	int errors = 0;
        Iterator itr = hs.iterator();
        while(itr.hasNext()){
            if(hm.get(itr.next().hashCode())==null) {
            	errors+=1;
            };
        }
	    System.out.println(errors);
    	System.out.println("**********************");
	}

}
