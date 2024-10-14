package it.polito.oop.books;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections.*;
import java.util.List;
import java.util.LinkedList;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collection;
import java.util.stream.Stream; 


public class Question {
	private String q;
	private Topic mt;
	public Map<String, Integer> ans = new HashMap<>();

	public Question(String que, Topic t){
		this.q = que;
		this.mt = t;
	}
	
	public String getQuestion() {
		return q;
	}
	
	public Topic getMainTopic() {
		return mt;
	}

	public void addAnswer(String answer, boolean correct) {
		// +1 corretto
		// -1 NON corretto
		int o;
		if(correct == true)
			o = 1;
		else
			o = -1;
		ans.put(answer, o);
	}
	
    @Override
    public String toString() {
        return q+"("+this.mt.getKeyword()+ ")";
    }

	public long numAnswers() {
	    return ans.size();
	}

	public Set<String> getCorrectAnswers() {
		return ans.entrySet().stream().filter(r -> r.getValue().equals(1))
		.map(Map.Entry::getKey)
		.collect(Collectors.toSet());
	//return null;
	}

	public Set<String> getIncorrectAnswers() {
		return ans.entrySet().stream().filter(r -> r.getValue().equals(-1))
		.map(Map.Entry::getKey)
		.collect(Collectors.toSet());
        //return ans.keySet().stream().filter(ans.values().equals(-1)).collect(Collectors.toSet());
	}
}
