package it.polito.library;

import java.util.TreeMap;

public class Reader {
    private String fullName;
    private String readerID;
    private TreeMap<String, String> rentals;
    private boolean renting;

    
    public Reader(String name, String surname, String readerID) {
        this.fullName = name + " " + surname;
        this.readerID = readerID;
        this.renting = false;
        this.rentals = new TreeMap<String, String>();
    }
    
    public String getName() {
        return fullName;
    }
    
    public String getReaderID() {
        return readerID;
    }
    
    public TreeMap<String, String> getRentals() {
        return rentals;
    }
    
    
    public void startRental(String bookID, String startingDate) {
        
    	String dates = startingDate + " " + "ONGOING";
    	
    	rentals.put(bookID, dates);
    	
    	renting=true;
    	
    }

    public void endRental(String bookID, String endingDate) {
    	
    	String dates = rentals.get(bookID).split(" ")[0] + " " + endingDate;
    	    	
    	rentals.put(bookID, dates);
    	
    	this.renting=false;
    	
    }
    
    public boolean isRenting() {
    	
    	return this.renting;
    }
    
    public int getNRentals() {
    	
    	return rentals.values().size();
    }

}