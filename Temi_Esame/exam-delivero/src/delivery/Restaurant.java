package delivery;
import java.util.List;
import java.util.Map;

import java.util.*;
import java.util.stream.*;

public class Restaurant {
    private String name;
    private String cat;
    private List<Integer> ratin = new LinkedList<>();
    private Map<String, Order> custoOrder = new TreeMap<>();
    private Integer sum = 0;
    private float avg;

    List <Dish> dd = new ArrayList<>();

    public Restaurant(String na, String c){
        this.name = na;
        this.cat = c;
    }
    public boolean inrange(float min, float max){ for(Dish i : dd){ if(dd == null) return false; if(!(i.dprice > max || i.dprice<min) ) return true;} return false;}

    public float getavg(){
       float ss  = 0;
        if(ratin.size() == 0) return -1;
        for(Integer i : ratin){
            ss = (float) i+ss;
        }
        this.avg = sum/(float) ratin.size();
        return ss/(float) ratin.size(); 
    }

    public void addRatin(Integer r){
        ratin.add(r);
        this.sum = this.sum+r;
    }

    public List<Integer> getratin(){
        return ratin;
    } 

    public String getnam(){
        return name;
    }

    public String getC(){
        return cat;
    }

    public void addD(Dish d){
        dd.add(d);
    }

    public List<Dish> getDish(){
        return dd;
    }

}
