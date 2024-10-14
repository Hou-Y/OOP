package clinic;

import java.util.*;
import java.util.stream.*;
import java.time.*;

public class Pat {
    private String f; private String  l ;
    public String ssn;

    public Pat(String f, String  l ,String ssn){
        this.f = f;
        this.l = l; this.ssn = ssn;
    }

    public String getpfn(){return f;}
    public String getpln(){return l;}
    public String getpssn(){return ssn;}


    @Override
    public String toString(){
        return l+" "+f+" "+ssn;
    }


    public static List<LocalTime> orariLezioni(LocalTime da, LocalTime a){
        LocalTime current = da;
        ArrayList<LocalTime> res = new ArrayList<>();
        while( current.isBefore(a) ){
            res.add(current);
            current = current.plusMinutes(90);
            //riesco ad aggiungere un'unità alla volta (ma li posso concatenare volendo)
            //anche se aggiungere 90 minuti tutto insieme è più compatto
            //current.plusHours(1).plusMinutes(30);
        }
        return res;

    }
}
