package hydraulic;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method
 * {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends Element {

	/**
	 * Constructor
	 * @param name name of the tap element
	 */
	public Tap(String name) {
		super();
		this.name = name;
	}

	/**
	 * Set whether the tap is open or not. The status is used during the simulation.
	 *
	 * @param open opening status of the tap
	 */
	public void setOpen(boolean open){
		
		if(open){
			this.inFlow = this.outFlow;
		}
		else{
			this.outFlow = 0.0;
		}
	}
	
	public double checkOpen(){
		return outFlow;
	}

	@Override
	public String getType(){
		return "Tap";
	}
}
