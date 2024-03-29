package test;

//import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;


import org.junit.Test;

import it.polito.po2024.esempi.lista.Lista;
import it.polito.po2024.esempi.lista.Lista.Iteratore;


public class TestLista {

    private Lista l;


    @Before
    public void setUp() { // fixture pre-test, il nome setUp è una convenzione
        l = new Lista();
    }

    @Test
    public void testNuovaLista(){
        assertEquals( 0, l.dimensione()); // con import static ...

        //Assert.assertEquals(0, l.dimensione()); // con import (semplice)
    }

    @Test
    public void testInserimento(){
        l.aggiungi( 3 );

        assertEquals( 1, l.dimensione());

        l.aggiungi( 3 );

        assertEquals( 2, l.dimensione());
    }

    @Test
    public void testScansione(){
        l.aggiungi( 5 );
        // tradotto con autoboxing in:
        l.aggiungi( Integer.valueOf(5) );
        l.aggiungi( 4 );
        l.aggiungi( 3 );

        Number v = l.primo();
        assertEquals( Integer.valueOf(3), v);

        v = l.prossimo();
        assertEquals( Integer.valueOf(4), v);

        v = l.prossimo();
        assertEquals( Integer.valueOf(5), v);
    }

    @Test
    public void testScansioneLoop(){
        l.aggiungi( 5 );
        l.aggiungi( 4 );
        l.aggiungi( 3 );


        Number v = l.primo();
        assertEquals( 3, v);

        while( !l.fine() ){
            v = l.prossimo();
        }
    }


    @Test
    public void testScansioneIteratore(){
        l.aggiungi( 5 );
        l.aggiungi( 4 );
        l.aggiungi( 3 );

        int numCombinazioni = 0;
        Iteratore it1 = l.iteratore();
        assertTrue(it1.esisteProssimo());
        while( it1.esisteProssimo() ){
            Number primo = it1.prossimo();
            Iteratore it2 = l.iteratore();
            assertTrue(it2.esisteProssimo());
            while( it2.esisteProssimo() ){
                Number secondo = it2.prossimo();
                System.out.println(primo + " - " + secondo);
                numCombinazioni++;
            }
            assertFalse(it2.esisteProssimo());
        }

        assertEquals( 9, numCombinazioni);
    }

    @Test
    public void testSomma(){
        l.aggiungi( 5 );
        l.aggiungi( 4.5 );
        l.aggiungi( 3 );

        assertEquals(12.5, l.somma(), 0.01);
    }
}
