package diet;

/**
 * Represents a complete menu.
 * 
 * It can be made up of both packaged products and servings of given recipes.
 *
 */
public class Menu implements NutritionalElement {
	private String name;
	private static Food mat;
	private  int n =0; //stesso problema degli ingredienti
	private  Nodo head;
	private Nodo current;

	//costruttore
	public Menu(String name, Food map){
		this.name = name;
		mat = map;
	}

	/**
	 * Adds a given serving size of a recipe.
	 * The recipe is a name of a recipe defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param recipe the name of the recipe to be used as ingredient
	 * @param quantity the amount in grams of the recipe to be used
	 * @return the same Menu to allow method chaining
	 */
    public Menu addRecipe(String recipe, double quantity) {
		NutritionalElement ele = mat.getRecipe(recipe);
		if (n == 0){
			head = new Nodo(ele, quantity, n);
			//this.qt = quantity;
			this.current = head;
			n++;
			return this;
		}
		current.connect(new Nodo(ele, quantity, n));
		this.current = this.current.next; // avanzo di 1 
		n++;
		return this;
	}

	/**
	 * Adds a unit of a packaged product.
	 * The product is a name of a product defined in the {@code food}
	 * argument of the constructor.
	 * 
	 * @param product the name of the product to be used as ingredient
	 * @return the same Menu to allow method chaining
	 */
    public Menu addProduct(String product) {
		NutritionalElement ele = mat.getProduct(product);
		if (n == 0){
			head = new Nodo(ele, n);
			//this.qt = quantity;
			this.current = head;
			n++;
			return this;
		}
		current.connect(new Nodo(ele, n));
		this.current = this.current.next; // avanzo di 1 
		n++;
		return this;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Total KCal in the menu
	 */
	@Override
	public double getCalories() {
		double sol =0;
		Nodo i = head;
		//visto che abbiamo SOLO RawMaterial e quelli sono TUTTI settati con i valori nutrizionali a 100grammi
		while(i != null){
			if(i.getCurrent().per100g()) // se è una ricetta dove tutti i valori sono riferiti al 100g
				sol+=((i.getCurrent().getCalories())*(i.getQt()))/100;
			else //se è un prodotto
				sol+=(i.getCurrent().getCalories()*(i.getQt())); 
			// ho le calorie effettive di un serving di quel prodotto
			i = i.next;
		}
		return sol;
	}

	/**
	 * Total proteins in the menu
	 */
	@Override
	public double getProteins() {
		double sol =0;
		Nodo i = head;
		//visto che abbiamo SOLO RawMaterial e quelli sono TUTTI settati con i valori nutrizionali a 100grammi
		while(i != null){
			if(i.getCurrent().per100g()) // se è una ricetta dove tutti i valori sono riferiti al 100g
				sol+=((i.getCurrent().getProteins())*(i.getQt()))/100;
			else //se è un prodotto
				sol+=(i.getCurrent().getProteins()*(i.getQt())); 
			// ho le calorie effettive di un serving di quel prodotto
			i = i.next;
		}
		return sol;
	}

	/**
	 * Total carbs in the menu
	 */
	@Override
	public double getCarbs() {
		double sol =0;
		Nodo i = head;
		//visto che abbiamo SOLO RawMaterial e quelli sono TUTTI settati con i valori nutrizionali a 100grammi
		while(i != null){
			if(i.getCurrent().per100g()) // se è una ricetta dove tutti i valori sono riferiti al 100g
				sol+=((i.getCurrent().getCarbs())*(i.getQt()))/100;
			else //se è un prodotto
				sol+=(i.getCurrent().getCarbs()*(i.getQt())); 
			// ho le calorie effettive di un serving di quel prodotto
			i = i.next;
		}
		return sol;
	}

	/**
	 * Total fats in the menu
	 */
	@Override
	public double getFat() {
		double sol =0;
		Nodo i = head;
		//visto che abbiamo SOLO RawMaterial e quelli sono TUTTI settati con i valori nutrizionali a 100grammi
		while(i != null){
			if(i.getCurrent().per100g()) // se è una ricetta dove tutti i valori sono riferiti al 100g
				sol+=((i.getCurrent().getFat())*(i.getQt()))/100;
			else //se è un prodotto
				sol+=(i.getCurrent().getFat()*(i.getQt())); 
			// ho le calorie effettive di un serving di quel prodotto
			i = i.next;
		}
		return sol;
	}

	/**
	 * Indicates whether the nutritional values returned by the other methods
	 * refer to a conventional 100g quantity of nutritional element,
	 * or to a unit of element.
	 * 
	 * For the {@link Menu} class it must always return {@code false}:
	 * nutritional values are provided for the whole menu.
	 * 
	 * @return boolean indicator
	 */
	@Override
	public boolean per100g() {
		return false;
	}
}