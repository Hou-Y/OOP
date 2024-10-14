package discounts;
import java.util.*;
import java.util.stream.*;

public class Discounts {
	private final Map<Integer, Utente> cards = new TreeMap<>();
	private Integer n = 1;
	private Integer pu = 1;
	private Map<String ,Integer> pul = new TreeMap<>();
	private Map<Integer, Purch> purchase = new TreeMap<>();
	private Map<Integer, Purch> pNoCard = new TreeMap<>();
	private final Map<String, String> pPerCat = new TreeMap<>();
	private final Map<String, Integer> discPerCat = new TreeMap<>();
	private final Map<String, Prod> p = new TreeMap<>();

	
	//R1
	public int issueCard(String name) {
		Utente u = new Utente(name, n);
		cards.put(n ,u);
		n++;
	    return n-1;
	}
	
    public String cardHolder(int cardN) {
		Utente u = cards.get(cardN);
        return u.getName();
    }
    

	public int nOfCards() {
		//Utente u = cards.get(cardN);
        return n-1;

	}
	
	//R2
	public void addProduct(String categoryId, String productId, double price) 
			throws DiscountsException {
				if(p.keySet().contains(productId)) throw new DiscountsException();
				Prod pp = new Prod(categoryId, productId, price);
				p.put(productId, pp);
				pPerCat.put(productId, categoryId);
	}
	
	public double getPrice(String productId) 
			throws DiscountsException {
				Prod pp = p.get(productId);
				if(pp == null) throw new DiscountsException();
        return pp.price;
	}

	public int getAveragePrice(String categoryId) throws DiscountsException {
        if(!pPerCat.values().contains(categoryId)) throw new DiscountsException();
		return (int )Math.round(p.values().stream()
		.filter(s -> s.cat.equals(categoryId))
		//.peek("cat")
		.mapToDouble(Prod::getp).average().getAsDouble())
		//.collect(Collectors.summingInt(s ->(int)s.price))
		;
	}
	
	//R3
	public void setDiscount(String categoryId, int percentage) throws DiscountsException {
		if(percentage > 50 || percentage <0 || !pPerCat.containsValue(categoryId)) throw new DiscountsException();
		discPerCat.put(categoryId, percentage);
	}

	public int getDiscount(String categoryId) {
		if(discPerCat.containsKey(categoryId))
        return discPerCat.get(categoryId);
		return 0;
	}

	//R4
	public int addPurchase(int cardId, String... items) throws DiscountsException {
        Purch p = new Purch(pu, cardId, items);
		purchase.put(pu, p);
		Utente u = cards.get(cardId);
		u.addP(p);
		pu++;
		for(String i : items) {
            String a= i.split(":")[0];
            Integer b = Integer.valueOf(i.split(":")[1]);
            if(!this.p.keySet().contains(a)){
				throw new DiscountsException();
			}
        }
		return pu-1;
	}

	public int addPurchase(String... items) throws DiscountsException {
		Purch p = new Purch(pu,  items);
		purchase.put(pu, p);
		pNoCard.put(pu, p);
		for(String i : items) {String a= i.split(":")[0];Integer b = Integer.valueOf(i.split(":")[1]);if(!this.p.keySet().contains(a)){throw new DiscountsException();}}
		pu++;
        return pu-1;
	}
	
	public double getAmount(int purchaseCode) {
		double n = 0;
		double discount  = 0;
		double tmp = 0;
		Map<String , Integer> o =purchase.get(purchaseCode).getItems();

		
			//for(<String, Integer> i : o.entrySet()){
				for(String i : o.keySet()){
					
					if(purchase.keySet().contains(purchaseCode) && !pNoCard.keySet().contains(purchaseCode)){
						String cat = pPerCat.get(i);
						if(discPerCat.get(cat)!= null)
						tmp = discPerCat.get(cat);
					
					}
					n = n+(p.get(i).getp()*o.get(i))-tmp*(p.get(i).getp()*o.get(i))/100;
					discount = discount + tmp*(p.get(i).getp()*o.get(i))/100;
					tmp = 0;
					}
				
		purchase.get(purchaseCode).discAmount(discount);

        return n;
	}
	
	public double getDiscount(int purchaseCode)  {
		getAmount(purchaseCode);
        return purchase.get(purchaseCode).disc;
	}
	
	public int getNofUnits(int purchaseCode) {
        return purchase.get(purchaseCode).tot;
	}
	
	//R5
	public SortedMap<Integer, List<String>> productIdsPerNofUnits() {
        Map<String, Integer> tmp = purchase.values().stream().filter(p -> p.getItems().size() > 0).flatMap(s ->s.getItems().entrySet().stream()).collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summingInt(Map.Entry::getValue)));
		return tmp.entrySet().stream().collect(Collectors.groupingBy(Map.Entry::getValue, //raggruppa in base al valore della mappa tmp 
		//nel nostro caso il numero totale di merce acquistata (di un certo prodotto)

								 TreeMap::new,  //mette la soluzione del gropingBy DENTRO una TreeMap (ordinata)
								 //dove ha per chiave, quella data dal parametro sopra e come valore, quella del parametro sotto

								 Collectors.mapping(Map.Entry::getKey, Collectors.toList()))); //mapping() quando non puoi usare il .
								 //per esempio se sei dentro a un groupingBy 
								 // altrimenti .map() per quando hai bisogno solo di una parte dello stream
								 //nel mio caso necessito solo della chiave e voglio una lista
	}
	
	public SortedMap<Integer, Double> totalPurchasePerCard() {
		//cards.values().stream().collect(Collectors.groupingBy(Utente::getN, TreeMap::new , Collectors.mapping( s-> (Utente)s.acq.collect(Collectors.summingDouble(getAmount(t ->(Purch)t.pid))))));
        return cards.values().stream().collect(Collectors.groupingBy(Utente::getN, TreeMap::new , Collectors.summingDouble(
			s -> s.getP().stream().map(Purch::getpid)
			//se dentro alla funzione getpid dentro la classe Purch tornavi un int invece di un Integer (wrapper)
			//TI AVREBBE DATO ERRORE!!!!!!!!
			//idk why visto che getAmount() prende un int 
			.collect(Collectors.summingDouble(p -> this.getAmount(p)))
		)));
		//return null;
		// purchase.size() - pNoCard.size();
	}
	
	public double totalPurchaseWithoutCard() {
		double sum = 0;
		for(Purch i : pNoCard.values()){
			sum = sum+getAmount(i.pid);
		}
        return sum;
	}
	
	public SortedMap<Integer, Double> totalDiscountPerCard() {
		for(Utente u : cards.values()){
			for(Purch i : u.getP()){
				getAmount(i.getpid());
			}
		}
        return cards.values().stream().collect(Collectors.groupingBy(Utente::getN, TreeMap::new , Collectors.summingDouble(
			s -> s.getP().stream().collect(Collectors.summingDouble( r -> r.disc))
			)));
	}


}
