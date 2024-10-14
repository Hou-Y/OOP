package it.polito.med;
import java.util.*;
import java.util.stream.*;
import java.time.*;

public class Appoint implements Comparable <Appoint> {
    private Patient p ;
    private Doc d;
    private Integer id;
    private String aid;
    private String start;
    private String end;
    private String date;
    public boolean complete = false;

    public Appoint  (Patient p , Doc d, String slot, String date, Integer id){
        this.p = p;
        this.d = d;
        this.id = id;
        this.aid = id.toString();
        this.start =slot.split("-")[0];
        this.end = slot.split("-")[1];
        this.date = date;
    }

    public String getappstart(){return start;}
    public String getdoc(){return d.getid();}
    public Doc getd(){return d;}
    public Patient getp(){return p;}
    public String getappd(){return date;}
    public String getaid(){return aid;}

    @Override
	public int compareTo(Appoint o) {
		return this.start.compareTo(o.start);
	}


}
