package it.polito.po2024.esempi.lista;

// extends : eredita da : è una sotto-classe di : è un sottotipo di
public class ListaOrdinata extends Lista {

    private static boolean maggioreDi(Number n1, Number n2){
        return n1.doubleValue() >= n2.doubleValue();
    }

    @Override
    public void aggiungi(Number i) {
        if(testa==null || maggioreDi(testa.valore,i)){
            testa = new Elemento(i); 
        }else{
            Elemento c = testa;
            while( true ){
                if(c.next==null || maggioreDi(c.next.valore,i)){
                    Elemento e = new Elemento(i);
                    e.next = c.next;
                    c.next = e;
                    break;
                }
                c = c.next;
            }
        }
        dimensione++;
        // ... ordinare gli elementi
    }

    public Number minimo(){
        return testa.valore;
    }

}
