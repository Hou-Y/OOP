package delivery;
import java.util.*;
import java.util.stream.*;

public class Order {
    private Map<String, Integer> piatti = new TreeMap<>();
    private String customer;
    private String rn;
   public int delTime;
    public int delD;
    private Integer id;
    private String cat;
    private Restaurant r ;
    public boolean pending = true;



    public Order(Restaurant r, Integer n, String []dishN, int[]quant, String cust, String rnam, int deliveryTime,int deliveryDistance){
        this.customer = cust;
        this.rn = rnam;
        this.delTime = deliveryTime;
        this.delD = deliveryDistance;
        this.id = n;
        this.r = r;
        this.cat = r.getC();
        //for(int i = 0; i<quant.size(); i++){
            int j = 0;
            for(String i : dishN){
                piatti.put(i, quant[j]);
                j++;
        }
    }

    public String getCAT(){
        return cat;
    }

    public Integer getid(){
        return id;
    }

}
