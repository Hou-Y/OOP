package it.polito.project;

import java.util.*;
import java.util.stream.*;

public class Time {
    private String open;
    private String end;

    public Integer hopen;
    public Integer mopen;
    public Integer hclose;
    public Integer mclose;

    public double Rdurat;

    public Time(String st, String eend){
        this.open = st;
        this.end = eend;
        hopen = Integer.valueOf(st.split(":")[0]);
        mopen = Integer.valueOf(st.split(":")[1]);
        hclose = Integer.valueOf(eend.split(":")[0]);
        mclose = Integer.valueOf(eend.split(":")[1]);
        this.Rdurat = (hclose - hopen)+ (double )mclose/60 - (double) mopen/60;

    }

    public boolean checkin(Time in){
        if (in.hclose <= hopen || in.hopen >= hclose )
            return true;
        return false;

    }

    public String getS(){
        return open;
    }

    public String getE(){
        return end;
    }

}
