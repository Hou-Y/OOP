package jobOffers;

import java.util.*;
import java.util.stream.*;

public class Position {
    public final String pname;
    private Map<String, Integer> reqsk = new HashMap<>();
    private List<String> apply = new LinkedList<>();
    private Integer media;
    private Integer max;

    public Position(String name, String ... sk){
        this.pname = name;
        Integer j = 0; Integer max = 0;
        for(String i : sk){
            String k = i.split(":")[0];
            Integer lvl = Integer.valueOf(i.split(":")[1]);
            j++;
            max = max+lvl;
            this.reqsk.put(k, lvl);
        }
        this.media = max/j;
        this.max = max;
    }

    public Integer media(){
        return media;
    }

    public void addc(Candidate c){
        apply.add(c.getnamec());
    }

    public Integer getM(){
        return max;
    }

    public List<String > apllier (){
        apply.sort(Comparator.naturalOrder());
        return apply;
        //return apply.stream().forEach(s ->s.getnamec());
    }
    public Map<String, Integer> getreq(){return reqsk;}

    public boolean check (Candidate c){
        List <String> k = c.getS();
        if(k.containsAll(reqsk.keySet())){
            //apply.add(k);
            return true;
        } 
        return false;
    }

    public String gname(){
        return pname;
    }

}
