package it.polito.oop.futsal;
import it.polito.oop.futsal.Fields.Features;

import java.util.*;
import java.util.stream.*;

public class Field implements FieldOption {
    public Features f ;
    public Integer noCampo ;
    public Map<String, Associate> book = new TreeMap<>();
    private int tot = 0 ;
    private int diff = 0 ;
    //primo slot corrisponde all'ora di apertura

    public Field (Features fs, Integer no){

        
        this.f = fs;
        this.noCampo = no;

    }

    public Map<String, Associate> retb(){
        return book;
    }

    public void bookf(Associate a, String time, int h, int m, int diff){
        if(diff == 0) this.diff = diff;

        book.put(time, a);
        tot++;
    }

    public int getnoc(){
        return tot;
    }

    public boolean checkBooked(Integer ora, Integer minuto, String time){
        boolean o = false;
        if(this.book.get(time)!= null) o = true;
        //if()
        for(String i : book.keySet()){
            Integer k = Integer.valueOf(i.split(":")[0]);
            if(k.equals(ora)) o = true;
        }
        return o;
    }

    public int getField(){
        return noCampo;
    }

    public int getOccupation(){ return book.size();}
    public boolean match(Features req){if(req.ac && !f.ac){return false; } if(req.indoor && !f.indoor){return false;} if(req.heating && !f.heating){return false;} return true;}
}
