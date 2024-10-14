package it.polito.tvseriesdb;
import java.util.*;
import java.util.stream.*;

public class TV {
    private String name;
    private String se;
    private String ge;
    private Integer n = 0;

    public Set<Actor> actors = new HashSet<>();
    public List<Integer> eps = new ArrayList<>();
    public Map<Integer, String > dates= new TreeMap<>();
    public Map<Integer, Season> episodes = new TreeMap<>();
    public List<String> userlike = new LinkedList<>();
    public List<Integer> scores = new LinkedList<>();
    private Integer sum = 0;

    public TV(String title,String  tService,String genre){
        this.name = title;
        this.se = tService;
        this.ge = genre;
    }
    public Collection<Integer> g (){return dates.keySet();}

    public void addscore(Integer i){
        this.scores.add(i);
        this.sum = this.sum+i;
    }

    public Integer getsum(){return sum;}

    public String getname(){return name;}
    public String getservice(){return se;}
    public String getgenre(){return ge;}

    public void addA(Actor a){
        this.actors.add(a);
    }

    public Set<Actor> getA(){
        return actors;
    }
    
    public void adds(Integer ep, String re){
        eps.add(n, ep);
        dates.put(n ,re);
        episodes.put(n, new Season(ep));
        this.n++;
    }

    public Integer gets(){return n;}

    public void addtolike(String o){
        userlike.add(o);

    }

    public List<String> getuserlike(){
        return userlike;
    }


}
