package university;

public class Student {
    private int id;
	private String first;
	private String last;
    private Course[] studyPlan = new Course[100];
    private int ncourse =0 ;
    private int nExamTaken = 0;
    private Exam[] eTaken = new Exam[25]; //ci sono fino a un max di 25 corsi
    private float avgBonus;

    //costruttore
    public Student(String first, String last, int id){
        this.first = first;
		this.last = last;
        this.id = id;

    }
    public String getFirst(){
        return first;
    }
    public String getLast(){
        return last;
    }
    public int getID(){
        return id;
    }

    public void addCourse(int cid, Course c){
        this.studyPlan[ncourse] = c;
        ncourse +=1;
    }

    public String getStudyPlan(University u){
        StringBuilder sPlan = new StringBuilder();
        int i =0, cid;
        while(i < ncourse){ 
            cid = studyPlan[i].getcid();
            sPlan = sPlan.append(u.course(cid)).append("\n");
            i++;

		}
        return sPlan.toString();
    }
   
    public void saveExam(Exam e){
        this.eTaken[nExamTaken] = e;
        nExamTaken +=1;
    }

    public String avgGrade(int sid){
        //StringBuilder avgFormatted = new StringBuilder();
        String avgFormatted;
        int i, sum=0;
        float sol;
        if(nExamTaken == 0){
            avgFormatted = "Student " + sid + " hasn't taken any exams";//\n";
        }
        else {
            for(i=0; i<nExamTaken; i++){
                sum += eTaken[i].getGrade();}
                //eTaken[i] restituisce un tipo exam    }
            sol = sum / nExamTaken;
            avgFormatted = "Student " +sid+" : " +sol; //+"\n";
        }
        return avgFormatted.toString();
    }

    public void calcAvgBonus(){
     //il numero di esami sostenuti diviso per il numero di corsi a cui lo studente è iscritto,
     // moltiplicato per 10, viene aggiunto   
    float bonus = (nExamTaken / ncourse)* 10;
    float avg;
    String tavg=avgGrade(getID());
    if(tavg.indexOf("hasn't taken any exams") != -1) //se trovo questa frase significa che non ha dato nessun
    //esame e quindi è esonerato dal bonus
        avg = 0;
    else
      { avg= Float.parseFloat(tavg.substring(tavg.lastIndexOf(": ") +1));}
    this.avgBonus = avg+bonus;
    }

    public float getAvgBonus(){
        return avgBonus;
    }
}

    class unOrderedTopStud{ //inner class
        int studID;
        float avgP;
        int positionn;
        int index;
        String firstn;
        String lastn;

        //classe COSTRUTTORE
        unOrderedTopStud(Student s, int unorderedIndex){
            this.studID=s.getID();
            this.avgP = s.getAvgBonus(); 
            this.firstn = s.getFirst();
            this.lastn = s.getLast();
            this.index = unorderedIndex;//attenzione: la posizione parte da 0
            //position equivale all'indice in cui sta nella ORDERED!!
            this.positionn = 99; //inizializzo a un valore altissimo per assicurarmi che quelli che non 
            //hanno fatto niente rimangano con valori non inizializzati
        } 

        float getScore(){
            return avgP;
        }

        String getName(){
            return firstn;
        }

        String getSurName(){
            return lastn;
        }

        int getPosition(){
            return positionn;
        }
        void writePosition(int positionOrdered){
            this.positionn= positionOrdered;
        }
        
    }
//}


