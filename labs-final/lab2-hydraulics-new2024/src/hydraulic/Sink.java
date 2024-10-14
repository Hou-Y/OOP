package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends Element {
	//private final double NO_FLOW = 0;
	/**
	 * Constructor
	 * @param name name of the sink element
	 */
	public Sink(String name) {
		super(); //il costruttore NON richiede il proprio nome
		this.name = name;
		
	}

	@Override
	public void connect(Element elem) {
		// in sink non deve fare niente
	}

	@Override
	public String getType(){
		return "Sink";
	}
}
