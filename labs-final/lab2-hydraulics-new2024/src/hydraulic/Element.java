package hydraulic;


/**
 * Represents the generic abstract element of an hydraulics system.
 * It is the base class for all elements.
 *
 * Any element can be connect to a downstream element
 * using the method {@link #connect(Element) connect()}.
 * 
 * The class is abstract since it is not intended to be instantiated,
 * though all methods are defined to make subclass implementation easier.
 */
public abstract class Element {
	protected String name;
	protected Element entrata;
	protected double inFlow;
	protected double outFlow;
	//protected double []outFlowM;
	protected Element []uscita;
	protected int nElu = 1;
	protected double maxFlow = -1;
	protected int uscitaCorrente = 0;
	//protected Element uscita1;

	//costruttore
	public Element(){
		this.uscita = new Element[1];
	}

	public Element(String name, int noEl){
		this.uscita = new Element[noEl];
		//this.outFlowM = new double [noEl];
		this.nElu = noEl;
	}
	/**
	 * getter method for the name of the element
	 * 
	 * @return the name of the element
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Connects this element to a given element.
	 * The given element will be connected downstream of this element
	 * 
	 * In case of element with multiple outputs this method operates on the first one,
	 * it is equivalent to calling {@code connect(elem,0)}. 
	 * 
	 * @param elem the element that will be placed downstream
	 */
	public void connect(Element elem) {
		// does nothing by default
		this.uscita[0] = elem;
		elem.entrata = this;

	}
	
	/**
	 * Connects a specific output of this element to a given element.
	 * The given element will be connected downstream of this element
	 * 
	 * @param elem the element that will be placed downstream
	 * @param index the output index that will be used for the connection
	 */
	public void connect(Element elem, int index){
			this.uscita[index] = elem;
			elem.entrata = this;
		}// 0 per prima uscita, 1 per seconda uscita
		
		
	/**
	 * Retrieves the single element connected downstream of this element
	 * 
	 * @return downstream element
	 */
	public Element getOutput(){
		return this.uscita[0];
		
		// è possibile sapere a quale altro elemento è connessa la sua uscita
	}

	public int getnOut(){
		return nElu;
	}

	public String getType(){
		return "";
	}

	/**
	 * Retrieves the elements connected downstream of this element
	 * 
	 * @return downstream element
	 */
	public Element[] getOutputs(){
		return uscita;
	}
	
	/**
	 * Defines the maximum input flow acceptable for this element
	 * 
	 * @param maxFlow maximum allowed input flow
	 */
	public void setMaxFlow(double maxFlow) {
		// does nothing by default
		this.maxFlow = maxFlow;
	}

	public double getProp(int i){
		return 0.0;
	}
}
