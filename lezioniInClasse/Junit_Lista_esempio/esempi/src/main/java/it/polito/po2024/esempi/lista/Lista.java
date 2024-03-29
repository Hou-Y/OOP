package it.polito.po2024.esempi.lista;

public class Lista extends Object {

    protected class Elemento {
        public Elemento(Number i) {
            valore = i;
            next = testa;  // funziona perchè siamo in una INNER CLASS
        }
        Number valore;
        Elemento next;
    }
    protected Elemento testa;
    protected int dimensione;

    private Elemento current;

    public int dimensione() {
        return dimensione;
    }
    // in C invece di int potrei usare void*
    public void aggiungi(Number i) {
        testa = new Elemento(i);
        dimensione++;
    }

    public Number primo() {
        this.current = testa;
        return testa.valore;
    }

    public Number prossimo() {
        current = current.next;
        return current.valore;
    }

    public boolean fine() {
        return current.next==null;
   }

   public class Iteratore { // inner class 
        private Elemento prossimo = testa;

        public Iteratore(){
            this.prossimo = testa;
        }

        public boolean esisteProssimo() {
            return prossimo != null;
        }

        public Number prossimo() {
            Number valore = prossimo.valore;
            prossimo = prossimo.next;
            return valore;
        }

    }

public Iteratore iteratore() {
    return new Iteratore();
}
public double somma() {
    double acc=0.0;

    Iteratore it = iteratore();
    while( it.esisteProssimo() ){
        Number prossimo = it.prossimo();
        acc += prossimo.doubleValue(); // dynamic binding
    }
    return acc;
}


}
