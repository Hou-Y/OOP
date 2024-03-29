package test;

import org.junit.Test;

public class TestInvarianza {
    @Test
    public  void testCoVarianze(){

        Integer[] numeri = {1,2,3};

        Object[] oggetti;

        // Gli array sono CO-VARIANTI

        oggetti = numeri;

        oggetti[1] = "DUE";

        int due = numeri[2].intValue();


    }

}
