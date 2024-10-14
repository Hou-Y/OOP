package discounts;
import java.util.*;
import java.util.stream.*;

public class Utente {
    private String name;
    private Integer n;

    public final List<Purch> acq = new LinkedList<>();

    public Utente(String name, Integer n){
        this.name = name;
        this.n = n;
    }

    public String getName(){
        return name;
    }
    public List<Purch> getP(){return acq;}

    public Integer getN(){
        return n;
    }

    public void addP(Purch p){
        acq.add(p);
    }


}
