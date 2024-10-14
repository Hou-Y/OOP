package it.polito.tvseriesdb;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.stream.*;

public class Actor {
    private String name;
    private String su;
    private String nat;
    private String tn;

    public Set<TV> tvs = new HashSet<>();

    public Actor(String name,String surna,String genre){
        this.name = name;
        this.su = surna;
        this.nat = genre;
        this.tn = name+" "+surna;
    }

    public String getnamec(){return tn;}
    public String getname(){return name;}
    public String getsurn(){return su;}
    public String getnat(){return nat;}

    public void addTVS(TV o){
        this.tvs.add(o);
    }

}
