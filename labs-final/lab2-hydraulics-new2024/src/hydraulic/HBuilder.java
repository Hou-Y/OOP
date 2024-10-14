
package hydraulic;

/**
 * Hydraulics system builder providing a fluent API
 */
public class HBuilder {
    // è una lista di elementi?
    private Element head;
    private Element curr;
    private static int nFluentElem ;
    private static HSystem completo;
    //potrei anche aggiungere sempre al tail se solo non avessi split o multisplit
    
    //costruttore
    public HBuilder(){
        nFluentElem = 0;
    }

    /**
     * Add a source element with the given name
     * 
     * @param name name of the source element to be added
     * @return the builder itself for chaining 
     */
    public HBuilder addSource(String name) {
        
        //this.head.uscita[0] = tail;
        /*for(Element i : vect){
            if(name.equals(i.getName())){
                this.head = i; //visto che hanno la stessa reference
                //al momento se connetto curr a qualcosa ci connetto anche il head
                this.curr = i;
                break;
            }     
        }
        this.curr.name = name;*/
        this.head = new Source(name);
        curr = head;
        completo = new HSystem();
        completo.addElement(head);
        return this;
    }

    /**
     * returns the hydraulic system built with the previous operations
     * 
     * @return the hydraulic system
     */
    public HSystem complete() {
        //TODO: to be implemented
        return completo;
    }

    /**
     * creates a new tap and links it to the previous element
     * 
     * @param name name of the tap
     * @return the builder itself for chaining 
     */
    public HBuilder linkToTap(String name) {
        //TODO: to be implemented
        /*for(Element i: vect){
            if(name.equals(i.getName())){
                this.curr.connect(i, curr.uscitaCorrente); 
                break; 
            }
        }
        this.curr = this.curr.uscita[curr.uscitaCorrente];*/
        Element temp = new Sink(name);
        this.curr.connect(temp, curr.uscitaCorrente);
        nFluentElem ++;
        curr = curr.uscita[curr.uscitaCorrente];
        completo.addElement(curr);
        return this;
    }

    /**
     * creates a sink and link it to the previous element
     * @param name name of the sink
     * @return the builder itself for chaining 
     */
    public HBuilder linkToSink(String name) {
        //TODO: to be implemented
        /*for(Element i: vect){
            if(name.equals(i.getName())){
                this.curr.connect(i, curr.uscitaCorrente); 
                break; 
            }
        }
        this.curr = this.curr.uscita[curr.uscitaCorrente];*/
        Element temp = new Sink(name);
        this.curr.connect(temp, curr.uscitaCorrente);
        completo.addElement(curr.uscita[curr.uscitaCorrente]);
        nFluentElem ++;
        return this;
    }

    /**
     * creates a split and links it to the previous element
     * @param name of the split
     * @return the builder itself for chaining 
     */
    public HBuilder linkToSplit(String name) {
        //TODO: to be implemented
        /*for(Element i: vect){
            if(name.equals(i.getName())){
                this.curr.connect(i, curr.uscitaCorrente);
                
            }
            break;
        }
        this.curr = this.curr.uscita[curr.uscitaCorrente]; //passo al prossimo nodo
       */
        Element temp = new Split(name);
        this.curr.connect(temp, curr.uscitaCorrente);
        //curr = curr.uscita[curr.uscitaCorrente];
        nFluentElem ++;
        return this;
    }

    /**
     * creates a multisplit and links it to the previous element
     * @param name name of the multisplit
     * @param numOutput the number of output of the multisplit
     * @return the builder itself for chaining 
     */
    public HBuilder linkToMultisplit(String name, int numOutput) {
        //TODO: to be implemented
        /*for(Element i: vect){
            if(name.equals(i.getName())){
                this.curr.connect(i, curr.uscitaCorrente);
                //devo tenere un dato come numero corrente di uscite riempite;
                //curr.uscita[0].nElu = numOutput;
                //tecnicamente non serve visto che prendo proprio l'elemento
                //che contiene il numero di uscite già di suo
            
            }
            break;
        }
        this.curr = this.curr.uscita[curr.uscitaCorrente];*/ //passo al prossimo nodo
        //come lavoro sulle uscite?? 
        //come posso dire al then.() che al momento sto riempiendo l'uscita numero X
        Element temp = new Multisplit(name,numOutput);
        this.curr.connect(temp, curr.uscitaCorrente);
        //curr = curr.uscita[curr.uscitaCorrente];
        nFluentElem ++;
        return this;
    }

    /**
     * introduces the elements connected to the first output 
     * of the latest split/multisplit.
     * The element connected to the following outputs are 
     * introduced by {@link #then()}.
     * 
     * @return the builder itself for chaining 
     */
    public HBuilder withOutputs() {
        //TODO: to be implemented
        //in che senso specifico le differenti uscite???
        //cosa vuole che faccia??
        
        curr = curr.uscita[curr.uscitaCorrente];
        completo.addElement(curr);
        //in esempio passo dal Source al Multisplit, elem da collegare
        return this;
    }

    /**
     * inform the builder that the next element will be
     * linked to the successive output of the previous split or multisplit.
     * The element connected to the first output is
     * introduced by {@link #withOutputs()}.
     * 
     * @return the builder itself for chaining 
     */
    public HBuilder then() {
        //TODO: to be implemented
        //necessito del prenious OPPURE in un quale modo riesco a notificare linkto..
        //per non avanzare all'ucita del prossimo elemento ma rimanere in quello corrente
        //come tengo conto del numero di porta di uscita corrente???
        //curr = curr.entrata;
        curr.uscitaCorrente ++;
        return this;
    }

    /**
     * completes the definition of elements connected
     * to outputs of a split/multisplit. 
     * 
     * @return the builder itself for chaining 
     */
    public HBuilder done() {
        //TODO: to be implemented
        curr = curr.entrata;
        return this;
    }

    /**
     * define the flow of the previous source
     * 
     * @param flow flow used in the simulation
     * @return the builder itself for chaining 
     */
    public HBuilder withFlow(double flow) {
        this.curr.outFlow = flow;
        return this;
    }

    /**
     * define the status of a tap as open,
     * it will be used in the simulation
     * 
     * @return the builder itself for chaining 
     */
    public HBuilder open() {
        //TODO: to be implemented
        this.curr.inFlow = this.curr.outFlow;
			
        return this;
    }

    /**
     * define the status of a tap as closed,
     * it will be used in the simulation
     * 
     * @return the builder itself for chaining 
     */
    public HBuilder closed() {
        //TODO: to be implemented
        this.curr.outFlow = 0.0;
        return this;
    }

    /**
     * define the proportions of input flow distributed
     * to each output of the preceding a multisplit
     * 
     * @param props the proportions
     * @return the builder itself for chaining 
     */
    public HBuilder withPropotions(double[] props) {
        //TODO: to be implemented
        if(curr instanceof Multisplit)
            ((Multisplit)(curr)).setProportions(props);
        /*for(int i=0 ; i<curr.getnOut() ; i++){
            curr.setProportions() = props[i];
            curr.getProp(i); //The method getProp(int) is undefined for the type Element
        }*/
        //((Multisplit)(temp)).getMOutFlow();
        return this;
    }

    /**
     * define the maximum flow theshold for the previous element
     * 
     * @param max flow threshold
     * @return the builder itself for chaining 
     */
    public HBuilder maxFlow(double max) {
        //TODO: to be implemented
        this.curr.maxFlow = max;
        return this;
    }
}
