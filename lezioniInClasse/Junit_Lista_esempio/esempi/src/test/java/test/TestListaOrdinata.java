package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import it.polito.po2024.esempi.lista.Lista;
import it.polito.po2024.esempi.lista.ListaOrdinata;
import it.polito.po2024.esempi.lista.Lista.Iteratore;

public class TestListaOrdinata {

    private ListaOrdinata l;


    @Before
    public void setUp() { // fixture pre-test, il nome setUp è una convenzione
        l = new ListaOrdinata();

        l.aggiungi(3);
        l.aggiungi(7);
        l.aggiungi(1);
        l.aggiungi(5);

    }

    @Test
    public void testSomma(){ // esattamente come la Lista (normale)

        assertEquals(16, l.somma(), 0.01);
    }

    @Test
    public void testOrdinamento(){
    
        // mi aspetto che la scansione mi faccia visitare
        // gli elementi "in ordine"

        Number prec = Double.MIN_VALUE;
        Iteratore it = l.iteratore();
        while( it.esisteProssimo() ){
            Number corrente = it.prossimo();
            assertTrue( "Non sono in ordine " + prec + " > " + corrente,
                        prec.doubleValue() <= corrente.doubleValue()
            );
            prec = corrente;
        }
    
    }

    @Test
    public void testMinino(){
        assertEquals("Valore minimo errato",
                     Integer.valueOf(1), l.minimo());
    }


}
