package MA2_csvReader;

import com.opencsv.bean.CsvBindByName;

public class Flight {
	
		@CsvBindByName
	    private int year;

	    @CsvBindByName
	    private int month;

	    @CsvBindByName
	    private int day;
	    
	    @CsvBindByName
	    private int dep_time;
	    
	    @CsvBindByName
	    private int sched_dep_time;
	    
	    @CsvBindByName
	    private int dep_delay;
	    
	    @CsvBindByName
	    private int arr_time;
	    
	    @CsvBindByName
	    private int sched_arr_time;

	    @CsvBindByName
	    private int arr_delay;
	    @CsvBindByName
	    private int carrier;
	    @CsvBindByName
	    private int flight;
	    @CsvBindByName
	    private int tailnum;
	    @CsvBindByName
	    private int origin;
	    @CsvBindByName
	    private int dest;
	    
	    @CsvBindByName
	    private int air_time;
	    
	    @CsvBindByName
	    private int distance;
	    
	    @CsvBindByName
	    private int hour;
	    
	    @CsvBindByName
	    private int minute;
	    
	    @CsvBindByName
	    private int time_hour;
	    
	    
	    public int getIndex() {
	        return this.index;
	    }

	    public void setIndex(int newIndex) {
	        this.index = newIndex;
	    }

	
}
