package mountainhuts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

//import org.w3c.dom.ranges.Range;

import static java.util.stream.Collectors.*;
import java.util.Collections.*;

/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */
public class Region {
	private String name;
	private final List<Ranges> allrange;
	private final Set<Municipality> mSet;
	private final Set<MountainHut> huts;
	private final Map<String, MountainHut> fucker = new TreeMap<>();
	//NON usare Range che è un'interfaccia! (abstract)

	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		this.name = name;
		allrange = new LinkedList<>();
		mSet = new LinkedHashSet<>();
		huts = new LinkedHashSet<>();
	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return name;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		for(String i: ranges){
			allrange.add(new Ranges(i));
		}
		//arrayList ogni volta che aggiungi un elemento nasconde una COPIA 
		//Collections.sort(Comparator.comparing(allrange::allrange.getStart()));
	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		//Integer unboxed to int?
		/*for(Ranges i: allrange){
			if(i.getStart()<=altitude && i.getEnd()>=altitude){ return i.getStart()+"-"+i.getEnd();}
		}
		return "0-INF";*/
		return allrange.stream()
				.filter( r -> r.contains(altitude) )
				.findFirst()
				.orElse(Ranges.DEFAULT).toString();

	}

	/**
	 * Return all the municipalities available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return Collections.unmodifiableCollection(mSet);
		//return mSet.getUn();
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return Collections.unmodifiableCollection(huts);
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		//come farlo con gli stream
		//RIFARE CON STREAM!!
		Municipality m = new Municipality(name, province, altitude);
		/*if( ! (mSet.contains(m))){
			mSet.add(m);
		}
		return m;*/
		/*return mSet.stream()
			.filter( e -> e.equals(m))
			.orElse(mSet.add(m));*/

		for(Municipality i : mSet){
			if(i.getName().equals(m.getName())) return i;}
			// equals torna TRUE se TUTTI i caratteri sono uguali
		mSet.add(m);
		return m;
		//equals per object
		//this method returns true if and only if x and y refer to the same object ( x == y has the value true ).
		//nel caso degli object controlla il tag -> devono essere uguali identici -> devo usare qualcosa di diverso per controllare se è lo stesso
		//NON gli va bene un altro oggetto con gli stessi contenuti, vuole proprio lo stesso ()
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name,String category, Integer bedsNumber,
			Municipality municipality) {
				/*return mountainHuts.stream().filter( x -> x.getName().equals(name)).findFirst().orElseGet(() -> {
					MountainHut h = new MountainHut(name, altitude, category, bedsNumber, municipality);
					mountainHuts.add(h);
					return h;*/
		
				return huts.stream()
					.filter( e -> e.getName().equals(name)).findFirst().orElseGet( ()-> {
						MountainHut h = new MountainHut(name, null, category, bedsNumber, municipality);
						huts.add(h);
						fucker.put(name, h);
						return h;});
				/*MountainHut m = new MountainHut(name, null, category, bedsNumber, municipality);
				if( ! (huts.contains(m))){
					huts.add(m);
				}
				return m;*/
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber,
			Municipality municipality) {
				return huts.stream()
				.filter( e -> e.getName().equals(name)).findFirst().orElseGet( ()-> {
					MountainHut h = new MountainHut(name, altitude, category, bedsNumber, municipality);
					huts.add(h);
					fucker.put(name, h);
					return h;});
	}

	/**
	 * Creates a new region and loads its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		Region r = new Region(name);
		ArrayList<String> d = new ArrayList<>(r.readData(file)); //così mi errore -> devo averlo statico 
		ListIterator<String> it = d.listIterator(1);
        while (it.hasNext()) {
			//dal secondo elemento aggiungo dati effettivi
            String item = it.next();
            String [] s = item.split(";");
			//gli array in java partono da indice 1 !!
			Municipality mu = r.createOrGetMunicipality(s[1], s[0], Integer.valueOf(s[2]));
			if(s[4] == "") //al 4° posto a partire da 0 -> altitude
				//name [altitude] category bed municipality
				{//MountainHut hu = r.createOrGetMountainHut(s[3], s[5], Integer.valueOf(s[6]), mu );
				r.createOrGetMountainHut(s[3], s[5], Integer.valueOf(s[6]), mu );}
			else{
				r.createOrGetMountainHut(s[3], Integer.valueOf(s[4]), s[5], Integer.valueOf(s[6]), mu );
				//MountainHut hu = r.createOrGetMountainHut(s[3], Integer.valueOf(s[4]), s[5], Integer.valueOf(s[6]), mu);
				//perchè versione 2 mi dà errore??
			}
			//
		}
		return r;
	}

	/**
	 * Reads the lines of a text file.
	 *
	 * @param file path of the file
	 * @return a list with one element per line
	 */
	public static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {
		return mSet.stream()
		.collect(groupingBy(Municipality::getProvince, counting()));
		// assume each municipality has at least a hut

		//altrimenti
		/*
		 * return mountainHuts.stream()
			.map(MountainHut::getMunicipality)
			.distinct()
			.collect(groupingBy(Municipality::getProvince, // classifie
									 counting()))	// downstream
		 */
		//come chiave il nome di una provincia e come valore il numero dei comuni in tale provincia
	}

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		//provincia è solo in Municipality 
		// counting() per rifugio, necessito della lista dei MountainHuts
		return huts.stream()
		.collect(groupingBy(e -> e.getMunicipality().getProvince(), 
				groupingBy( f -> f.getMunicipality().getName(), counting() )));
	
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		//mi serve allrange per avere tutti i range e necessito di huts, come includere huts
		Map<String,Long> res = huts.stream().map(h -> h.getAltitude().orElse(h.getMunicipality().getAltitude()))
				.map(this::getAltitudeRange)
				.collect(groupingBy(r->r, 			// key mapper
						            counting()));	// downstream
		// adds also altitude ranges with no mountain huts
		allrange.stream().map(Ranges::toString).forEach(r -> res.putIfAbsent(r, 0L));
		return res;

		//invece di usare allrange.stream() dalla quale riesci ad ottenere SOLO dei range di altezze
		/*Map <String, Long> res =  huts.stream()
		.map(this::getAltitudeRange)
		.collect(groupingBy( r-> r, counting()));
		allrange.stream().map(Ranges::toString).forEach(r -> res.putIfAbsent(r, 0L));
		return res;*/
		//ma non avresti altre info, potresti poi aggiungerlo successivamente, invece così torna dei range di valore
		//.collect(groupingBy(Region::getAltitudeRanges, 
							 // huts.forEach( (String)s -> s.getAltitudeRange()).counting()));
							 //huts.stream().map( Region::getAltitudeRange).counting()));
		/*return getAltitudeRanges().stream()
							 .collect(toMap(
								 range -> range,
								 range -> huts.values().stream()
									 .filter(h -> getAltitudeRange(h.getAltitudeStream()).equals(range))
									 .map(MountainHut::getBedsNumber)
									 .reduce(Integer::max)
							 ));*/
					 
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		return huts.stream()
			.collect(groupingBy( r-> r.getMunicipality().getProvince(), 
									//summingInt(MountainHut::getBedsNumber))
									reducing(0, MountainHut::getBedsNumber, (a,b) -> a+b))
					);
		/* reducing lo posso usare DENTRO groupingBy
		 * reducing(0, MountainHut::getBedsNumber, (a,b) -> a+b)
		 *  0 inidica il valore iniziale
		 * MountainHut::getBedsNumber indica il valore che viene preso per fare l'operazione
		 * +   indica che nel nostro caso è una somma
		 * reducing(valoreIniziale, valoreChePrendoOgniVolta come a b, che operazione faccio)
		 * nel nostro caso (a,b) -> a+b    li sommo
		 */
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		
		
		/*return allrange.stream()
								.collect(toMap( Ranges::toString , range -> fucker.values().stream()
								//.peek(s -> System.out.println("lnlknlk: " + s))
								.filter
										(h -> {
											String rangee = getAltitudeRange(h.getAltitude().orElse(h.getMunicipality().getAltitude()));
											//System.out.println("Comparing "+ rangee +" with "+ range);
											return rangee.equals(range);
										})
									
								//.map(MountainHut::getBedsNumber)
								.peek(s -> System.out.println("Stream element: " + s))
								.map(s ->{
									
									System.out.println("Current bed number "+ s.getBedsNumber() );
									Integer nb = s.getBedsNumber();
									System.out.println("Current bed number "+ nb );
									return nb;
								})
								.peek(nb -> System.out.println("Mapped element: " + nb))
								.reduce(Integer::max)
								));*/





		//numero massimo di letti DI UN SINGOLO HUT (in un dato range di altezze)
		// per ogni altezza -> controllo in che range è -> di quel range compare il numero di letti con il massimo attuale
		// si potrebbe fare anche senza stream (credo)
		/*return huts.stream()
				.findAny(Ranges.contains())*/
		
		//Optional.ofNullable(max);
	
			/*return allrange.stream()
									.collect(toMap( Ranges::toString , range -> fucker.values().stream()
									.filter(h -> {
												String rangee = getAltitudeRange(h.getAltitude().orElse(h.getMunicipality().getAltitude()));
												return rangee.equals(range);
											})
									.peek(s -> System.out.println("Stream element: " + s))
									.map(s ->{
										System.out.println("Current bed number "+ s.getBedsNumber() ); //nel debug NON VA DENTRO al .map, come se dopo .filter uscisse SOLO VUOTO
										Integer nb = s.getBedsNumber();
										System.out.println("Current bed number "+ nb );
										return nb;
									})
									.peek(nb -> System.out.println("Mapped element: " + nb))
									.reduce(Integer::max)
									));*/

									Map<String, Optional<Integer>> res= huts.stream()
									.collect(groupingBy(this::getAltitudeRange,
														mapping(MountainHut::getBedsNumber, 
																maxBy(Comparator.naturalOrder()))
																)
											);
							// adds also altitude ranges with no mountain huts
							allrange.stream().map(Ranges::toString).forEach(r -> res.putIfAbsent(r, Optional.of(0)));
							return res;
					






		/*return allrange.stream().map(Ranges::toString).toList().stream()
				.collect(toMap( r -> r ,
						r -> huts.stream().filter(
							(h -> h.getAltitude().orElse(
								h.getMunicipality().getAltitude()).equals(r)) )
								.map(MountainHut::getBedsNumber)));*/


								/*return allrange.stream()
								.collect(toMap( Ranges::toString , range -> huts.stream()
								.filter(h -> h.getAltitude().orElse(h.getMunicipality().getAltitude()).equals(range))
								.map(MountainHut::getBedsNumber)
								.reduce(Integer::max)
								));*/

								/*huts.stream().filter(
									(h -> h.getAltitude().orElse(
										h.getMunicipality().getAltitude()).equals()) )
										.map(MountainHut::getBedsNumber)
										));*/
	
				
}

private String getAltitudeRange(MountainHut mh) {  //NAH I'D LOSE (DIES)
	return getAltitudeRange(mh.getAltitude()
							 .orElseGet( mh.getMunicipality()::getAltitude ) );
}



	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		Map<String,Long> nums = huts.stream().collect(groupingBy(e -> e.getMunicipality().getName(), counting()));
		
		/*per ogni NUMERO DI huts presenti in un certo territorio (Municipality) fai la lista dei nomi dei comuni con quel numero di huts */
		return nums.keySet().stream().sorted().collect(groupingBy(s -> nums.get(s)));
		//return null;
	}

}
