package jobOffers;

import java.util.*;
import java.util.stream.*;

public class Candidate {
    private String name;
    private  List <String> skillz = new LinkedList<>();
    private Integer nk ;
    
    private Map<String, Integer>rate = new TreeMap<>();
    private Integer media;

    public Candidate(String n, String ... k){
        Integer j = 0;
        this.name = n;
        
        for(String i: k)  { 
             skillz.add(i);
             j++;
            }
        this.nk = j;
    }

    public Integer gemm(){
        return media;
    }
    //ma se il candidato NON ha ancora ricevuto NESSUNA review da un consulente?
    public boolean eligible(Map<String, Integer> req){ for(String i : req.keySet()){if(rate.size()==0 || (rate.get(i) < req.get(i))) return false; }return true; }

    public Map<String, Integer> getr(){return rate;}
    public Integer getn(){
    return nk;}

    public List<String> getS(){
        return skillz;
    }

    public String getnamec(){
        return name;
    }

    public void addrev(String ... sk){
        int j = 0; Integer max = 0;
        for(String i : sk){
            String k = i.split(":")[0];
            Integer lvl = Integer.valueOf(i.split(":")[1]);
            j++;
            max = max+lvl;
            this.rate.put(k, lvl);
        }
        this.media = max/j;
    }


}




