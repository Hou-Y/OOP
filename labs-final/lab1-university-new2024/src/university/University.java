package university;
import java.io.ObjectStreamField;
import java.util.Arrays;
import java.util.logging.Logger;

//import university.unOrderedTopStud;


/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {
	public static final int LIM_TOP_STU = 3;

	private String name;
	private String RecFName;
	private String RecLName;
	private int numStu = 0;
	private int numCourse = 0;

	private Student[] listS = new Student[100];

	private Course[] listC = new Course[50];

	//sID = new int[100];
	//courseID = new int[50];
    // R2
	/**
	 * Enrol a student in the university
	 * The university assigns ID numbers 
	 * progressively from number 10000.
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * 
	 * @return unique ID of the newly enrolled student
	 */
	public int enroll(String first, String last){
		
		int id = 10000+numStu;
		listS[numStu] = new Student(first, last, id);
		numStu +=1 ;

		logger.info("New student enrolled: "+id+", "+first+" "+last); //->come dovrei usare info()?? non ci sta niente da nessuna parte, da dove lo scopro?
		//VEDI logger, cerca nella documentazione
		return id;
	}
	
	/**
	 * Retrieves the information for a given student.
	 * The university assigns IDs progressively starting from 10000
	 * 
	 * @param id the ID of the student
	 * 
	 * @return information about the student
	 */
	public String student(int id){
		//public Student student(int id){
		
		Student s = listS[id-10000];
		return s.getID()+" "+s.getFirst()+" "+s.getLast();
		//return s;
	}

// R1
	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		// Example of logging
		// logger.info("Creating extended university object");
		//: to be implemented
		this.name = name;
	}
	
	/**
	 * Getter for the name of the university
	 * 
	 * @return name of university
	 */
	public String getName(){
		// to be implemented

		return name;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first first name of the rector
	 * @param last	last name of the rector
	 */
	public void setRector(String first, String last){
		//: to be implemented
		this.RecFName = first;
		this.RecLName = last;
	}
	
	/**
	 * Retrieves the rector of the university with the format "First Last"
	 * 
	 * @return name of the rector
	 */
	public String getRector(){
		// to be implemented
		return RecFName+" "+RecLName;
	}
	
 // R3
	/**
	 * Activates a new course with the given teacher
	 * Course codes are assigned progressively starting from 10.
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * 
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		int cid = 10+ numCourse;
		listC[numCourse] = new Course(title, teacher, cid);
		numCourse +=1;

		logger.info("New course activated: "+cid+", "+title+" "+teacher);

		return cid;
	}
	
	/**
	 * Retrieve the information for a given course.
	 * 
	 * The course information is formatted as a string containing 
	 * code, title, and teacher separated by commas, 
	 * e.g., {@code "10,Object Oriented Programming,James Gosling"}.
	 * 
	 * @param code unique code of the course
	 * 
	 * @return information about the course
	 */
	public String course(int code){
		Course c = listC[code-10];
		return c.getcid()+","+c.getcname()+","+c.getteacher();
	}
	
// R4
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		Student s = listS[studentID-10000];
		Course c = listC[courseCode - 10];
		s.addCourse(courseCode, c );
		c.newAtt(s);

		logger.info("Student "+studentID+" signed up for course "+courseCode);

	}
	
	/*public University getstudent(int sid){
		//devo cercare tra i dati presenti, come faccio a riferire all'oggetto che contiene quell'UNI?
		//l'Uni con i dati già belli pronti

		//cerco l'oggetto uni che contenga uno specifico sid, supponendo ci sia una singola Uni tra gli oggetti
		//ma se ne avessi più di uno??

		// boolean test = Arrays.asList(arr).contains(toCheckValue);
		// listS[sid]
		return targetUni;
	}*/
	
	/**
	 * Retrieve a list of attendees.
	 * 
	 * The students appear one per row (rows end with `'\n'`) 
	 * and each row is formatted as describe in in method {@link #student}
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		//ho una lista di sid che seguono questo corso (ne ho bisogno)
		//ma le info aggiuntive sono invisibili a qst lvl (sono dentro course)
		//getlist of student id che partecipano al corso dalla classe Course
		// e poi rimango sempre qua per prendere i nomi da student id usando i metodi getter 
		//NON avendo il numero della lista qua devo metter il for dentro la classe Course
		Course c = listC[courseCode - 10];
		//String nameStu = c.getListAtt(c, this);
		//Quando usi this senza alcun campo o metodo specifico, 
		//stai riferendoti all'intero oggetto corrente dell'istanza della classe in cui si trova il codice.

		return c.getListAtt(c, this);
	}

	/**
	 * Retrieves the study plan for a student.
	 * 
	 * The study plan is reported as a string having
	 * one course per line (i.e. separated by '\n').
	 * The courses are formatted as describe in method {@link #course}
	 * 
	 * @param studentID id of the student
	 * 
	 * @return the list of courses the student is registered for
	 */
	public String studyPlan(int studentID){
		Student s = listS[studentID - 10000];
		return s.getStudyPlan(this);
	}

// R5
	/**
	 * records the grade (integer 0-30) for an exam can 
	 * 
	 * @param studentId the ID of the student
	 * @param courseID	course code 
	 * @param grade		grade ( 0-30)
	 */
	public void exam(int studentId, int courseID, int grade) {
		Exam e = new Exam(studentId, courseID, grade);

		Student s = listS[studentId - 10000];
		s.saveExam(e);

		Course c = listC[courseID -10];
		c.savePass(e);

		logger.info("Student "+studentId+" took an exam in course "+courseID+" with grade "+grade);
	}

	/**
	 * Computes the average grade for a student and formats it as a string
	 * using the following format 
	 * 
	 * {@code "Student STUDENT_ID : AVG_GRADE"}. 
	 * 
	 * If the student has no exam recorded the method
	 * returns {@code "Student STUDENT_ID hasn't taken any exams"}.
	 * 
	 * @param studentId the ID of the student
	 * @return the average grade formatted as a string.
	 */
	public String studentAvg(int studentId) {
		Student s = listS[studentId -10000];
		return s.avgGrade(studentId);
	}
	
	/**
	 * Computes the average grades of all students that took the exam for a given course.
	 * 
	 * The format is the following: 
	 * {@code "The average for the course COURSE_TITLE is: COURSE_AVG"}.
	 * 
	 * If no student took the exam for that course it returns {@code "No student has taken the exam in COURSE_TITLE"}.
	 * 
	 * @param courseId	course code 
	 * @return the course average formatted as a string
	 */
	public String courseAvg(int courseId) {
		Course c = listC[courseId -10];
		return c.avgGrade(courseId);
	}
	

// R6
	/**
	 * Retrieve information for the best students to award a price.
	 * 
	 * The students' score is evaluated as the average grade of the exams they've taken. 
	 * To take into account the number of exams taken and not only the grades, 
	 * a special bonus is assigned on top of the average grade: 
	 * the number of taken exams divided by the number of courses the student is enrolled to, multiplied by 10.
	 * The bonus is added to the exam average to compute the student score.
	 * 
	 * The method returns a string with the information about the three students with the highest score. 
	 * The students appear one per row (rows are terminated by a new-line character {@code '\n'}) 
	 * and each one of them is formatted as: {@code "STUDENT_FIRSTNAME STUDENT_LASTNAME : SCORE"}.
	 * 
	 * @return info on the best three students.
	 */
	public String topThreeStudents() {
		//usa metodo avg che hai già scritto e poi sommaci il bonus supponendo NON ci siano parità di punteggio!
		//avg torna già formattato, non conviene... o si?
		int i, j, numTopStu = 0, forLoop;
		unOrderedTopStud listTop[] = new unOrderedTopStud[numStu];
		float allAvgs[] = new float[numStu];
		StringBuilder threeTops = new StringBuilder();
		float allAvgO[] = new float[numStu];
		
		for(i=0; i<numStu; i++){
			Student s = listS[i];
			s.calcAvgBonus();
			//meglio creare un array differente anzichè ordinare quella che già ho visto che rischio di 
			//avere casini nel trovare la posizione dall'id poi, inseriscili tutti e POI ordinali e seleziona i primi (min(3, num_stu_eleggibili))
			if(s.getAvgBonus() > 0){ //SOLO PER STUDENTI CHE SI SONO ISCRITTI A CORSI E HANNO SUPERATO ALMENO UN
				//add to structure (dovrei creare una inner class?? o una nested class??)
				listTop[numTopStu] = new unOrderedTopStud(s, i);
				//ora è vuoto e lo riempio, DEVE contenere null bruh
				allAvgs[numTopStu] = s.getAvgBonus();
				numTopStu++;
				//inserisci valori dentro listTop disordinati
			}
		}
		Arrays.sort(allAvgs); //ORDINA IN BASE AL PUNTEGGIO, SE TENEVO L'INTERO OGGETTO CHISSA' IN BASE A COSA ORDINAVA
		//Q: come ordinare un oggetto in base a un attributo invece di un altro??

		for(i=0; i<numStu ; i++){
			allAvgO[i] = 0;
		}
		allAvgO[0] = allAvgs[numStu-1];
		allAvgO[1] = allAvgs[numStu-2];
		if(numStu>2) allAvgO[2] = allAvgs[numStu-3];
		for(i=0; i<numTopStu; i++){
			for(j=0; j<numTopStu; j++){
				if(allAvgO[i] == listTop[j].getScore()){  //SE IL PUNTEGGIO è UGUALE A QUELLO DI UNO STUDENTE HO TROVATO LA POSIZIONE
					//BASANDOMI SULL'INDICE
					listTop[j].writePosition(i); // i è la posizione ORDINATA
					//ORA HO PER OGNI STUDENTE LA SUA POSIZIONE IN GRADUATORIA
					break; //this only breaks from the INNERMOST cycle!! 
				}
			}
		}
		//Arrays.sort(listTop); COME ORDINO DIRETTAMENTE listTop che contiene usando come criterio SOLO
		// il float degli average ma con l'entry degli average nascosta (invisibile a University)
		if (numTopStu < LIM_TOP_STU){
			forLoop=numTopStu;
		}
		else {
			forLoop = LIM_TOP_STU;
		}
		for(i=0; i<forLoop; i++){
			//trovo 
			//ma tanto non ho modo di raggiungere l'indice della classe Student OG che contiene i nomi
			//ma ora la complessità rimane quadratica ffkers
				for(j=0; j<numTopStu; j++){
					 //prova = listTop[j].getPosition();
					if(i ==listTop[j].getPosition())
						threeTops = threeTops.append(listTop[j].getName()).append(" ").append(listTop[j].getSurName()).append(" : ").append(allAvgO[i]).append("\n");
				// format : "STUDENT_FIRSTNAME STUDENT_LASTNAME : SCORE"
				}
			}
		return threeTops.toString();
	}

// R7
    /**
     * This field points to the logger for the class that can be used
     * throughout the methods to log the activities.
     */
    public static final Logger logger = Logger.getLogger("University");

}
