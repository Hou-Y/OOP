package it.polito.library;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class LibraryManager {
	
	private TreeMap<String, Integer> titles;
    private TreeMap<String, Book> books;
    private TreeMap<String, Reader> readers;
    private int bookIDs = 1000;
    private int readerIDs = 1000;
    
    public LibraryManager() {
    	
    	this.titles = new TreeMap<>();
        this.books = new TreeMap<>();
        this.readers = new TreeMap<>();
        new HashSet<>();
    }
    
    // R1: Readers and Books 
    
    /**
	 * adds a book to the library archive
	 * The method can be invoked multiple times.
	 * If a book with the same title is already present,
	 * it increases the number of copies available for the book
	 * 
	 * @param title the title of the added book
	 * @return the ID of the book added 
	 */
    public String addBook(String title) {
        
    	String bookID = Integer.toString(bookIDs);
    	bookIDs=bookIDs+1;

    	
        Book book = new Book(title, bookID); 
        books.put(bookID, book);
        
        if (titles.containsKey(title)) {
			
			Integer count = titles.get(title) + 1;
			titles.put(title, count);// visto che ha la stessa chiave sostituisce il precedente valore con il nuovo valore
		
		} else {
			
			titles.put(title, 1);
			
		}
        
        return book.getID();
        
    }
    
    /**
	 * Returns the book titles available in the library
	 * sorted alphabetically, each one linked to the
	 * number of copies available for that title.
	 * 
	 * @param title the title of the added book
	 * @return a map of the titles liked to the number of available copies
	 */
    public SortedMap<String, Integer> getTitles() {    	
        
        return titles;
    }
    
    /**
	 * Returns the books available in the library
	 * 
	 * @param title the title of the added book
	 * @return a set of the book ids
	 */
    public Set<String> getBooks() {    	
        return books.keySet();
    }
    
    /**
	 * Adds a new reader
	 * 
	 * @param name first name of the reader
	 * @param surname last name of the reader
	 */
    public void addReader(String name, String surname) {
        
    		
    	String readerID = Integer.toString(readerIDs);
    	readerIDs++;
    	
        Reader reader = new Reader(name, surname, readerID);
        readers.put(readerID, reader);
        
    }
    
    
    /**
	 * Returns the reader name associated to a unique reader ID
	 * 
	 * @param readerID the unique reader ID
	 * @return the reader name
	 * @throw an exception if the readerID is not present in the archive
	 */
    public String getReaderName(String readerID) throws LibException {
        
    	if (!readers.containsKey(readerID)) {
            throw new LibException("Reader not found!");
        }
        
        Reader reader = readers.get(readerID);
        return reader.getName();
    }    
    
    
    // R2: Rentals Management
    
    
    /**
	 * Retrieves the bookID of a copy of a book if available
	 * 
	 * @param bookTitle the title of the book
	 * @return the unique book ID of a copy of the book or the message "Not available"
	 * @throw an exception if the book is not present in the archive
	 */
    public String getAvailableBook(String bookTitle) throws LibException {
           	
    	if (!titles.containsKey(bookTitle)) {
            throw new LibException("Title not found");
        }
    	             	
    	String availableBook = books.values().stream()
    	.filter(b -> b.getTitle().equals(bookTitle))
    	.filter(b -> b.isRented() == false)
    	.sorted(Comparator.comparing(Book::getID))
    	.map(Book::getID)
    	.findFirst().orElse("Not available");
    	
    	return availableBook;
      	
    }   

    /**
	 * Starts a rental of a specific book copy for a specific reader
	 * 
	 * @param bookID the unique book ID of the book copy
	 * @param readerID the unique reader ID of the reader
	 * @param startingDate the starting date of the rental
	 * @throw an exception if the book copy or the reader are not present in the archive,
	 * if the reader is already renting a book, or if the book copy is already rented
	 */
	public void startRental(String bookID, String readerID, String startingDate) throws LibException {
        
		if (!books.containsKey(bookID)) {
            throw new LibException("Book not found");
        }
        
        if (!readers.containsKey(readerID)) {
            throw new LibException("Reader not found");
        }
        
        Book book = books.get(bookID);
        Reader reader = readers.get(readerID);
        
        if (book.isRented() || reader.isRenting()) {
            throw new LibException("Ongoing rental");
        }
        
        book.startRental(readerID, startingDate);
        reader.startRental(bookID, startingDate);
    }
    
	/**
	 * Ends a rental of a specific book copy for a specific reader
	 * 
	 * @param bookID the unique book ID of the book copy
	 * @param readerID the unique reader ID of the reader
	 * @param endingDate the ending date of the rental
	 * @throw an exception if the book copy or the reader are not present in the archive,
	 * if the reader is not renting a book, or if the book copy is not rented
	 */
    public void endRental(String bookID, String readerID, String endingDate) throws LibException {
        
    	if (!books.containsKey(bookID)) {
            throw new LibException("Book not found");
        }
        
        if (!readers.containsKey(readerID)) {
            throw new LibException("Reader not found");
        }
        
        Book book = books.get(bookID);
        Reader reader = readers.get(readerID);
        
        if (!book.isRented() || !reader.isRenting()) {
            throw new LibException("No ongoing rental");
        }
        
        book.endRental(readerID, endingDate);
        reader.endRental(bookID, endingDate);
    }
    
    
   /**
	* Retrieves the list of readers that rented a specific book.
	* It takes a unique book ID as input, and returns the readers' reader IDs and the starting and ending dates of each rental
	* 
	* @param bookID the unique book ID of the book copy
	* @return the map linking reader IDs with rentals starting and ending dates
	* @throw an exception if the book copy or the reader are not present in the archive,
	* if the reader is not renting a book, or if the book copy is not rented
	*/
    public SortedMap<String, String> getRentals(String bookID) throws LibException {
        
    	if (!books.containsKey(bookID)) {
            throw new LibException("Book not found");
        }
        
        Book book = books.get(bookID);
        return book.getRentals();
    }
    
    
    // R3: Book Donations
    
    /**
	* Collects books donated to the library.
	* 
	* @param titles It takes in input book titles in the format "First title,Second title"
	*/
    public void receiveDonation(String donatedTitles) {
        
        String[] convertedTitles = donatedTitles.split(",");
    	
    	for (String title : convertedTitles) {
        	        	
    		addBook(title); 		
    		
    	}
    
    }
    
    // R4: Archive Management

    /**
	* Retrieves all the active rentals.
	* 
	* @return the map linking reader IDs with their active rentals

	*/
    public Map<String, String> getOngoingRentals() {
    	
    	TreeMap<String, String> ongoingRentals = new TreeMap<String, String>();
    	
    	for (Reader r : readers.values()) {
    		
    		String readerID = r.getReaderID();
    		    		
    		if (r.isRenting()) {
    			
    			Map<String, String> readerRentals = r.getRentals();
    			
    			for (String rentedBook : readerRentals.keySet()) {
    				
    				String rentalStatus = readerRentals.get(rentedBook).split(" ")[1];

    				if (rentalStatus.equals("ONGOING")) {
    					
    					ongoingRentals.put(readerID, rentedBook);
    				}
    			}
    			
    			
    		}
    		
    		
    	}
    	
    	return ongoingRentals;

    }
    
    /**
	* Removes from the archives all book copies, independently of the title, that were never rented.
	* 
	*/
    public void removeBooks() {
    	
    	ArrayList<String> toRemove = new ArrayList<String>();
    	
    	for (String b : books.keySet()) {
    		
    		String title = books.get(b).getTitle();
    		
    		if (books.get(b).getRentals().isEmpty()) {
    			
    			if (titles.get(title) > 1) {
    				
    				Integer count = titles.get(title) - 1;
    				titles.put(title, count);
    			
    			} else {
    				
    				titles.remove(title);
    				
    			}
    			
    			toRemove.add(b);
    			
    	    }
    			
    		}
    	
    	for (String b:toRemove) {
    		
    		books.remove(b);
    	}
        

    	}
    	
    // R5: Stats
    
    /**
	* Finds the reader with the highest number of rentals
	* and returns their unique ID.
	* 
	* @return the uniqueID of the reader with the highest number of rentals
	*/
    public String findBookWorm() {
    	
    	Reader r = readers.values().stream()
    	.sorted(Comparator.comparing(Reader::getNRentals).reversed())
    	.findFirst().get();
    	
    	return r.getReaderID();
    	
    	
    }
    
    /**
	* Returns the total number of rentals by title. 
	* 
	* @return the map linking a title with the number of rentals
	*/
    public Map<String,Integer> rentalCounts() {
    	return
    	this.books.values().stream()
    	.collect(Collectors.groupingBy( Book::getTitle, 
    									Collectors.summingInt(b->b.getRentals().size())));
        	
    }

}
