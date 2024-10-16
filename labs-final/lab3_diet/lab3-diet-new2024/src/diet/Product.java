package diet;

public class Product implements NutritionalElement{
    protected String name;
	private double calories;
	private double proteins;
	private double carbs;
	private double fat;

     //costruttore NO: ABSTRACT CLASS NON HANNO COSTRUTTORI
     public Product(String name, double calories, double proteins, double carbs, double fat){
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fat = fat;
    }

    
    public String getName(){
        return name;
    }

    public double getCalories(){
        return calories;
    }

    public double getProteins(){
        return proteins;
    }

    public double getCarbs(){
        return carbs;
    }

    public double getFat(){
        return fat;
    }

    public boolean per100g(){
        return false;
    }
}
