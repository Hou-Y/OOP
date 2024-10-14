package discounts;
import java.util.*;
import java.util.stream.*;

public class Purch {
    public Integer pid;
    public Integer cardn;
    public double disc;

    public Map<String ,Integer> prodl = new TreeMap<>();
    public Integer tot = 0;
    //public Integer disc = -1;

    public Purch(Integer n, int id, String ... k){
        this.cardn = id;
        this.pid = n;
       for(String i : k) {
            String a= i.split(":")[0];
            Integer b = Integer.valueOf(i.split(":")[1]);
            prodl.put(a, b);

            this.tot = this.tot+b;
        }

    }

    public Purch(Integer n,  String ... k){
        this.cardn = -1;
        this.pid = n;
       for(String i : k) {
            String a= i.split(":")[0];
            Integer b = Integer.valueOf(i.split(":")[1]);
            prodl.put(a, b);

        }

    }

    public Map<String, Integer> getItems(){
        return prodl;
    }

    public Integer getpid(){return pid;}

    public void discAmount(double d){
        this.disc = d;
    }
}
