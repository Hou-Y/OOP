package it.polito.tvseriesdb;
import java.util.*;
import java.util.stream.*;

public class Season {
    public Map<Integer, String> eplist = new TreeMap<>();
    public Integer curNEps = 0;
    public  Integer totEp;

    public Season(Integer e){
        this.totEp = e;
    }

    public void addep(String episodeTitle){
        

        this.curNEps ++;
        this.eplist.put(curNEps,episodeTitle);
    }

}
