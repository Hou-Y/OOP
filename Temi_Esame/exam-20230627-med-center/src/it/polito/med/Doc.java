package it.polito.med;
import java.util.*;
import java.util.stream.*;
import java.time.*;

public class Doc// extends Patient{
    {
    public String id;
    private String name;
    private String sur;
    private String spe;

    //per chiave il giorno
    public Map<String , Daily> schedule = new TreeMap<>();

    public List<Appoint> aa = new LinkedList<>();

    public Doc(String id, String name ,String surname, String speci){
        this.id = id;
        this.name = name;
        this.sur = surname;
        this.spe = speci;
    }

    public String getspe(){return spe;}
    public String getsurn(){return sur;}
    public String getname(){return name;}
    public String getid(){return id;}

    public Daily addSche(String date, String start, String end, int duration){
        schedule.put(date, new Daily(start, end, duration));
        return schedule.get(date);
    };

    public Map<String ,Daily> getsche(){return schedule;}

    public void adda(Appoint a){
        aa.add(a);
    }

    public List<Appoint> geta(){return aa;}
    public Double gc(){
        //Double r = schedule.values().stream().mapToDouble(s ->s.getslots().size()).sum();
        return (double) aa.size()/schedule.values().stream().mapToDouble(s ->s.getslots().size()).sum();}
}
