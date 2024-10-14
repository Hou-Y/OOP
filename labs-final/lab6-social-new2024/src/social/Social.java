package social;

import java.util.*;
import java.util.stream.*;


public class Social {
	Map <String, Person> pl = new TreeMap<>();
	Map <Person, Person> friend = new HashMap<>();
	Map <String, Group> groups = new HashMap<>();
	Map <Person, Post> pul = new HashMap<>();



	/**
	 * Creates a new account for a person
	 * 
	 * @param code	nickname of the account
	 * @param name	first name
	 * @param surname last name
	 * @throws PersonExistsException in case of duplicate code
	 */
	public void addPerson(String code, String name, String surname)
			throws PersonExistsException {
				Person p = new Person(code, name, surname);
				if(pl.keySet().contains(code)) throw new PersonExistsException();
				else{
					pl.put(code, p);
				}


	}

	/**
	 * Retrieves information about the person given their account code.
	 * The info consists in name and surname of the person, in order, separated by blanks.
	 * 
	 * @param code account code
	 * @return the information of the person
	 * @throws NoSuchCodeException
	 */
	public String getPerson(String code) throws NoSuchCodeException {
		Person p = pl.get(code);
		if(p == null) throw new NoSuchCodeException();
		return p.getid()+" "+p.getname()+" "+p.getSurn();
	}

	/**
	 * Define a friendship relationship between to persons given their codes.
	 * 
	 * Friendship is bidirectional: if person A is friend of a person B, that means that person B is a friend of a person A.
	 * 
	 * @param codePerson1	first person code
	 * @param codePerson2	second person code
	 * @throws NoSuchCodeException in case either code does not exist
	 */
	public void addFriendship(String codePerson1, String codePerson2)
			throws NoSuchCodeException {
				Person p = pl.get(codePerson1);
				Person l = pl.get(codePerson2);
				if(p == null || l == null) throw new NoSuchCodeException();
				friend.put(p,l);
				p.addf(l.getid());
				l.addf(p.getid());

	}

	/**
	 * Retrieve the collection of their friends given the code of a person.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return the list of person codes
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> listOfFriends(String codePerson)
			throws NoSuchCodeException {
				Person p = pl.get(codePerson);
				if(p == null ) throw new NoSuchCodeException();
				
		return p.getFriend();
	}

	/**
	 * Retrieves the collection of the code of the friends of the friends
	 * of the person whose code is given, i.e. friends of the second level.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return collections of codes of second level friends
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> friendsOfFriends(String codePerson)
			throws NoSuchCodeException {
				List <String> sol = new LinkedList<>();
				Person p = pl.get(codePerson);
				if(p == null) throw new NoSuchCodeException();
				List<String> l = p.getFriend();
				for(String i : l){
					Person k = pl.get(i);
					//sol.add(k.getFriend().toString());
					for(String w : k.getFriend()){
						if( !w.equals(codePerson)){
							sol.add(pl.get(w).getid());
						}
					}
				}
		//return sol;
		return p.getFriend().stream() //ho uno stream di stringhe formato dall'ID di ogni amico di p
		.map(pl::get) //da ogni String ID prendo l'oggetto Person dalla lista di persone
		//ho bisogno dell'oggetto friend per poter chiamare getFriend
		.map(Person::getFriend)  //ma getFriend ritorna una LISTA di String
		//perchè qua diventa una Lista di liste invece di diventare uno stream di liste?
		
		.flatMap(Collection::stream) //Collection SENZA s!! (NON Collections)
		// un lista di liste NON è in grado di diventare una Collection
		//ma una lista di Oggetti sì, nel nostro caso una lista di
		.filter(s -> !s.equals(codePerson))
		.collect(Collectors.toList()); //List accetta duplicati
		//se NON li vuoi trasformalo in un toSet()


		/*.distinct()
		 * .collect(Collectors.toList())
		 * 
		 * questa coppia crea due collezioni in totale, è più lenta del trasformarlo direttamente in un set
		 */
	}

	/**
	 * Retrieves the collection of the code of the friends of the friends
	 * of the person whose code is given, i.e. friends of the second level.
	 * The result has no duplicates.
	 * 
	 * 
	 * @param codePerson code of the person
	 * @return collections of codes of second level friends
	 * @throws NoSuchCodeException in case the code does not exist
	 */
	public Collection<String> friendsOfFriendsNoRepetition(String codePerson)
			throws NoSuchCodeException {
				Set <String> sol = new HashSet<>();
				Person p = pl.get(codePerson);
				if(p == null) throw new NoSuchCodeException();
				List<String> l = p.getFriend();
				for(String i : l){
					Person k = pl.get(i);
					//sol.add(k.getFriend().toString());
					for(String w : k.getFriend()){
						if( !w.equals(codePerson)){
							sol.add(pl.get(w).getid());
						}
					}
				}
		return sol;
	}

	/**
	 * Creates a new group with the given name
	 * 
	 * @param groupName name of the group
	 */
	public void addGroup(String groupName) {
		Group g = new Group(groupName);
		groups.put(groupName, g);

	}

	/**
	 * Retrieves the list of groups.
	 * 
	 * @return the collection of group names
	 */
	public Collection<String> listOfGroups() {
		return groups.keySet();
	}

	/**
	 * Add a person to a group
	 * 
	 * @param codePerson person code
	 * @param groupName  name of the group
	 * @throws NoSuchCodeException in case the code or group name do not exist
	 */
	public void addPersonToGroup(String codePerson, String groupName) throws NoSuchCodeException {
		Group g = groups.get(groupName);
		if(g == null) throw new NoSuchCodeException();
		g.addPerson(codePerson);
	}

	/**
	 * Retrieves the list of people on a group
	 * 
	 * @param groupName name of the group
	 * @return collection of person codes
	 */
	public Collection<String> listOfPeopleInGroup(String groupName) {
		Group g = groups.get(groupName);
		return g.ppg();
	}

	/**
	 * Retrieves the code of the person having the largest
	 * group of friends
	 * 
	 * @return the code of the person
	 */
	public String personWithLargestNumberOfFriends() {
		//COME ORDINARE IN ORDINE INVERSO COME E' STATO FATTO IN QUEL TEMA D'ESAME (forse library? oppure ??? (book? ma non penso))
		return pl.values().stream()

		//il .sorted() NON è consigliato solo perchè è più difficile e lungo da scrivere
		//.sorted(Comparator.comparing((Person r)-> r.getFriend().size()).reversed())
		//alternativamente
		.max(Comparator.comparing((Person r)-> r.getFriend().size()))
		//.findFirst()
		.map(Person::getid).orElse("no person found");
		//se lo stream è vuoto potrebbe tornare un optional, inoltre il .map() restituisce un optional
		//btw se lo stream è vuoto il .map() NON viene proprio applicato
		

		//.orElse("").map(Person::getid).reversed();
	}

	/**
	 * Find the code of the person with largest number
	 * of second level friends
	 * 
	 * @return the code of the person
	 */
	public String personWithMostFriendsOfFriends() {
		/*return pl.values().stream().forEach(
			r -> r.get
		);*/
		String sol = "";
		int max = 0;
		for(String i : pl.keySet()){
			try{
				Collection <String> s = this.friendsOfFriendsNoRepetition(i);
				if(s.size() > max){
					sol = i;
					max = s.size();
				}
			} catch(Exception e){
				
			}
			
		}
		/*return pl.values().stream() //stream di Person
			.max(Comparator.comparing(
				p -> p.friendsOfFriends().count()  //questo metodo NON lancia eccezioni, al contrato di friendsOfFriends nella classe Social
			))
			.map(Person::getid).orElse("");*/
		return sol;
	}

	/**
	 * Find the name of group with the largest number of members
	 * 
	 * @return the name of the group
	 */
	public String largestGroup() {
		//prendo lo stream di gruppi e vado a calcolare il massimo per gruppo
		
		String o = "";
		int max = 0;
		for(Group i : groups.values()){
			if(i.getsize() > max){
				max = i.getsize();
				o = i.getName();
			}

		}
		//return groups.values().stream().;
		return groups.values().stream()
				.max(Comparator.comparing( g -> g.ppg().size()))
				.map(Group::getName).orElse("");

		//return o;
	}

	/**
	 * Find the code of the person that is member of
	 * the largest number of groups
	 * 
	 * @return the code of the person
	 */
	public String personInLargestNumberOfGroups() {
		//parto dalla lista di persone, getGroups e poi size
		//funge se dentro la persona ho i gruppi di cui fa parte
		/*
		 * return people.values().stream()
				.max(Comparator.comparing(
						p -> p.getGroups().size()
				)).map(Person::getCode).orElse(NONE)
				;

		 */

		 //la versione sopra è MOLTO più facile rispetto a quella sotto
		 //struttura dati più complicata ma espressioni più semplici

		 //CONSIGLIO: METTI PIù COSE NELLA STRUTTURA DATI per poter scrivere le funzioni più BANALI POSSIBILI

		return groups.values().stream() //prendo tutti i gruppi
		
		//per ogni gruppo prendo i codici delle persone in quel gruppo
		//uso flatMap perchè altrimenti, usando direttamente .ppg() avrei uno stream di collezioni
		// il flatMap prende lo stream da ogni collezione e concatena gli stream
		.flatMap(r -> r.ppg().stream()) //recupero i codici delle persone
		//ora ho l'elenco di tutti i membri e tutti i gruppi -> se una persona è in 
		//più gruppi compare più volte

		//collect dove la chiave è il codice stesso e il valore è quante volte compare
		.collect(Collectors.groupingBy( //conto quante volte appaiono
			s -> s,  //SENZA counting() avrei una mappa con ogni codice e un lista
			Collectors.counting())  //con esso ho una mappa la cui chiave è il codice e il valore è il numero di volte in cui appare
			)
			//ore devo prendere la chiave il cui valore è 
		.entrySet() 
		.stream()//prendo lo stream di tutte le entry chiave valore e poi ordino in base al valore (che è il conteggio)
		.max(Comparator.comparing( Map.Entry::getValue)) //vado a ordinare in base al valore
		//ora non ho più delle persone ma delle entry in cui la chiave è ciò che cerco
		
		//ora prendo la chiave di ogni persona
		.map(Map.Entry::getKey).orElse("");
		//.map(Group::ppg).flatMap().collect(Collectors.groupingBy()).collect(Collectors.toSet());
	}

	/**
	 * add a new post by a given account
	 * @param author the id of the post author
	 * @param text the content of the post
	 * @return a unique id of the post
	 */
    public String post(String author, String text) {
		Post p = new Post(author, text);
		Person k = pl.get(author);
		String pid = p.addID();
		k.addpost(author, pid);
		pul.put(k, p);
		return pid;
    }

	/**
	 * retrieves the content of the given post
	 * @param author	the author of the post
	 * @param pid		the id of the post
	 * @return the content of the post
	 */
    public String getPostContent(String author, String pid) {
		Person p = pl.get(author);
		Post pu = pul.get(p);
		return pu.getPContent();
		//return p.;
    }

	/**
	 * retrieves the timestamp of the given post
	 * @param author	the author of the post
	 * @param pid		the id of the post
	 * @return the timestamp of the post
	 */
    public long getTimestamp(String author, String pid) {
		Person p = pl.get(author); Post pu = pul.get(p); return pu.getTime();
    }

	/**
	 * returns the list of post of a given author paginated 
	 * 
	 * @param author	author of the post
	 * @param pageNo	page number (starting at 1)
	 * @param pageLength page length
	 * @return the list of posts id
	 */
    public List<String> getPaginatedUserPosts(String author, int pageNo, int pageLength) {
		/*return paginate(pl.get(author).posts(), 
						pageNo, pageLength, 
						comparing(Post::getTimestamp).reversed(), // sort criterion
						Post::getId); // post pagination mapping
		*/
		
		/*int max = pageNo*pageLength; // il numero MASSIMO di post che devo considerare
		int skip = (pageNo-1)*pageLength;
			return
				pl.get(author).posts() //genera uno stream di post
				.sorted(comparing(Post::getTimestamp).reversed()) //in ordine decrescente di timestamp
				//dal più recente al più vecchio
				.limit(max)
				.skip(skip)
				.map(Post::getId) //prendo l'id del post
				.collect(toList());*/

				/* se non hai la funzione posts() 
				return pl.get(author).getFriend().stream() //genera uno stream di post
				,map(pl::get)
				.sorted(comparing(Post::getTimestamp).reversed()) //in ordine decrescente di timestamp
				//dal più recente al più vecchio
				.limit(max)
				.skip(skip)
				.map(Post::getId) //prendo l'id del post
				.collect(toList());*/ 
		return null;
    }

	/**
	 * returns the paginated list of post of friends 
	 * 
	 * the returned list contains the author and the id of a post separated by ":"
	 * 
	 * @param author	author of the post
	 * @param pageNo	page number (starting at 1)
	 * @param pageLength page length
	 * @return the list of posts key elements
	 */
	public List<String> getPaginatedFriendPosts(String author, int pageNo, int pageLength) {
		return null;
	}
}