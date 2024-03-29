package it.polito.po2024.esempi.lista;

public class ListaUniversale {

    protected class Elemento {
        public Elemento(Object i) {
            valore = i;
            next = testa;  // funziona perchè siamo in una INNER CLASS
        }
        Object valore;
        Elemento next;
    }
    protected Elemento testa;
    protected int dimensione;

    private Elemento current;

    public int dimensione() {
        return dimensione;
    }
    
    /**
     * Aggiunge un nuovo elemento alla lista
     * @param i
     */
    public void aggiungi(Object i) {
        testa = new Elemento(i);
        dimensione++;
    }

    public Object primo() {
        this.current = testa;
        return testa.valore;
    }

    public Object prossimo() {
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

        public Object prossimo() {
            Object valore = prossimo.valore;
            prossimo = prossimo.next;
            return valore;
        }

    }

    public Iteratore iteratore() {
        return new Iteratore();
    }

    @Override
    public String toString(){
        String result = "[ ";
        Iteratore it = iteratore();
        while(it.esisteProssimo()){
            result += it.prossimo().toString();
            if(it.esisteProssimo()){
                result += ", ";
            }
        }
        return result + "]";
    }
    public boolean contiene(Object el) {
        Iteratore it = iteratore();
        while(it.esisteProssimo()){
            if(it.prossimo().equals(el)){
                return true;
            }
        }
        return false;
    }

    private static final boolean DEEP_EQUALS = false;
    @Override
    public boolean equals(Object o){
        if( o instanceof ListaUniversale l){
            //ListaUniversale l = (ListaUniversale)o;
            if( this.dimensione != l.dimensione){
                return false;
            }
            // SHALLOW EQUALS
            Iteratore mio = iteratore();
            Iteratore suo = l.iteratore();
            while(mio.esisteProssimo()){
                if(DEEP_EQUALS)
                    if( ! mio.prossimo().equals(suo.prossimo())){ // deep
                        return false;
                    }
                else
                    if( mio.prossimo() != suo.prossimo()){ // shallow
                        return false;
                    }
            }
            return true;
        }
        return false;
    }

}
