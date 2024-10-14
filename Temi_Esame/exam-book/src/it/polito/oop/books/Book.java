package it.polito.oop.books;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collections.*;
import java.util.Map;
import java.util.Set;
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


public class Book {
	Map<String, Topic> tl = new TreeMap<>();
	Map <String, Question> ql = new TreeMap<>();
	Map <String, TheoryChapter> tcl = new TreeMap<>();
	Map <String, ExerciseChapter> ecl = new TreeMap<>();
	Map <String, Assignment> compit = new TreeMap<>();
    /**
	 * Creates a new topic, if it does not exist yet, or returns a reference to the
	 * corresponding topic.
	 * 
	 * @param keyword the unique keyword of the topic
	 * @return the {@link Topic} associated to the keyword
	 * @throws BookException
	 */
	public Topic getTopic(String keyword) throws BookException {
	    Topic t = new Topic(keyword);
		if(keyword == null || keyword =="") throw new BookException();
		if(tl.keySet().contains(keyword)){
			return tl.get(keyword);
		}
		tl.put(keyword, t);
		return t;
	}

	public Question createQuestion(String question, Topic mainTopic) {
        Question q = new Question(question, mainTopic);
		ql.put(question, q);
		return q;
	}

	public TheoryChapter createTheoryChapter(String title, int numPages, String text) {
        TheoryChapter tc = new TheoryChapter(title, numPages, text);
		tcl.put(title, tc);
		return tc;
	}

	public ExerciseChapter createExerciseChapter(String title, int numPages) {
        ExerciseChapter tc = new ExerciseChapter(title, numPages);
		ecl.put(title, tc);
		return tc;
	}

	public List<Topic> getAllTopics() {
		return tl.values().stream().collect(Collectors.toList());
        //List<Topic> l =
		/* */
		//return null;
		/*return this.tcl.values().stream().map()
		.collect(Collectors.toList());*/
	}

	public boolean checkTopics() {
		Set <Topic> s = ecl.values().stream()
			.flatMap(r -> r.getTopics().stream())
			//.map(Collection::stream)
			.collect(Collectors.toSet());
		//.collect(Collectors.toSet());
		Set <Topic> p = tcl.values().stream()
			.flatMap(r -> r.getTopics().stream())
			//.map(Collection::stream)
			.collect(Collectors.toSet());
        return (p.containsAll(s));
		/*In other words, p.contains(s) checks if s is an element of p, 
		not if all elements of s are in p. 
		To check if all elements of s are in p, you should use p.containsAll(s). */
	}

	public Assignment newAssignment(String ID, ExerciseChapter chapter) {
		Assignment a = new Assignment(ID, chapter);
		compit.put(ID, a);
		//come dovremmo sapere il numero totale delle risposte corrette?
        return a;
	}
	
    /**
     * builds a map having as key the number of answers and 
     * as values the list of questions having that number of answers.
     * @return
     */
    public Map<Long,List<Question>> questionOptions(){
		/*Map <Question, Long> s = ql.values().stream()
		.collect(Collectors.groupingBy(Question::q, Question::numAnswers));*/
        
		//return new Map(s.entrySet().collect(Collectors.groupingBy(r ->r.numAnswer()))) ;

		//le versioni sopra sono entrambe sbagliate non fare questi errori, quella sotto è corretta
		return ql.values().stream()
		.collect(Collectors.groupingBy(Question::numAnswers));
		//groupingBy con un singolo parametro crea, per ogni istanza del parametro
		// una lista contentente l'oggetto (nello stream) che possiede tale caratteristica
    }
}
