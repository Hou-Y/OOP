package it.polito.library;

//la mia gioia all'esame quando dovrò scrivere tutte queste librerie per fare partire la pipeline
// solo per non avere una bocciatura automatica

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections.*;
import java.util.Collection;
import java.util.stream.Stream; 




public class LibraryManager {
	Map <String, Book> library;
	Map <String , Reader> lettori = new TreeMap<>();
	private int numBook = 0; //come per lab university
	private int numr = 0;



	public LibraryManager(){
		library = new TreeMap<>();
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
		/*Integer o = 1;
		for(Book i : library.values()){
			if (i.getName().equals(title)){
				o = i.getN()+1;
			}	
		}
		Book b = new Book(title, o);
		library.put(title, b); //aggiunto durante correzione
        return b.getId();*/
		Integer id = 1000+numBook;	
		Book b = new Book(title, id.toString());
		int copia =(int) library.entrySet().stream().filter( s-> s.getValue().getName().equals(title)).count();
        b.setN(copia+1);
		library.put(b.getID(), b);
		numBook ++;
		return b.getID();
    }
    
    /**
	 * Returns the book titles available in the library
	 * sorted alphabetically, each one linked to the
	 * number of copies available for that title.
	 * 
	 * @return a map of the titles liked to the number of available copies
	 */
    public SortedMap<String, Integer> getTitles() {  
		Map <String, Integer> s = library.values().stream().collect(Collectors.groupingBy(Book::getName, 
		Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));	
        return new TreeMap<>(s);
    }
    
    /**
	 * Returns the books available in the library
	 * 
	 * @return a set of the titles liked to the number of available copies
	 */
    public Set<String> getBooks() {    	    	
        // devo ottenere le chiavi (che sono Integer), trasformarle in Stringhe e fare il set di tutte le chiavi	    	
        return library.keySet() //ottengo gli Integer che sono le chiavi
				.stream()  //lo trasformo in uno stream
				.map(String :: valueOf) //conversion di uno stream di Integer in UNO STREAM DI STRINGHE (usando la funzione valueOf() )
				//valueOf è DENTRO la classe String (NON scrivo string perchè diventa uno string bensì perchè  E' DENTRO LA CLASSE chiamata String)
				.collect(Collectors.toSet()); //lo stream lo raccolgo in un set di stringhe
		/*return library.keySet().stream().toString()
				.collect(Collectors.toSet()); 
				USARE toString() dà errore -> trasforma TUTTO lo stream in un string, e non posso usare collect con qualcosa che non 
				è più uno stream*/
    }
    
    /**
	 * Adds a new reader
	 * 
	 * @param name first name of the reader
	 * @param surname last name of the reader
	 */
    public void addReader(String name, String surname) {
		Integer id = numr+1000;
		Reader r = new Reader(name, surname, id.toString());
		lettori.put(r.getID(), r);
		numr ++;

    }
    
    
    /**
	 * Returns the reader name associated to a unique reader ID
	 * 
	 * @param readerID the unique reader ID
	 * @return the reader name
	 * @throws LibException if the readerID is not present in the archive
	 */
    public String getReaderName(String readerID) throws LibException {
		for(Reader i: lettori.values()){
			if(i.getID().equals(readerID)){
					return i.getName()+ " "+i.getSurname();
			}
		}
		throw new LibException("Id non presente");
    }    
    
    
    // R2: Rentals Management
    
    
    /**
	 * Retrieves the bookID of a copy of a book if available
	 * 
	 * @param bookTitle the title of the book
	 * @return the unique book ID of a copy of the book or the message "Not available"
	 * @throws LibException  an exception if the book is not present in the archive
	 */
    public String getAvailableBook(String bookTitle) throws LibException {
		if(getTitles().keySet().contains(bookTitle))
        	return library.values().stream()
			.filter(s -> s.getName().equals(bookTitle) && s.getStatus().equals(Book.Status.READY)).findFirst()
			.map(Book::getID).orElse(("Not available"));
		throw new LibException("non esiste");
		//return null;
    }   

    /**
	 * Starts a rental of a specific book copy for a specific reader
	 * 
	 * @param bookID the unique book ID of the book copy
	 * @param readerID the unique reader ID of the reader
	 * @param startingDate the starting date of the rental
	 * @throws LibException  an exception if the book copy or the reader are not present in the archive,
	 * if the reader is already renting a book, or if the book copy is already rented
	 */
	public void startRental(String bookID, String readerID, String startingDate) throws LibException {
    if (library.containsKey(bookID) && lettori.containsKey(readerID)){
		Book b = library.get(bookID);
		Reader r = lettori.get(readerID);
		if (b.isRented().equals(Book.Status.UNAVAILABLE) || r.isRenting()) {
            throw new LibException("Ongoing rental");
        }
		Rental re = new Rental(b, r, startingDate);
		b.str(readerID, re);
		r.stb(bookID, re);
		return;
	}
	throw new LibException("libro o lettore non esite");
	}
    
	/**
	 * Ends a rental of a specific book copy for a specific reader
	 * 
	 * @param bookID the unique book ID of the book copy
	 * @param readerID the unique reader ID of the reader
	 * @param endingDate the ending date of the rental
	 * @throws LibException  an exception if the book copy or the reader are not present in the archive,
	 * if the reader is not renting a book, or if the book copy is not rented
	 */
    public void endRental(String bookID, String readerID, String endingDate) throws LibException {
		if (library.containsKey(bookID) && lettori.containsKey(readerID)){
			Book b = library.get(bookID);
			Reader r = lettori.get(readerID);
			b.modr(r, endingDate);
			r.modb(b.getID());
			return;
		}
		throw new LibException("libro o lettore non esite");
		
	}
    
    
   /**
	* Retrieves the list of readers that rented a specific book.
	* It takes a unique book ID as input, and returns the readers' reader IDs and the starting and ending dates of each rental
	* 
	* @param bookID the unique book ID of the book copy
	* @return the map linking reader IDs with rentals starting and ending dates
	* @throws LibException  an exception if the book copy or the reader are not present in the archive,
	* if the reader is not renting a book, or if the book copy is not rented
	*/
    public SortedMap<String, String> getRentals(String bookID) throws LibException {
		if (!library.containsKey(bookID)) {
            throw new LibException("Book not found");
        }
		return library.get(bookID).gerr();
	
    }
    
    
    // R3: Book Donations
    
    /**
	* Collects books donated to the library.
	* 
	* @param donatedTitles It takes in input book titles in the format "First title,Second title"
	*/
    public void receiveDonation(String donatedTitles) {
		String[] d = donatedTitles.split(",");
		for(String i : d){
			addBook(i);
		}
    }
    
    // R4: Archive Management

    /**
	* Retrieves all the active rentals.
	* 
	* @return the map linking reader IDs with their active rentals

	*/
    public Map<String, String> getOngoingRentals() {
        return null;
    }
    
    /**
	* Removes from the archives all book copies, independently of the title, that were never rented.
	* 
	*/
    public void removeBooks() {
		for(Book i : library.values()){
			if(i.gerr() == null){
				library.remove(i);
			}
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
        return lettori.values().stream()
		.sorted(Comparator.comparing(Reader::getR).reversed())
		.findFirst().get().getID();
    }
    
    /**
	* Returns the total number of rentals by title. 
	* 
	* @return the map linking a title with the number of rentals
	*/
    public Map<String,Integer> rentalCounts() {
        return null;
    }

}
