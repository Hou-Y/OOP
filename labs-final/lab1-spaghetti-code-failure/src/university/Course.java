package university;

public class Course {
    private int cid;
	private String teacher;
	private String cname;
    private Student[] listAtt= new Student[100];
    private int nAtt = 0;
    private int nPassed = 0;
    private Exam[] ePassed = new Exam[100]; //ci sono fino a un max di 100 studenti

    //costruttore
    public Course(String title, String teacher, int cid){
        this.teacher = teacher;
		this.cname = title; 
        this.cid = cid;
    }

    public String getteacher(){
        return teacher;
    }

    public String getcname(){
        return cname;
    }

    public int getcid(){
        return cid;
    }

    public void newAtt(Student s){
        this.listAtt[nAtt]= s; // elemento in posizione nAtt
        //prende il valore dell'ID studente (10000 + numStu)
        nAtt +=1;
    }

    //from here onwards review!!
    public String getCNAtt(int sid, University u){
        //vorrei che lo studente che ha sid = quello mandato  
        //return s.getFirst()+" "+s.getLast();
        //University u = u.getstudent(sid);
        return u.student(sid); 
    }

    public String getListAtt(Course c, University u){
        int sid;
        StringBuilder nameStu = new StringBuilder();
        //String nameStu = getCNAtt(listAtt[0], u);
        int i =0;
        while(i < nAtt){ //controlla il primo elemento  sia uno studente effettivo
        //do{
			//nameStu = nameStu+"\n"+getCNAtt(listAtt[i], u);
            sid = listAtt[i].getID();
            nameStu = nameStu.append(u.student(sid)).append("\n");
            i++;

		}//while(listAtt[i]!= 0);
        return nameStu.toString();
    }

    public void savePass(Exam e){
        this.ePassed[nPassed] = e;
        nPassed +=1;
    }

    public String avgGrade(int courseId){
        String avgC;
        int i, sum=0;
        float sol;
        if(nPassed == 0){
            avgC = "No student has taken the exam in "+getcname();
        }
        else {
            for(i=0; i<nPassed; i++){
                sum += ePassed[i].getGrade();

            }
            sol = (float) sum / nPassed; //se non lo esplichi torna 28.0
            //PRIMA fa divisione tra interi con tanto di troncamento e POI lo trasforma in un float
            //trasformando numeratore o denominatore in un float risolve
            //The average for the course COURSE_TITLE is: COURSE_AVG
            avgC = "The average for the course " + getcname()+" : " + sol; //+"\n";
        }
        return avgC;

    }
}
