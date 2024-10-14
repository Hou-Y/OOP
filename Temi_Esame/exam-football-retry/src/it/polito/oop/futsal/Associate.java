package it.polito.oop.futsal;

import java.util.*;
import java.util.stream.*;

public class Associate {
    private String name;
    private String surn;
    private String mobi;
    private Integer id;

    public Associate(String n, String s, String m, int id){
        this.name = n;
        this.surn = s;
        this.mobi = m;
        this.id = id;
    }

    public String getF(){
        return name;
    }

    public String geL(){
        return surn;
    }

    public String getL(){
        return surn;
    }

    public String getP(){
        return mobi;
    }

}
