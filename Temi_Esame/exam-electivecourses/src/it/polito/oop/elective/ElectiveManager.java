package it.polito.oop.elective;

import java.util.*;
import java.util.stream.*;

/**
 * Manages elective courses enrollment.
 * 
 *
 */
public class ElectiveManager {
    Map<String, Course> course = new TreeMap<>();
    Map<String, Student> slist = new TreeMap<>();
    Map<String , Double> students = new HashMap<>();
    private List<Notifier> notifiers= new LinkedList<>();
    //Map<String, Integer> pref = new TreeMap<>();

    /**
     * Define a new course offer.
     * A course is characterized by a name and a number of available positions.
     * 
     * @param name : the label for the request type
     * @param availablePositions : the number of available positions
     */
    public void addCourse(String name, int availablePositions) {
        Course c = new Course(name, availablePositions);
        course.put(name, c);
    }
    
    /**
     * Returns a list of all defined courses
     * @return
     */
    public SortedSet<String> getCourses(){
        //public Set<String> getCourses(){ // NON MODIFICARE MAI I TIPI PER LE FUNZIONI GIA' PRESENTI
       //SortedSet<String >s = new TreeSet<>();
        //s = courses.keySet();
        //return s;
        return course.keySet().stream().collect(Collectors.toCollection(TreeSet::new));
        //return course.keySet();
    }
    
    /**
     * Adds a new student info.
     * 
     * @param id : the id of the student
     * @param gradeAverage : the grade average
     */
    public void loadStudent(String id, 
                                  double gradeAverage){
                                    students.put(id, gradeAverage);
                                    Student s = new Student(id, gradeAverage);
                                    slist.put(id, s);


        
    }

    /**
     * Lists all the students.
     * 
     * @return : list of students ids.
     */
    public Collection<String> getStudents(){
        return students.keySet();
    }
    
    /**
     * Lists all the students with grade average in the interval.
     * 
     * @param inf : lower bound of the
     *  interval (inclusive)
     * @param sup : upper bound of the interval (inclusive)
     * @return : list of students ids.
     */
    public Collection<String> getStudents(double inf, double sup){
        return students.entrySet()
        .stream()
        .filter( s ->s.getValue() >= inf && s.getValue()<=sup)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
    }


    /**
     * Adds a new enrollment request of a student for a set of courses.
     * <p>
     * The request accepts a list of course names listed in order of priority.
     * The first in the list is the preferred one, i.e. the student's first choice.
     * 
     * @param id : the id of the student
     * @param selectedCourses : a list of of requested courses, in order of decreasing priority
     * 
     * @return : number of courses the user expressed a preference for
     * 
     * @throws ElectiveException : if the number of selected course is not in [1,3] or the id has not been defined.
     */
    public int requestEnroll(String id, List<String> courses)  throws ElectiveException {
        Student s = slist.get(id);
        if( s == null ||courses.size() > 3 || courses.size() < 1) throw new ElectiveException();
        for(String i : courses) {
            if(!course.keySet().contains(i)){
                    throw new ElectiveException();
            }
            
        }
        Integer j = 0;
        for(String i : courses) {
            course.get(i).addchoice(j);
            //s.addPref(course.get(i));
            j++;
            
        }
        notifiers.forEach(l->l.requestReceived(id));
        /* //equals to 
         * for(Notfier nt : notifiers){
         * nt.requestReceived(id);}
         */
        s.addC(courses);
        //requestReceived
        //Notifier.requestReceived(id);
        return courses.size();
    }
    
    /**
     * Returns the number of students that selected each course.
     * <p>
     * Since each course can be selected as 1st, 2nd, or 3rd choice,
     * the method reports three numbers corresponding to the
     * number of students that selected the course as i-th choice. 
     * <p>
     * In case of a course with no requests at all
     * the method reports three zeros.
     * <p>
     * 
     * @return the map of list of number of requests per course
     */
    public Map<String,List<Long>> numberRequests(){
        /*List<String> s =  students
            .entrySet().stream()
            .sorted(Comparator.comparing(Map.Entry::getValue));
            //.map(Map.Entry::getKey)
            .flatMap(Map.Entry::getKey)
            .;*/
         Map<String, List<Long>> sol = new HashMap<>();
         for(String i : course.keySet()){
            sol.put(i, course.get(i).getchoice());
         }
        return sol;
    }
    
    
    /**
     * Make the definitive class assignments based on the grade averages and preferences.
     * <p>
     * Student with higher grade averages are assigned to first option courses while they fit
     * otherwise they are assigned to second and then third option courses.
     * <p>
     *  
     * @return the number of students that could not be assigned to one of the selected courses.
     */
    public long makeClasses() {
        //prendo gli studenti, li ordino per voto decrescente (dal più alto al più basso)
        //servo quello in cima, segno che è enrolled e diminuisco di 1 i posti disponibili nel corso
        //Map<String, Double>  sOrdered = students.entrySet().stream().sorted(Comparator.comparing().reversed());
        List <Student> ordered = slist.values().stream().sorted(Comparator.comparing(Student::getavg).reversed()).collect(Collectors.toList());
        return ordered.stream().mapToInt(
            s -> {
                for(int i = 0; i<s.pref.size(); i++){
                    String k = s.pref.get(i);
                    Course co = course.get(k);
                    if(co!= null && co.isSpace()){
                        notifiers.forEach( l -> l.assignedToCourse(s.getname(), k));
                        s.enroll(co, i+1);
                        co.addStudent(s);
                        return 0;
                    }
                }
                return 1;
            }
        ).sum();
        //return -1;
    }
    
    
    /**
     * Returns the students assigned to each course.
     * 
     * @return the map course name vs. student id list.
     */
    public Map<String,List<String>> getAssignments(){
        return course.values().stream().collect(Collectors.toMap( 
            s-> s.cname, 
            s ->s.getstulist().stream().map(Student::getname).collect(Collectors.toList())
            //il map lo applichi su uno STREAM !!!!!!!!!!!!!!!!!!!!!!!!
            ));
    }
    
    
    /**
     * Adds a new notification listener for the announcements
     * issues by this course manager.
     * 
     * @param listener : the new notification listener
     */
    public void addNotifier(Notifier listener) {
        this.notifiers.add(listener);
        
    }
    
    /**
     * Computes the success rate w.r.t. to first 
     * (second, third) choice.
     * 
     * @param choice : the number of choice to consider.
     * @return the success rate (number between 0.0 and 1.0)
     */
    public double successRate(int choice){
        //tra tutti gli studenti, quanti sono andati nella classe che hanno selezionato come prima, seconda o terza scelta
        // il numero è dato da choice
        double den = slist.size();
        int num = slist.values().stream().collect(Collectors.summingInt(r ->{
            if( r.getscelta() == choice)
                return 1;
            return 0;
        } ));
        return num/den;
    }

    
    /**
     * Returns the students not assigned to any course.
     * 
     * @return the student id list.
     */
    public List<String> getNotAssigned(){
        return slist.values().stream().filter(s->s.getscelta() == -1).map(Student::getname).collect(Collectors.toList());
    }
    
    
}
