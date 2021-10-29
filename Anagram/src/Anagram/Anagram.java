package Anagram;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;



public class Anagram {
	public static String sortCharacters( String s ) {
		char[] chArray = s.toCharArray();
		Arrays.sort(chArray);
		return new String(chArray);
	}
	
    public static void main(String[] args){
		// TODO Auto-generated method stub
		File f = new File("words_alpha.txt");
		HashMap<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();
    	int lineCounter = 0;
    	try {
    	    Scanner sc = new Scanner(f);
    	    while( sc.hasNextLine() ) {
    	    	lineCounter++;
    	        String line = sc.nextLine();
    	        
    	        String key = sortCharacters(line);
    	        ArrayList<String> values = new ArrayList<String>();
    	        ArrayList<String> testVal = new ArrayList<String>();
    	        testVal = hm.getOrDefault(key, values);
    	    	if(hm.get(key) == null) {
    	    		values.add(line);
    	    		hm.put(key, values);
    	    	}
    	    	else {
    	    		testVal.add(line);
    	    		hm.put(key, testVal);
    	    	}

//    	        System.out.println(sortCharacters(line));
//    	        System.out.println(line);
    	    }

    	} catch (FileNotFoundException ex) {
    	    System.out.println("File "+f+" not found.");
    	}
//    	System.out.println(hs.get(sortCharacters("search")));
	    System.out.println(hm.get(sortCharacters("search")));

	    Iterator it = hm.entrySet().iterator();
	    int counter=0;
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
//	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        int size = hm.get(pair.getKey()).size();
	        if(size>counter) {counter=size;}
//	        if(pair.getValue().size()>counter) {counter = pair.getValue().size();}
//	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    System.out.println(counter);
	    System.out.println(hm.get(sortCharacters("search")));
	    System.out.println("AaBB".hashCode());
	    System.out.println("BBBB".hashCode());
    	
	}

}
