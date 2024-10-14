package it.polito.oop.books;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections.*;
import java.util.Map;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections.*;
import java.util.Collection;
import java.util.stream.Stream;


public class ExerciseChapter {
    private String name;
    private int n;
    private String text;
    public Set<Topic> etop = new HashSet<>();
    public Set<Question> qus = new HashSet<>();

    public ExerciseChapter(String tit, int np){
        this.name = tit;
        this.n = np;
    }

    public String getText() {
		return text;
	}

    public void setText(String newText) {
        this.text = newText;
    }


	public List<Topic> getTopics() {
        List<Topic > s = new LinkedList<>();
        for(Topic i : this.etop){
            s.add(i);
        }
        //return s;
        return etop.stream().sorted(Comparator.comparing(Topic::getKeyword)).collect(Collectors.toList());
        //DA RICORDARE
	}

    public String getTitle() {
        return name;
    }

    public void setTitle(String newTitle) {
        this.name = newTitle;
    }

    public int getNumPages() {
        return n;
    }
    
    public void setNumPages(int newPages) {
        this.n = newPages;
    }
    
    public void addTopic(Topic topic) {
        this.etop.add(topic);
        for(Topic c : topic.subt){
            addTopic(c);
        }
    }
    

	public void addQuestion(Question question) {
        qus.add(question);
        this.etop.add(question.getMainTopic());
	}	
}
