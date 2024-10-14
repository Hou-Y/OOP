package it.polito.project;

import java.util.*;
import java.util.stream.*;

public class Review {

    private String name;
    private String topic;
    private String id ;
    public boolean openPool = false;

    public List<Time> slot = new LinkedList<>();
    public Map<String, List<String>> rSlot = new HashMap<>();
    public Map<String, List<Time>> fuck = new HashMap<>();
    public Time time;
    public  String day ;
    //public double duration;

    public Review(String title, String t, String id){
        this. name = title;
        this.topic = t;
        this.id = id;

    }

    public String getRtitle(){
        return name;
    }

    public Time getTime(){
        return time;
    }

    public List<Time> getallt(){
        return slot;
    }

    public double addTime(String date , String st, String end){
        this.day = date;
        if(!rSlot.keySet().contains(date)){List <String> l = new LinkedList<>(); List <Time> f = new LinkedList<>();
        this.time = new Time(st,end);
        l.add(st+"-"+end); f.add(this.time);rSlot.put(date, l ); fuck.put(date, f);
        //duration = time.Rdurat;
        slot.add(this.time);} else {rSlot.get(date).add(st+"-"+end); fuck.get(date).add(this.time = new Time(st, end));}
        return time.Rdurat;

    }

    public String getRid(){
        return id;
    }


    public String getRtopic(){
        return topic;
    }

}
