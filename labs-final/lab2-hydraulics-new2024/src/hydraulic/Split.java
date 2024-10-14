package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Element {
	//protected Element uscita1;
	//lo split HA DUE USCITE
	

	/**
	 * Constructor
	 * @param name name of the split element
	 */
	public Split(String name) {
		super(name, 2);
		this.name = name;
	}
	public Split(String name, int noEl) {
		super(name, noEl);
		this.name = name;
	}

	@Override
	public String getType(){
		return "Split";
	}

	/*@Override
	public void connect(Element elem, int index){
		// does nothing by default
		// 0 per prima uscita, 1 per seconda uscita
		if(index == 0){
			this.uscita[0] = elem;
			elem.entrata = this;
		}
		else {
			this.uscita[1] = elem;
			elem.entrata = this;
		}

	}*/

	@Override
	public Element[] getOutputs(){
		//Element[] uscite = {uscita, uscita1};
		return uscita;
	}


}
