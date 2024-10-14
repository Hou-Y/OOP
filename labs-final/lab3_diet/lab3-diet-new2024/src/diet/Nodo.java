package diet;

public class Nodo {
    private NutritionalElement cur;
	protected Nodo next;
	private double qt;
    private int index;


    public Nodo( NutritionalElement ele, double qt, int curIndex){
        this.cur = ele;
        this.qt = qt;
        this.index = curIndex;
    }

    public Nodo (NutritionalElement ele, int curIndex){
        this.cur = ele;
        this.qt = 1;
        this.index = curIndex;
    }

    public void connect(Nodo b){
        //connette e avanza di 1 nella lista
       this.next = b;
    }

    public double getQt(){
        //connette e avanza di 1 nella lista
       return qt;
    }

    public NutritionalElement getCurrent(){
        //connette e avanza di 1 nella lista
       return cur;
    }
}
