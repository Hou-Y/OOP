package it.polito.library;

import java.util.TreeMap;

public class Book {
	
	private String title;
    private String bookID;
    private TreeMap<String, String> rentals;
    private boolean rented;
    
    public Book(String title, String bookID) {
        this.title = title;
        this.bookID = bookID;
        this.rentals = new TreeMap<String, String>();
        this.rented=false;
    }
    

	public String getTitle() {
        return title;
    }
    
    public String getID() {
        return bookID;
    }
    
    public TreeMap<String, String> getRentals() {
        return rentals;
    }
    
    public void startRental(String readerID, String startingDate) {
        
    	String dates = startingDate + " " + "ONGOING";
    	
    	rentals.put(readerID, dates);
    	
    	rented=true;
    	
    }
    
    public void endRental(String readerID, String endingDate) {
        	
    	String dates = rentals.get(readerID).split(" ")[0] + " " + endingDate;
    	    	
    	rentals.put(readerID, dates);
    	
    	rented=false;
    	
    }
    
    
    public boolean isRented() {
    	
    	return this.rented;
    }
}


