package it.polito.tvseriesdb;

import java.util.*;
import java.util.stream.*;
import java.time.*;
import java.time.format.DateTimeFormatter;


public class TVSeriesDB {
	Set<String> serv = new TreeSet<>();
	Map<String, TV> series = new TreeMap<>();
	private Map<String, Actor> act = new TreeMap<>();

	// R1
	
	/**
	 * adds a list of transmission services.
	 * The method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param tServices the transmission services
	 * @return number of transmission service inserted so far
	 */
	public int addTransmissionService(String...tServices) {
		for(String i : tServices){
			serv.add(i);
		}
		return serv.size();
	}
	
	/**
	 * adds a TV series whose name is unique. 
	 * The method can be invoked multiple times.
	 * 
	 * @param title the title of the TV Series
	 * @param tService the name of the transmission service 
	 * broadcasting the TV series.
	 * @param genre the genre of the TV Series
	 * @return number of TV Series inserted so far
	 * @throws TSException if TV Series is already inserted or transmission service does not exist.
	 */
	public int addTVSeries(String title, String tService, String genre) throws TSException {
		if(series.keySet().contains(title) || !serv.contains(tService)) throw new TSException();
		series.put(title, new TV(title, tService, genre));
		return series.size();
		
	}
	
	/**
	 * adds an actor whose name and surname is unique. 
	 * The method can be invoked multiple times.
	 * 
	 * @param name the name of the actor
	 * @param surname the surname of the actor
	 * @param nationality the nationality of the actor
	 * @return number of actors inserted so far
	 * @throws TSException if actor has already been inserted.
	 */
	public int addActor(String name, String surname, String nationality) throws TSException {
		if(act.containsKey(name+" "+surname)){
			throw new TSException();
		}
		act.put(name+" "+surname, new Actor(name, surname, nationality));
		return act.size();
	}
	
	/**
	 * adds a cast of actors to a TV series
	 * 
	 * @param tvSeriesTitle	TV series for which the cast is inserted
	 * @param actors	list of actors to add to a TV series, format of 
	 * 					each actor identity is "name surname"
	 * @return number of actors in the cast
	 * @throws TSException in case of non-existing actor or TV Series does not exist
	 */
	public int addCast(String tvSeriesTitle, String...actors) throws TSException {
		if(!series.keySet().contains(tvSeriesTitle) )throw new TSException();
		for(String i : actors){
			if(!act.keySet().contains(i)) throw new TSException();
			act.get(i).addTVS(series.get(tvSeriesTitle));
			series.get(tvSeriesTitle).addA(act.get(i));
			//series.get()
		}
		

		return series.get(tvSeriesTitle).getA().size();
	}
      
	// R2
	
	/**
	 * adds a season to a TV series
	 * 
	 * @param tvSeriesTitle	TV series for which the season is inserted
	 * @param numEpisodes	number of episodes of the season
	 * @param releaseDate	release date for the season (format "gg:mm:yyyy")
	 * @return number of seasons inserted so far for the TV series
	 * @throws TSException in case of non-existing TV Series or wrong releaseDate
	 */
	public int addSeason(String tvSeriesTitle, int numEpisodes, String releaseDate) throws TSException {
		if(!series.containsKey(tvSeriesTitle)) throw new TSException();
		TV t = series.get(tvSeriesTitle);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:MM:yyyy");
		/*	DateTimeFormatter (sono case sensitive!!)
			y: Year
			M: Month in year
			d: Day in month
			H: Hour in day (0-23)
			h: Hour in am/pm (1-12)
			m: Minute in hour
			s: Second in minute
			S: Millisecond in second
			a: Am/pm marker
			E: Day of week (e.g. Tuesday) */
			//LocalDate last = LocalDate.parse(t.dates.get(t.gets()-2), formatter);
			//LocalDate current = LocalDate.parse(releaseDate,  formatter);
		if(t.dates.size() == 0 || LocalDate.parse(t.dates.get(t.gets()-1), formatter).compareTo(LocalDate.parse(releaseDate, formatter))<= 0)
			t.adds(numEpisodes, releaseDate);
			else throw new TSException();
		return t.gets();
	}
	

	/**
	 * adds an episode to a season of a TV series
	 * 
	 * @param tvSeriesTitle	TV series for which the season is inserted
	 * @param numSeason	number of season to which add an episode
	 * @param episodeTitle	title of the episode (unique)
	 * @return number of episodes inserted so far for that season of the TV series
	 * @throws TSException in case of non-existing TV Series, season, repeated title 
	 * 			of the episode or exceeding number of episodes inserted
	 */
	public int addEpisode(String tvSeriesTitle, int numSeason, String episodeTitle) throws TSException {
		TV t = series.get(tvSeriesTitle);
		if(t == null || numSeason > t.eps.size()) throw new TSException();
		Season s = t.episodes.get(numSeason-1);
		if(t.episodes.get(numSeason-1).eplist.values().contains(episodeTitle) || s.curNEps >=s.totEp )throw new TSException();
		t.episodes.get(numSeason-1).addep(episodeTitle);
		return t.episodes.get(numSeason-1).curNEps;
	}

	/**
	 * check which series and which seasons are still lacking
	 * episodes information
	 * 
	 * @return map with TV series and a list of seasons missing episodes information
	 * 
	 */
	public Map<String, List<Integer>> checkMissingEpisodes() {
		//return null ;
		//torna una mappa dove la chiave è il nome di una serie tv mentre ha per valori degli interi
		//che rappresentano quali sono le stagioni NON complete
		/*return series.values().stream().collect(Collectors.toMap(TV::getname, 
			( s->s.episodes.entrySet().stream().filter(entry -> entry.getValue().curNEps < entry.getValue().totEp)
			.map(o->o.getKey())
			.collect(Collectors.toList())
			)
			)); //così torna una mappa includendo anche le serie tv complete o SENZA stagioni
				//perchè il filtro lo hai messo DOPO il .toMap*/

		return series.values().stream()
		.filter(s->s.episodes.entrySet().stream().anyMatch(entry -> entry.getValue().curNEps < entry.getValue().totEp))
		.collect(Collectors.toMap(TV::getname, 
			s->s.episodes.entrySet().stream().filter(entry -> entry.getValue().curNEps < entry.getValue().totEp)
			.map(o->o.getKey())
			.map(o -> o+1)
			 //s ->s.episodes.keySet()
			 //ora ho un set di Integer ma sembra conti come Map<String, Object>
			 //.stream() //collect è un'operazione che puoi fare solo su stream
			 //.reduce( r -> r+1)
			 .collect(Collectors.toList())
		));
	}

	// R398
	
	/**
	 * Add a new user to the database, with a unique username.
	 * 
	 * @param username	username of the user
	 * @param genre		user favourite genre
	 * @return number of registered users
	 * @throws TSException in case username is already registered
	 */
	Map<String, User> u = new TreeMap<>();
	public int addUser(String username, String genre) throws TSException {
		User o = new User(username, genre);
		if(u.containsKey(username)) throw new TSException();
		u.put(username, o);
		return u.size();
	}

	/**
	 * Adds a series to the list of favourite
	 * series of a user.
	 * 
	 * @param username	username of the user
	 * @param tvSeriesTitle	 title of TV series to add to the list of favourites
	 * @return number of favourites TV series of the users so far
	 * @throws TSException in case user is not registered or TV series does not exist
	 */
	public int likeTVSeries(String username, String tvSeriesTitle) throws TSException {
		User o = u.get(username);
		if(!u.containsValue(o) || !series.containsKey(tvSeriesTitle) || o.likes().contains(tvSeriesTitle)) throw new TSException();
		o.addlike(tvSeriesTitle);
		series.get(tvSeriesTitle).addtolike(username);
		if(o.reviewdMap.get(tvSeriesTitle)!=null)
		o.likedMap.put(tvSeriesTitle,o.reviewdMap.get(tvSeriesTitle) );
		return o.likes().size();
	}
	
	/**
	 * Returns a list of suggested TV series. 
	 * A series is suggested if it is not already in the user list and if it is of the user's favourite genre 
	 * 
	 * @param username name of the user
	 * @throws TSException if user does not exist
	 */
	public List<String> suggestTVSeries(String username) throws TSException {
		if(!u.containsKey(username)) throw new TSException();
		String g = u.get(username).getugenre();
		List<String> l =  series.values()
		.stream()
		.filter(s ->s.getgenre().equals(g) && !s.userlike.contains(username))
		.map(TV::getname)
		.collect(Collectors.toList())
		;
		if(l.size() == 0)l.add("");
		return l;
	}
	
	//R4 

	/**
	 * Add reviews from a user to a tvSeries
	 * 
	 * @param username	username of the user
	 * @param tvSeries		name of the participant
	 * @param score		review score assigned
	 * @return the average score of the series so far from 0 to 10, extremes included
	 * @throws TSException	in case of invalid user, score or TV Series
	 */
	public double addReview(String username, String tvSeries, int score) throws TSException {
		if(score < 1 || score > 10 || !u.containsKey(username) || !series.containsKey(tvSeries)) throw new TSException();
		TV v = series.get(tvSeries);
		series.get(tvSeries).addscore(score);
		User o = u.get(username);
		u.get(username).addsc(score);
		u.get(username).reviewdMap.put(tvSeries, score);
		return v.getsum()/(double) v.scores.size() ;
	}

	/**
	 * Average rating of tv series in the favourite list of a user
	 * 
	 * @param username	username of the user
	 * @return the average score of the series in the list of favourites of the user
	 * @throws TSException	in case of invalid user, score or TV Series
	 */
	public double averageRating(String username) throws TSException {
		User o = u.get(username);
		if(o == null) throw new TSException();
		return o.likedMap.entrySet().stream().collect(Collectors.summingInt(Map.Entry::getValue)) / (double)o.sc.size();
		//la media tra le serie della lista dei piaciuti propria
	}
	
	// R5

	/**
	 * Returns most awaited season of a tv series using format "TVSeriesName seasonNumber", the last season of the best-reviewed TV series who has not come out yet with
	 * respect to the current date passed in input. In case of tie, select using alphabetical order. Date format: dd::mm::yyyy
	 * 
	 * @param currDate	currentDate
	 * @return the most awaited season of TV series who still has to come out
	 * @throws TSException	in case of invalid user, score or TV Series
	 */
	public String mostAwaitedSeason(String currDate) throws TSException {
		return null;
	}
	
	/**
	 * Computes the best actors working in tv series of a transmission service passed
	 * in input. The best actors have worked only in tv series of that transmission service
	 * with average rating higher than 8.
	 * @param transmissionService	transmission service to consider
	 * @return the best actors' names as "name surname" list
	 * @throws TSException	in case of transmission service not in the DB
	 */
	public List<String> bestActors(String transmissionService) throws TSException {
		if(!serv.contains(transmissionService)) throw new TSException();

		List<TV> sol = series.values().stream()
		.filter(v ->v.getsum()/(double) v.scores.size() >= 8)
		.collect(Collectors.toList());
		List<TV> exclude = series.values().stream()
		.filter(v ->v.getsum()/(double) v.scores.size() < 8)
		.collect(Collectors.toList());
		List<String > bact = new LinkedList<>();
 		/*return act.values().stream()
		.filter(s -> !s.tvs.contains(sol.getname()));*/
		for(Actor i : act.values()){
			for(TV j : exclude){
				if(i.tvs.contains(j)){
				break;
			}
			bact.add(i.getnamec());
			}

			
		}
		return bact;
	}

	
}
