package social;


import java.util.*;

public class Group {
    private String name;
    private Set<String> peop = new HashSet<>();
    private int size = 0;

    public Group(String n){
        this.name = n;
    }

    public int getsize(){
        return size;
    }

    public String getName(){
        return name;
    }

    public void addPerson(String c){
        peop.add(c);
        this.size ++;
    }

    public Set<String> ppg(){
        return peop;
    }

}
