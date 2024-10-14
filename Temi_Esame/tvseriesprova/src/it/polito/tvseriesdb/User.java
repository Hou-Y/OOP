package it.polito.tvseriesdb;
import java.util.*;
import java.util.stream.*;

public class User {
    private String name;
    private String genre;
    public List<String> liked = new LinkedList<>();
    public List<Integer> sc = new LinkedList<>();
    public Map<String, Integer> reviewdMap = new TreeMap<>();
    public Map<String, Integer> likedMap = new TreeMap<>();
    private Integer sum = 0;

    public User(String n , String g){
        this.name = n;
        this.genre = g;
        //this.liked.add(g);
    }

    public void addsc(Integer s){
        sc.add(s); 
        this.sum = this.sum+s;
    }
    public Integer getsum(){return sum;}

    public List<Integer> getsc(){return sc;}

    public void addlike(String s){
        liked.add(s);
    }

    public String getugenre(){return genre;}

    public List<String> likes(){
        return liked;
    }

}
