package it.polito.oop.elective;
import java.util.*;
import java.util.stream.*;

public class Student {
    private String name;
    private Double avg;
    private Integer siz;
    private int nscelta = -1 ;

    public final List<String> pref = new LinkedList<>();
    public Course enrolled ;

    public Student(String n , Double av){
        this.name = n;
        this.avg = av;
    }

    public void addC(List<String> courses){
        this.siz = courses.size();
        for(String i : courses)
            this.pref.add(i);
        
    }

    /*public void addPref(Course c){
        pref.add(c);
    }*/

    public Double getavg(){ return avg;}

    public String getname(){return name;}

    public void enroll(Course c, int i){ this.enrolled = c; this.nscelta = i;}
    public int getscelta(){return nscelta;}

   

}
