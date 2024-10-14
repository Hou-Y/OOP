package diet;

/**
 * Represents a recipe of the diet.
 * 
 * A recipe consists of a a set of ingredients that are given amounts of raw materials.
 * The overall nutritional values of a recipe can be computed
 * on the basis of the ingredients' values and are expressed per 100g
 * 
 *
 */
public class Recipe implements NutritionalElement {
	protected final String rName;
	static private Food mat; // treemap di cibo
	//private final static int MAX = 20;
	//private static RawMaterial ingRaw[] = new RawMaterial[MAX];
	private int n =0; // n NON è condiviso! Ci potrebbero essere PIU' ricette con un numero diverso di ingredienti, lo stesso vale per head
	private  double totqt;
	private Nodo head;
	private Nodo current;
	//non so se metterlo come inner class oppure tenerlo così sfuso
	//Così devo scorrere la lista fino a trovare l'elemento (non posso usare indice)
		//private int index;
	
	//costruttore
	public Recipe(String name, Food map){
		this.rName = name;
		mat = map;
	}
	
	/**
	 * Adds the given quantity of an ingredient to the recipe.
	 * The ingredient is a raw material.
	 * 
	 * @param material the name of the raw material to be used as ingredient
	 * @param quantity the amount in grams of the raw material to be used
	 * @return the same Recipe object, it allows method chaining.
	 */
	public Recipe addIngredient(String material, double quantity) {
		//non so il numero massimo di ingredienti che una ricetta può avere, dovrei fare una lista?
		NutritionalElement ele = mat.getRawMaterial(material);
		if (n == 0){
			head = new Nodo(ele, quantity, n);
			//this.qt = quantity;
			this.current = head;
			n++; totqt = totqt+quantity;
			return this;
		}
		this.current.connect(new Nodo(ele, quantity, n));
		this.current = this.current.next; // avanzo di 1 
		n++; totqt = totqt+quantity;
		return this;
	}

	@Override
	public String getName() {
		return rName;
	}

	
	@Override
	public double getCalories() {
		/*(valore*quantity)/100 per ogni rawMaterial
		se la somma delle quantity != 100 allora scalo il valore finale a 100g*/
		double sol =0;
		Nodo i = head;
		//visto che abbiamo SOLO RawMaterial e quelli sono TUTTI settati con i valori nutrizionali a 100grammi
		while(i != null){
			sol+=((i.getCurrent().getCalories())*(i.getQt()))/100;
			i = i.next;
		}
		if(totqt != 100){ //scale to proper amount
			sol = (sol*100)/ this.totqt;
		}
		return sol;
	}
	

	@Override
	public double getProteins() {
		double sol =0;
		Nodo i = head;
		//visto che abbiamo SOLO RawMaterial e quelli sono TUTTI settati con i valori nutrizionali a 100grammi
		while(i != null){
			//come assicurarsi che prenda il getProtein() della RawMaterial? dovrebbe capire da solo il tipo
			sol+=((i.getCurrent().getProteins())*(i.getQt()))/100;
			//sol+=(((NutritionalElement)(i.getCurrent())).getProteins())*(i.getQt())/100;
			i = i.next;
		}
		if(totqt != 100){ //scale to proper amount
			sol = (sol*100)/ this.totqt;
		}
		return sol;
	}

	@Override
	public double getCarbs() {
		double sol =0;
		Nodo i = head;
		//visto che abbiamo SOLO RawMaterial e quelli sono TUTTI settati con i valori nutrizionali a 100grammi
		while(i != null){
			sol+=((i.getCurrent().getCarbs())*(i.getQt()))/100;
			i = i.next;
		}
		if(totqt != 100){ //scale to proper amount
			sol = (sol*100)/ this.totqt;
		}
		return sol;
	}

	@Override
	public double getFat() {
		double sol =0;
		Nodo i = head;
		//visto che abbiamo SOLO RawMaterial e quelli sono TUTTI settati con i valori nutrizionali a 100grammi
		while(i != null){
			sol+=((i.getCurrent().getFat())*(i.getQt()))/100;
			i = i.next;
		}
		if(totqt != 100){ //scale to proper amount
			sol = (sol*100)/ this.totqt;
		}
		return sol;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Recipe} class it must always return {@code true}:
	 * a recipe expresses nutritional values per 100g
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return true;
	}
	
}
