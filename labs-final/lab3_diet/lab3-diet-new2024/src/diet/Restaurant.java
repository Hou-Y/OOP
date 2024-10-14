package diet;

import diet.Order.OrderStatus;

/**
 * Represents a restaurant class with given opening times and a set of menus.
 */
public class Restaurant {
	protected String rname;
	private static final int MAX = 24+1;
	//nel caso il ristorante chiuda a mezzanotte (ma lo scrive come 24:00)
	//nel caso apra a mezzanotte (e viene scritto com 00:00)

	private Hours timetable[] = new Hours[MAX];
	private Menu menuR;
	//numero totale di orari dev'essere PARI
	//apertura chiusura apertura chiusura
	

	public Restaurant(String name){
		this.rname = name;
	}
	
	/**
	 * retrieves the name of the restaurant.
	 *
	 * @return name of the restaurant
	 */
	public String getName() {
		return rname;
	}

	/**
	 * Define opening times.
	 * Accepts an array of strings (even number of elements) in the format {@code "HH:MM"},
	 * so that the closing hours follow the opening hours
	 * (e.g., for a restaurant opened from 8:15 until 14:00 and from 19:00 until 00:00,
	 * arguments would be {@code "08:15", "14:00", "19:00", "00:00"}).
	 *
	 * @param hm sequence of opening and closing times
	 */
	public void setHours(String ... hm) {
		int ore, oreA =0, min;
		boolean even = true; //se è pari è l'ora di apertura
		//se è dispari è l'ora di chiusura
		for(int i=0; i<MAX; i++){
			timetable[i] = new Hours();
		}
		for(String i : hm){
			//int indexprova = i.lastIndexOf(":") +1;
			//String substrinprova = i.substring(indexprova);
			min = Integer.parseInt(i.substring(i.lastIndexOf(":") +1));
			//indexprova = i.lastIndexOf(":") -1;
			//String oraprova = i.substring(0, i.lastIndexOf(":"));
			ore = Integer.parseInt(i.substring(0, i.lastIndexOf(":")));
			if(even){
				oreA=ore;
				timetable[ore].setApertura();
			} 
			else
			timetable[ore].setChiusura();
			timetable[ore].setMin(min);
			
			//devo cambiare gli elementi intermedi?
			/* cambio tutti i successivi a true o false a seconda sia apertura o chiusura? Bruttissimo e poco efficiente
			 * ripetizioni inutili, trovare un metodo migliore senza modificare la struttura dati */
			if(!even){
				for(int ele=oreA; ele<=ore ; ele++){
				timetable[ele].setHour(true);
				//timetable[ele].setHour(even);
				}
			}	
			//prep per la prossima iterazione
			if(even)
				even = false;
			else
				even = true;
		}
	}

	/**
	 * Checks whether the restaurant is open at the given time.
	 *
	 * @param time time to check
	 * @return {@code true} is the restaurant is open at that time
	 */
	public boolean isOpenAt(String time){
		int min, ore;
		Hours check = new Hours();
		min = Integer.parseInt(time.substring(time.lastIndexOf(":") +1));
		ore = Integer.parseInt(time.substring(0, time.lastIndexOf(":")));
		check.setHour(true);
		check.setMin(min);
		if( timetable[ore].HourOpen()) //se nella lista di bool potrebbe essere aperto
		{
			if(timetable[ore].getcheckIns() == 0 ) return true;
			else if(timetable[ore].getcheckIns() == 1){
				if(timetable[ore].compareTo(check) == 1){ //se il valore in minuti da controllare è minore di quello di chiusura, significa che
					//il ristorante è aperto
					return true;
				};
				//rivedere il compare to
			}
			else if( timetable[ore].getcheckIns() == -1){ // se il valore in min da controllare è maggiore di quello di apertura
				//significa che il ristorante è aperto
				if(timetable[ore].compareTo(check) == -1){
					return true;
				};
			}
			
		}
		return false;
	}

	/**
	 * Adds a menu to the list of menus offered by the restaurant
	 *
	 * @param menu	the menu
	 */
	public void addMenu(Menu menu) {
		this.menuR = menu;
	}

	/**
	 * Gets the restaurant menu with the given name
	 *
	 * @param name	name of the required menu
	 * @return menu with the given name
	 */
	public Menu getMenu(String name) {
		return menuR;
	}

	/**
	 * Retrieve all order with a given status with all the relative details in text format.
	 *
	 * @param status the status to be matched
	 * @return textual representation of orders
	 */
	public String ordersWithStatus(OrderStatus status) {
		return null;
	}
}
