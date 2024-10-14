package it.polito.library;

import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.SortedMap;
 import java.util.LinkedList;
 import java.util.List;
 import java.util.stream.Collectors;

public class Book {
    public enum Status{READY, UNAVAILABLE};
    private String title;
    private String ID;

    private Integer copies = 1;
    public Status s;
    private Map <String, Rental> history = new TreeMap<>();


    public  Book(String title, String n){
        this.ID = n;
        this.title = title;
        this.s = Status.READY;

    }

    public void modr(Reader r, String en){
        Rental m =history.get(r.getID());
        m.endr(en);
       this.s = Status.READY;
    }

    public SortedMap<String, String> gerr(){
        Map <String, String> s =  this.history.values().stream().collect(Collectors.toMap(
            Rental::getr, Rental::gettime
        ) );
        return new TreeMap<>(s);
    }

    public void str(String r, Rental re){
        history.put(r, re);
        this.s = Status.UNAVAILABLE;
    }

    public String getID(){
        return ID;
    }

    public  Status getStatus(){
        return s;
    }

    public String getName(){
        return title;
    }

    public Integer getN(){
        return copies;
    }

    public void setN(int copy){
        this.copies = copy;
    }
    public Status isRented(){
        return s;
    }
}
