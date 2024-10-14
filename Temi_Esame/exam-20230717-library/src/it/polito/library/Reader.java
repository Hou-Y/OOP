package it.polito.library;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.SortedMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.LinkedList;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.Collections.*;
import java.util.Collection;
import java.util.stream.Stream; 

public class Reader {
    private String name;
    private String surn;
    private String ID;
    private boolean r;

    private Map<String , Rental> history = new TreeMap<>();

    public Reader(String n, String s, String id){
        this.name = n;
        this.surn = s;
        this.ID = id;
        this.r=false;

    }

    public void stb(String r, Rental re){
        history.put(r, re);
        this.r=true;
    }


    public int getR(){
        return history.values().size();
    }

    public void modb(String b){
        Rental m =history.get(b);
        this.r=false;
    }

    public String getID(){
        return ID;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surn;
    }
    public boolean isRenting(){
        return r;
    }

}
