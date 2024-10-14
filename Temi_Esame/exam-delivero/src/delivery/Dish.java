package delivery;
//package delivery;
import java.util.List;
import java.util.Map;

import java.util.*;
import java.util.stream.*;

public class Dish {
    public String dname;
    public float dprice;

    public Dish (String na, float p){
        this.dname = na;
        this.dprice = p;
    }

    public String getDN(){
        return dname;
    }

}
