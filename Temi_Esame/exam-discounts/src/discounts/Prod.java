package discounts;

import java.util.*;
import java.util.stream.*;

public class Prod {
    public String cat;
    public String prod;
    public double price;
    

    public Prod(String categoryId, String productId, double price){
        this.cat = categoryId;
        this.prod = productId;
        this.price = price;

    }

    public double getp(){
        return price;
    }

    

}
