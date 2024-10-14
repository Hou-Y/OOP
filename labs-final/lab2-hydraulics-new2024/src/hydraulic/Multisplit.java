package hydraulic;

/**
 * Represents a multisplit element, an extension of the Split that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends Split {
	private double []propor;
	private double []outFlowM;

	/**
	 * Constructor
	 * @param name the name of the multi-split element
	 * @param numOutput the number of outputs
	 */
	public Multisplit(String name, int numOutput) {
		super(name, numOutput);
		this.nElu = numOutput;
		this.outFlowM = new double [numOutput];
		this.propor = new double [numOutput];
		//this.outMsplit = new double[nEl];
	}
	
	/**
	 * Define the proportion of the output flows w.r.t. the input flow.
	 * 
	 * The sum of the proportions should be 1.0 and 
	 * the number of proportions should be equals to the number of outputs.
	 * Otherwise a check would detect an error.
	 * 
	 * @param proportions the proportions of flow for each output
	 */
	public void setProportions(double... proportions) {
		int i;
		for(i=0; i<this.getnOut(); i++){
			//outFlowM[i]=proportions[i]*inFlow; -> questo lo fai nella simulazione
			this.propor[i] = proportions[i];
		}
		
	}
	
	public void getMOutFlow(){
		for(int i=0; i<this.getnOut(); i++)
			this.outFlowM[i]=propor[i]*inFlow;
		//return outFlowM;
	}

	@Override
	public String getType(){
		return "Multisplit";
	}

	@Override
	public double getProp(int i){
		return propor[i];
	}
	
}
