package it.polito.oop.books;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Assignment {
    private String stu;
    private ExerciseChapter ec;
    public List<Double> stusco = new ArrayList<>();


    public Assignment(String id, ExerciseChapter ecc){
        this.stu = id;
        this.ec = ecc;

    }
    public String getID() {
        return stu;
    }

    public ExerciseChapter getChapter() {
        return ec;
    }

    public double addResponse(Question q,List<String> answers) {
       double totc = q.getCorrectAnswers().size();
       double tots = q.getIncorrectAnswers().size();
       Set<String> correct = q.getCorrectAnswers();
       Set<String> worg = q.getIncorrectAnswers();
       double N = totc+tots;
        double FP = answers.stream().filter(r -> q.getIncorrectAnswers().contains(r)).toList().size();

        //da quelle corrette TOGLI quelle che NON sono presenti tra le risposte degli studenti
        double FN = correct.stream().filter(r -> !answers.contains(r)).count();
        stusco.add((N - FP - FN)/N);
    return (N - FP - FN)/N;
        
    }
    
    public double totalScore() {
        return stusco.stream().reduce((a,b) -> a+b).orElse(0.0);
    }

}
