package it.polito.oop.books;

import java.util.List;
import java.util.Comparator;
import java.util.LinkedList;

public class Topic {
	private String key;
	public List<Topic> subt= new LinkedList<>();

	public Topic(String k){
		this.key = k;
	}

	public String getKeyword() {
        return key;
	}
	
	@Override
	public String toString() {
	    return key;
	}

	public boolean addSubTopic(Topic topic) {
		try{
				subt.add(topic);
		} catch(Exception e){
			return false;
		}
        return true;
	}

	/*
	 * Returns a sorted list of subtopics. Topics in the list *MAY* be modified without
	 * affecting any of the Book topic.
	 */
	public List<Topic> getSubTopics() {
        return subt.stream().distinct().sorted(Comparator.comparing(Topic::getKeyword)).toList();
	}
}
