package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.po2024.esempi.lista.Lista;
import it.polito.po2024.esempi.lista.ListaUniversale;
import it.polito.po2024.esempi.lista.ListaUniversale.Iteratore;

public class TestListaUniversale {

    @Test
    public void testLista(){
        ListaUniversale l = new ListaUniversale();

        l.aggiungi(3);
        l.aggiungi("tre");
        l.aggiungi(new ListaUniversale());

        Iteratore it = l.iteratore();
        while(it.esisteProssimo()){
            Object prossimo = it.prossimo();
            System.out.println(prossimo);
            assertNotNull(prossimo);
        }

    }

    @Test
    public void testContiene(){
        ListaUniversale l = new ListaUniversale();

        l.aggiungi(3);
        l.aggiungi("tre");
        l.aggiungi(new ListaUniversale());

        assertTrue( l.contiene( Integer.valueOf(3) ));
        assertFalse( l.contiene( Integer.valueOf(4) ));

        assertTrue( l.contiene( new ListaUniversale() ));
    }
}
