package hydraulic;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * Lo status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends Element {
	//private final double NO_FLOW = 0.0;

	/**
	 * constructor
	 * @param name name of the source element
	 */
	public Source(String name) {
		super();
		this.name = name;
	}

	@Override
	public String getType(){
		return "Source";
	}

	@Override
	public void setMaxFlow(double maxFlow) {
		// does nothing by default
		
	}

	/**
	 * Define the flow of the source to be used during the simulation
	 *
	 * @param flow flow of the source (in cubic meters per hour)
	 */
	public void setFlow(double flow){
		//dove sta l'interfaccia (btw da come lo scrivno sembra che sia stata già definita da loro ma sembra non sia così)
		//this.inFlow = NO_FLOW;
		this.outFlow = flow;
		// TODO: to be implemented
	}

}
