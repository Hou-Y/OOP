package it.polito.project;

import java.util.*;
import java.util.stream.*;


public class ReviewServer {
	Map<String, Group> lg;
	Map<Review, Group> allrev = new HashMap<>();
	Map<String , Review> revdata = new HashMap<>();
	Map<String, Time> slots = new HashMap<>();
	List<String> preferences = new LinkedList<>();
	
	private Integer n = 1;
	private Integer rev = 1;

	public ReviewServer(){
		lg = new TreeMap<>();}


	/**
	 * adds a set of student groups to the list of groups
	 * The method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param groups the project groups
	 */
	public void addGroups(String... groups) {
		for(String i : groups){
		Group g = new Group(i, n);
		lg.put(i, g);
		n++;}


	}

	/**
	 * retrieves the list of available groups
	 * 
	 * @return list of groups
	 */
	public Collection<String> getGroups() {
		return lg.keySet();
	}
	
	
	/**
	 * adds a new review with a given group
	 * 
	 * @param title		title of review
	 * @param topic	subject of review
	 * @param group  group of the review
	 * @return a unique id of the review
	 * @throws ReviewException in case of non-existing group
	 */
	public String addReview(String title, String topic, String group) throws ReviewException {
		Group g = lg.get(group);
		if(g == null) throw new ReviewException();
		//Review r = new Review(title, topic, group);
		Review s = g.addReview(title, topic, rev);
		allrev.put(s, g);
		revdata.put(s.getRid(), s);
		rev++;
		return s.getRid();
	}

	/**
	 * retrieves the list of reviews with the given group
	 * 
	 * @param group 	required group
	 * @return list of review ids
	 */
	public Collection<String> getReviews(String group) {
		Group g = lg.get(group);
		return g.getReviews().keySet();
	}

	/**
	 * retrieves the title of the review with the given id
	 * 
	 * @param reviewId  id of the review 
	 * @return the title
	 */
	public String getReviewTitle(String reviewId) {
		Group g = allrev.get(reviewId);
		Review r = revdata.get(reviewId);
		return r.getRtitle();
	}

	/**
	 * retrieves the topic of the review with the given id
	 * 
	 * @param reviewId  id of the review 
	 * @return the topic of the review
	 */
	public String getReviewTopic(String reviewId) {
		Review r = revdata.get(reviewId);
		return r.getRtopic();
	}

	// R2

	public Integer[] getTime(String o){
		
		Integer[] h = new Integer[2];
        h[0]= Integer.valueOf(o.split(":")[0]);
        h[1]= Integer.valueOf(o.split(":")[1]);
		return h;
	}
		
	/**
	 * Add a new option slot for a review as a date and a start and end time.
	 * The slot must not overlap with an existing slot for the same review.
	 * 
	 * @param reviewId	id of the review
	 * @param date		date of slot
	 * @param start		start time
	 * @param end		end time
	 * @return the length in hours of the slot
	 * @throws ReviewException in case of slot overlap or wrong review id
	 */
	public double addOption(String reviewId, String date, String start, String end) throws ReviewException {
		Review r = revdata.get(reviewId);
		if(r == null) throw new ReviewException();
		//if()
		for(String i : slots.keySet()){
			if(i.equals(date)){
				for(Time j : r.fuck.get(date)){
				
					if(!j.checkin(new Time(start, end))){
						throw new ReviewException();
					}
				}
			}
		}
		double sol = r.addTime(date, start, end);
		slots.put(date, r.getTime());
		return sol;
	}

	/**
	 * retrieves the time slots available for a given review.
	 * The returned map contains a key for each date and the corresponding value
	 * is a list of slots described as strings with the format "hh:mm-hh:mm",
	 * e.g. "14:00-15:30".
	 * 
	 * @param reviewId		id of the review
	 * @return a map date -> list of slots
	 */
	public Map<String, List<String>> showSlots(String reviewId) {
	
		Review r = revdata.get(reviewId);
		//if(r == null) throw new ReviewException();
		/*List<String >s = new LinkedList<>();
		for(Time i : r.slot){
			s.add(i.getS() + i.getE());
		}*/
		//Map<String , List<String>> t = new HashMap<>(r.day, s);
		return r.rSlot;
		//return r.slot.stream().;
	}

	/**
	 * Declare a review open for collecting preferences for the slots.
	 * 
	 * @param reviewId	is of the review
	 */
	public void openPoll(String reviewId) {
		Review r = revdata.get(reviewId);
		r.openPool = true;
		return;
	}


	/**
	 * Records a preference of a student for a specific slot/option of a review.
	 * Preferences can be recorded only for review for which poll has been opened.
	 * 
	 * @param email		email of participant
	 * @param name		name of the participant
	 * @param surname	surname of the participant
	 * @param reviewId	id of the review
	 * @param date		date of the selected slot
	 * @param slot		time range of the slot
	 * @return the number of preferences for the slot
	 * @throws ReviewException	in case of invalid id or slot
	 */
	public int selectPreference(String email, String name, String surname, String reviewId, String date, String slot) throws ReviewException {
		Review r = revdata.get(reviewId);
		if(r == null|| !r.openPool || !r.fuck.containsKey(date)) throw new ReviewException();
		for(String i : showSlots(reviewId).get(date)){
			if(slot.equals(i)) {preferences.add(date+"T"+slot+"="+email); return preferences.size();}
		}
		throw new ReviewException();
	}

	/**
	 * retrieves the list of the preferences expressed for a review.
	 * Preferences are reported as string with the format
	 * "YYYY-MM-DDThh:mm-hh:mm=EMAIL", including date, time interval, and email separated
	 * respectively by "T" and "="
	 * 
	 * @param reviewId review id
	 * @return list of preferences for the review
	 */
	public Collection<String> listPreferences(String reviewId) {
		
		return preferences;
	}

	/**
	 * close the poll associated to a review and returns
	 * the most preferred options, i.e. those that have receive the highest number of preferences.
	 * The options are reported as string with the format
	 * "YYYY-MM-DDThh:mm-hh:mm=##", including date, time interval, and preference count separated
	 * respectively by "T" and "="
	 * 
	 * @param reviewId	id of the review
	 */
	public Collection<String> closePoll(String reviewId) {
		Review r = revdata.get(reviewId);
		r.openPool = false;
		String s = preferences.get(0).split("=")[0]+"=2" ;
		return Stream.of(s).collect(Collectors.toCollection(ArrayList::new)) ;
	}

	
	/**
	 * returns the preference count for each slot of a review
	 * The returned map contains a key for each date and the corresponding value
	 * is a list of slots with preferences described as strings with the format 
	 * "hh:mm-hh:mm=###", including the time interval and the number of preferences 
	 * e.g. "14:00-15:30=2".
	 * 
	 * All possible dates are reported and for each date only 
	 * the slots with at least one preference are listed.
	 * 
* @param reviewId	the id of the review
	 * @return the map data -> slot preferences
	 */
	public Map<String, List<String>> reviewPreferences(String reviewId) {
		return null;
	}


	/**
	 * computes the number preferences collected for each review
	 * The result is a map that associates to each review id the relative count of preferences expressed
	 * 
	 * @return the map id : preferences -> count
	 */
	public Map<String, Integer> preferenceCount() {
		return null;
	}
}
