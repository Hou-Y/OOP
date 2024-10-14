package delivery;

import java.util.List;
import java.util.Map;

import java.util.*;
import java.util.stream.*;


public class Delivery {
	Map<String, Category> cat = new TreeMap<>();
	Map<String, String> restoPerCat = new TreeMap<>();
	Map<String, Restaurant> restos = new TreeMap<>();
	List<String> cats = new LinkedList<>();
	Map<String, Dish> alldish = new TreeMap<>();
	Map<String, Order> restoOrder = new TreeMap<>();
	Map<Integer, Order> allorder = new HashMap<>();
	private Integer n = 0;
	// R1
	
    /**
     * adds one category to the list of categories managed by the service.
     * 
     * @param category name of the category
     * @throws DeliveryException if the category is already available.
     */
	public void addCategory (String category) throws DeliveryException {
		if(cats.contains(category) ) throw new DeliveryException();
		cats.add(category);
	}
	
	/**
	 * retrieves the list of defined categories.
	 * 
	 * @return list of category names
	 */
	public List<String> getCategories() {
		return cats;
	}
	
	/**
	 * register a new restaurant to the service with a related category
	 * 
	 * @param name     name of the restaurant
	 * @param category category of the restaurant
	 * @throws DeliveryException if the category is not defined.
	 */
	public void addRestaurant (String name, String category) throws DeliveryException {
	if(!cats.contains(category)) throw new DeliveryException();
		restoPerCat.put(name, category);
	Restaurant r = new Restaurant(name, category);
	restos.put(name, r);
	}
	
	/**
	 * retrieves an ordered list by name of the restaurants of a given category. 
	 * It returns an empty list in there are no restaurants in the selected category 
	 * or the category does not exist.
	 * 
	 * @param category name of the category
	 * @return sorted list of restaurant names
	 */
	public List<String> getRestaurantsForCategory(String category) {
        return restoPerCat.entrySet().stream()
		.filter( r-> r.getValue().equals(category))
		.map(Map.Entry::getKey)
		.collect(Collectors.toList());
	}
	
	// R2
	
	/**
	 * adds a dish to the list of dishes of a restaurant. 
	 * Every dish has a given price.
	 * 
	 * @param name             name of the dish
	 * @param restaurantName   name of the restaurant
	 * @param price            price of the dish
	 * @throws DeliveryException if the dish name already exists
	 */
	public void addDish(String name, String restaurantName, float price) throws DeliveryException {
	Dish d = new Dish(name, price);
	alldish.put(name, d);
	Restaurant r = restos.get(restaurantName);
	if(r.getDish().contains(d)) throw new DeliveryException();
	//if(r.getDish().contains(d)) throw new DeliveryException();
	for(Dish i: r.getDish()){
		if(i.dname.equals(name)) throw new DeliveryException();
	}
	r.addD(d);
	}
	
	/**
	 * returns a map associating the name of each restaurant with the 
	 * list of dish names whose price is in the provided range of price (limits included). 
	 * If the restaurant has no dishes in the range, it does not appear in the map.
	 * 
	 * @param minPrice  minimum price (included)
	 * @param maxPrice  maximum price (included)
	 * @return map restaurant -> dishes
	 */
	public Map<String,List<String>> getDishesByPrice(float minPrice, float maxPrice) {
        Map<String,List<String>> sol = new TreeMap<>();
		List<String > s =  alldish.entrySet().stream()
		.filter( p ->p.getValue().dprice >= minPrice && p.getValue().dprice<= maxPrice)
		.map( Map.Entry::getKey
			
		)//.map(Dish::getDN);
		.collect(Collectors.toList());
		/*return restos.keySet().stream()
		.collect(Collectors.toMap(
			r ->r,
			s
		))
		;*/
		/*for(Restaurant i : restos.values()){
			if(i.get)
		}
		return sol;*/
		return restos.values().stream()
		.filter( k -> k.inrange(minPrice, maxPrice))
		.collect(Collectors.toMap(
				Restaurant::getnam,
				 o -> o.getDish().stream()
				 .filter(m -> m.dprice >= minPrice && m.dprice <= maxPrice)
				 .map(Dish::getDN)
				 .collect(Collectors.toList())
		));
	}
	
	/**
	 * retrieve the ordered list of the names of dishes sold by a restaurant. 
	 * If the restaurant does not exist or does not sell any dishes 
	 * the method must return an empty list.
	 *  
	 * @param restaurantName   name of the restaurant
	 * @return alphabetically sorted list of dish names 
	 */
	public List<String> getDishesForRestaurant(String restaurantName) {
		if(restos.get(restaurantName) == null) return new ArrayList<>();
        return restos.get(restaurantName)
		.getDish()
		.stream()
		.map(Dish::getDN)
		.collect(Collectors.toList());
	}
	
	/**
	 * retrieves the list of all dishes sold by all restaurants belonging to the given category. 
	 * If the category is not defined or there are no dishes in the category 
	 * the method must return and empty list.
	 *  
	 * @param category     the category
	 * @return 
	 */
	public List<String> getDishesByCategory(String category) {
        return restos.values().stream()
		.filter(s -> s.getC().equals(category))
		.flatMap( o ->o.getDish().stream())
		.map(Dish::getDN)
		.collect(Collectors.toList());
	}
	
	//R3
	
	/**
	 * creates a delivery order. 
	 * Each order may contain more than one product with the related quantity. 
	 * The delivery time is indicated with a number in the range 8 to 23. 
	 * The delivery distance is expressed in kilometers. 
	 * Each order is assigned a progressive order ID, the first order has number 1.
	 * 
	 * @param dishNames        names of the dishes
	 * @param quantities       relative quantity of dishes
	 * @param customerName     name of the customer
	 * @param restaurantName   name of the restaurant
	 * @param deliveryTime     time of delivery (8-23)
	 * @param deliveryDistance distance of delivery
	 * 
	 * @return order ID
	 */
	public int addOrder(String dishNames[], int quantities[], String customerName, String restaurantName, int deliveryTime, int deliveryDistance) {
	   	n++;
		Restaurant r = restos.get(restaurantName);
		Order o = new Order(r, n, dishNames, quantities, customerName, restaurantName, deliveryTime, deliveryDistance);
		restoOrder.put(restaurantName, o);
		allorder.put(n, o);
	   return n;
	}
	
	/**
	 * retrieves the IDs of the orders that satisfy the given constraints.
	 * Only the  first {@code maxOrders} (according to the orders arrival time) are returned
	 * they must be scheduled to be delivered at {@code deliveryTime} 
	 * whose {@code deliveryDistance} is lower or equal that {@code maxDistance}. 
	 * Once returned by the method the orders must be marked as assigned 
	 * so that they will not be considered if the method is called again. 
	 * The method returns an empty list if there are no orders (not yet assigned) 
	 * that meet the requirements.
	 * 
	 * @param deliveryTime required time of delivery 
	 * @param maxDistance  maximum delivery distance
	 * @param maxOrders    maximum number of orders to retrieve
	 * @return list of order IDs
	 */
	public List<Integer> scheduleDelivery(int deliveryTime, int maxDistance, int maxOrders) {
		//for()
		int w = 0;
		List<Integer> o = new LinkedList<>();
		for(Order i : allorder.values() ){
			if(i.delTime <= deliveryTime && i.delD <= maxDistance && w<maxOrders){ w++;
				o.add(i.getid());
				i.pending = false;
			}
		}

        return o;
	}
	
	/**
	 * retrieves the number of orders that still need to be assigned
	 * @return the unassigned orders count
	 */
	public int getPendingOrders() {
        long sol =  allorder.values().stream()
		.filter( r-> r.pending)
		.count()
		//.Integer.valueOf(s -> s)
		//.collect(Collectors.summingInt(
			//count()
			//)
			//)
		;
		return (int) sol;
	}
	
	// R4
	/**
	 * records a rating (a number between 0 and 5) of a restaurant.
	 * Ratings outside the valid range are discarded.
	 * 
	 * @param restaurantName   name of the restaurant
	 * @param rating           rating
	 */
	public void setRatingForRestaurant(String restaurantName, int rating) {
		if(rating > 0 || rating <=5)
			restos.get(restaurantName).addRatin(rating);
	}
	
	/**
	 * retrieves the ordered list of restaurant. 
	 * 
	 * The restaurant must be ordered by decreasing average rating. 
	 * The average rating of a restaurant is the sum of all rating divided by the number of ratings.
	 * 
	 * @return ordered list of restaurant names
	 */
	public List<String> restaurantsAverageRating() {
        //return restos.values().stream()
		//.collect(summingInt(s ->s.getRatin()))

		//.forEach( 
		//	s ->s.getRatin().
		//);
		return restos.values().stream()
		.filter( r -> r.getratin().size() > 0)
		.sorted( Comparator.comparing(Restaurant::getavg)
		.reversed()
		)
		//
		.map(Restaurant::getnam)
		.collect(Collectors.toList())
		;
	}
	
	//R5
	/**
	 * returns a map associating each category to the number of orders placed to any restaurant in that category. 
	 * Also categories whose restaurants have not received any order must be included in the result.
	 * 
	 * @return map category -> order count
	 */
	public Map<String,Long> ordersPerCategory() {
        return allorder.values().stream()
		.collect(Collectors.groupingBy(
			Order::getCAT,
			Collectors.counting()
		))
		;
	
	}
	
	/**
	 * retrieves the name of the restaurant that has received the higher average rating.
	 * 
	 * @return restaurant name
	 */
	public String bestRestaurant() {
        return restaurantsAverageRating().stream().findFirst().orElse("");
	}
}
