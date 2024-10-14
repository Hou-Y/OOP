package hydraulic;

import java.util.Arrays;

/**
 * Main class that acts as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {
	//private final int MAX_HS = 100;
	private  Element[] system =new Element[100];
	protected  int nElem = 0;
	

	/*public class currentSys extends HSystem{
		protected Element[] currs = new Element[nElem];
	}*/

// R1
	/**
	 * Adds a new element to the system
	 * 
	 * @param elem the new element to be added to the system
	 */
	public void addElement(Element elem){
		system[nElem] = elem;
		nElem ++;
	}
	
	/**
	 * returns the element added so far to the system
	 * 
	 * @return an array of elements whose length is equal to 
	 * 							the number of added elements
	 */
	public Element[] getElements(){
		//devo creare un vettore grande esattamente quanto il numero di elementi presenti attualmente
		//usare malloc e realloc 
		//oppure sovradimensionare e creare un nuovo vettore ->spreco di tempo e risorse
		Element[] currs = new Element[nElem];
		currs = Arrays.copyOf(system, nElem);
		//resize array to the size written in nElem
		return currs;
	}

// R4
	/**
	 * starts the simulation of the system
	 * 
	 * The notification about the simulations are sent
	 * to an observer object
	 * 
	 * Before starting simulation the parameters of the
	 * elements of the system must be defined
	 * 
	 * @param observer the observer receiving notifications
	 */
	public void simulate(SimulationObserver observer){
		
		int i;
		for(i=0; i<nElem; i++){
			//Non è detto che gli elementi vengano aggiunti in ORDINE
			//in altre parole non posso fare un semplice for, per ogni Source che trovo devo seguirlo fino al sink
			//come una lista doppio linkata
			if(system[i] instanceof Source){
				//se è una sorgente, devo trovare quale elemento è connesso in uscita e
				// settare l'ingresso di quell'elemento al flow in uscita dal source, e poi termino il ciclo while quando ho un sink
				//cur = system[i];
				//sostituisci le istanze SOTTOSTANTI di system[i] con current
				//while( !(cur instanceof Sink)){
				recursiveDFS(system[i],observer, "Source");

			}
		}
	}

	public void recursiveDFS(Element current, SimulationObserver observer, String type){
		//sembra errore sia dovuto perchè non uso il this ma se lo aggiungo il codice mi dà errore e non funge
		//come correggerlo?? e chiedi come mai non mi appaiono più i test SENZA aver modificato niente sul file
		//.yxm o qualcosa del genere
		//la ricorsione parte SE e SOLO se ho un source
		current.inFlow =  SimulationObserver.NO_FLOW;
		int i; 
		if( !(current instanceof Sink)){
		if(current.uscita[0] instanceof Split){
			// il valore viene settato ma poi nell'iterazione successiva viene resettato a NaN
			//come passare il 

			//metto uscita1 dentro element così questa classe lo vede ma lo tieni chiusa per ogni elemento
			//tranne che per split come se fosse un sink o un tap chiuso
			current.uscita[0].inFlow = current.outFlow;
			current.uscita[0].outFlow = current.outFlow/2;
			/*Cannot assign field "outFlow" because "current.uscita1" is null
			 * il current è un Source e alla sua uscita è connesso un Tap
			 * DOVE STA IL SPLIT??
			 */
			//SOLO per split visto che chiamo subito la funzione mi tocca settare anche il inFlow
			observer.notifyFlow(type, current.getName(), current.inFlow, current.outFlow);
			type = "Split";
			//recursiveDFS(current.uscita[0], observer, "Split");
			//recursiveDFS(current.uscita[0].uscita[1], observer, "Split");//qua devi splittare in due il depth search!!
			//come fare?? sopratutto se ho per esempio molti, MOLTI split
			//specide BFS o DFS come sugli alberi?

		} else if( current.uscita[0] instanceof Sink){
			//tap closed or is a sink -> funge come una coda di lista doppio linkata
			//arrivato a uno di questi NON vai più avanti!!
			//inflow fatto in fondo
			current.uscita[0].outFlow = SimulationObserver.NO_FLOW;
			//TERMINA controllo lista
			current.uscita[0].inFlow = current.outFlow;
			observer.notifyFlow(type, current.getName(), current.inFlow, current.outFlow);
			type = "Sink";
		}
  		else if(current.uscita[0] instanceof Tap){
			if(((Tap)(current.uscita[0])).checkOpen() == 0.0){
				current.uscita[0].outFlow = 0.0;
			}
			else {current.uscita[0].outFlow = current.outFlow;}
			current.uscita[0].inFlow = current.outFlow;
			observer.notifyFlow(type, current.getName(), current.inFlow, current.outFlow);
			type = "Tap";
			
		}
			//per attivare il metodo checkOpen devo essere un elemento di tipo Tap
			//inflow fatto in fondo
		else if (current.uscita[0] instanceof Multisplit){
			current.uscita[0].inFlow = current.outFlow;
			((Multisplit)(current.uscita[0])).getMOutFlow();
			current.uscita[0].inFlow = current.outFlow;
			observer.notifyFlow(type, current.getName(), current.inFlow, current.outFlow);
			type = "Multisplit";
		}
		//current.uscita[0].inFlow = current.outFlow; //anche se ottimizza il numero di linee di codice
		//otterrei un observer con valore inflow sbagliato -> devo spostarlo dentro ogni if
		//ho settato il flow del prossimo 
	    recursiveDFS(current.uscita[0], observer, type );
		//anyways come mai o il notify non funge o la ricorsione impazzisce
		if((current instanceof Split) || (current instanceof Multisplit) ) {
			for(i = 1; i<current.getnOut(); i++)
				recursiveDFS(current.uscita[i], observer, type );}
		}
		if(current instanceof Sink) observer.notifyFlow(type, current.getName(), current.inFlow, current.outFlow);
		//l'observer del sink, SOLO il sink non ha il suo notify mentre tutti gli altri lo hanno
		//se non metti la condizione sul sink vengono printate linee aggiuntive che non c'entrano niente
		
		//if( current.getnOut() == 2 )observer.notifyFlow(type, current.getName(), current.inFlow, current.outFlow, current.outFlow);
		//anche lo split manda una singola notifica MA con DUE uscite
		// observer.notifyFlow(type, current.getName(), current.inFlow, current.outFlow);*/
	}

// R6
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		//come capisco quando sono sotto al + dell'elemento voluto? come capisco quanti spazi mettere?
		StringBuilder drawing = new StringBuilder();
		//int vectPos[] = new int [nElem];
		for(Element i : system){
			if(i instanceof Source){
				recursivePrint(drawing, i, 0);
			}
		}
		return drawing.toString();
	}

	void recursivePrint(StringBuilder sketch, Element current, int n){
		if(current == null){
			sketch.append(" * ");
			return ;
		}
		String label = "["+current.getName()+"]";
		//if(!(current instanceof Sink)){
		String elType = current.getType();
			n = n+label.length() + elType.length();
		//}	
		sketch = sketch.append(label).append( elType);	
		if(current.getnOut() >=2){
			sketch = sketch.append(" +-> ");
			recursivePrint(sketch, current.uscita[0], n);
			
			for(int i=1; i<current.getnOut(); i++){
				sketch = sketch.append(" +-> ");
				//manca sistemare linee verticali sotto i +
				recursivePrint(sketch, current.uscita[i], n);
				//sketch = sketch.append("["+current.uscita[i].getName()+"]");
			}
				
		}
		else if(current instanceof Sink){
			sketch = sketch.append(" \n ");
			for(int j=0; j<n;j++){sketch = sketch.append(" ");}
		}
		else {
			sketch = sketch.append(" -> ");
			recursivePrint(sketch, current.uscita[0], n+" -> ".length());

		}	
		n = n-label.length();
		return;
	}

/* 	void recursivePrint(StringBuilder sketch, int n, Element current, int ... vect ){
	/*The three dots ( ... ) are used in a function's declaration as a parameter. 
	These dots allow zero to multiple arguments to be passed when the function is called. 
	The three dots are also known as var args .*/
		//ricorsione: in risalita stampo elementi successivi
		//passo due elementi? posizione ULTIMO + contando il numero di caratteri che stampo e aggiungendo spazi o stanghette finchè non arrivo alla posizione salvata
		//passo anche tutte le posizioni dei + precedenti a sto punto
		//passo: lista di posizioni dei + ; lunghezza (quanti + ci sono) e a ogni split/multisplit ricorro quando sono alla porta oltre la prima
/* 		String label = "["+current.getName()+"]";
		while(!(current instanceof Sink)){
			sketch = sketch.append(label);
			n = n+label.length();
			if(current.getnOut()==1){ // ho uscita
				sketch = sketch.append(" -> ");
				n = n+" -> ".length();
			}
			else if(current.getnOut()>=2){
				sketch = sketch.append(" +-> ");
				
				n = n+" +-> ".length();
				for(int i =0; i<current.getnOut(); i++){
					recursivePrint(sketch, n , current, vect);
					//ritorno in discesa -> arrivo fino alla fine della riga e dopo quella
					//vengono stampate tutte le soluzioni delle ricorsioni
					//ma per fare ciò devo avere il return e devo ricorrere per ogni elemento e NON più per 
					// in pratica nel for buildo in verticale: per ogni elemento faccio i suoi split
					// DOPO che sono arrivata al fondo della riga precedente
					//ricorsione: prox elemento
					//for: per n uscite split/multisplit/tubo e lo attacchi in SALITA dopo essere arrivato all'iterazione 
					//più interna
				}
			}
			else sketch = sketch.append("*");
		}
	}
	*/

// R7
	/**
	 * Deletes a previously added element 
	 * with the given name from the system
	 */
	public boolean deleteElement(String name) {
		int i = 0;
		Element curr, prev;
		while((system[i].getName() != name) ){
			i++;
			if((i==nElem)){
				return false;
				//ma dovrebbe tornare errore visto che in questo caso NON trova nemmeno il nome
			}
		}
		if(system[i].getnOut() > 1){
			return false;
		}
		else {
			curr = system[i];
			prev = system[i].entrata;
			//diminuisci numero di uscite connesse dell'elemento precedente per permettere eliminazioni successive
			//cancella dalla struttura e poi loro probabilmente runneranno di nuovo la parte per avere il grafico
			// è come cancellare un nodo doppio linkato di ASD
			prev.uscita[0] = curr.uscita[0];
			curr.uscita[0] = null;
			if(prev.uscita[0] != null) prev.uscita[0].entrata = prev;
			curr.entrata = null;
			if(prev instanceof Split && prev.getnOut() > 1){
				prev.nElu --; //se ho uno split o multisplit con più di una uscita come nodo precedente diminuisco il numero di uscite
				//basta controllare che sia split dato che il multispli è una classe derivata dello split
			}
			nElem --; //il numero di elementi totali diminuisce di uno
			//ma ora come dovrei sistemare lo sketch visto che è stato già fatto e non lo ho salvato da nessuna parte??
		}
		return true;
	}

// R8
	/**
	 * starts the simulation of the system; if {@code enableMaxFlowCheck} is {@code true},
	 * checks also the elements maximum flows against the input flow
	 * 
	 * If {@code enableMaxFlowCheck} is {@code false}  a normals simulation as
	 * the method {@link #simulate(SimulationObserver)} is performed
	 * 
	 * Before performing a checked simulation the max flows of the elements in thes
	 * system must be defined.
	 */
	public void simulate(SimulationObserver observer, boolean enableMaxFlowCheck) {
		//invocando il metodo `notifyFlowError()` dell'osservatore, 
		//passando il tipo di elemento (nome della classe), il nome dell'elemento, il flusso in ingresso e la sua portata massima.
		
		if(enableMaxFlowCheck){
			simulate(observer); 
			for(int i=0; i<nElem; i++){
				if (system[i].inFlow > system[i].maxFlow || system[i].maxFlow==-1){
					observer.notifyFlowError(system[i].getType(), system[i].name, system[i].inFlow, system[i].maxFlow);
				}
			}

		}
	}

// R9
	/**
	 * creates a new builder that can be used to create a 
	 * hydraulic system through a fluent API 
	 * 
	 * @return the builder object
	 */
    public static HBuilder build() {
		//come mettere tutti i dati che stanno nella system nel builder??
		
		// il nodo precedente è il return del nodo successivo? o qualcosa del genere
		//usa method chaining -> metodo().metodo1().metodo() 
		//struttura.fillData(system);
		//return 
		HBuilder ah = new HBuilder();
		return ah;
		//metti i valori del proportion alla simulazione e non prima 
		//seguendo l'ordine proposto
    }
}
