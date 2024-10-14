package it.polito.oop.books;

import java.util.List;
import java.util.LinkedList;

import java.util.stream.Collectors;
import java.util.Comparator;


public class TheoryChapter {
    private String name;
    private int n;
    private String text;
    public List<Topic> topicss = new LinkedList<>();

    public TheoryChapter(String tit, int np , String text ){
        this.name = tit;
        this.n = np;
        this.text = text;
    }

    public String getText() {
		return text;
	}

    public void setText(String newText) {
        this.text = newText;
    }


	public List<Topic> getTopics() {
        return topicss.stream().sorted(Comparator.comparing(Topic::getKeyword)).distinct().collect(Collectors.toList());
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
        this.topicss.add(topic);
        for(Topic c : topic.subt){
            //topicss.add(c) //
            addTopic(c); //1.2.1 è un subtopic di 1.2, usando SOLO la versione commentata
            //avresti SOLO estratto i subtopic direttamente SOTTO al topic ignorando i subtopic dei subtopic (in pratica ognorando la ricorsione)
            //per averla ricorsiva DEVI chiamare la funzione stessa (era scritto nei requisiti.. sto piangendo)
        }
    }
    
}
