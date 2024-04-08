package university;
// 100 studenti, 25 corsi distinti
// possono esserci fino a 100*25 esami 

public class Exam {
    private int esid;
    private int ecid;
    private int grade;

    //costruttore
    public Exam(int studentId, int courseID, int grade){
        this.esid = studentId;
        this.ecid = courseID;
        this.grade = grade;

    }

    public int getGrade(){
        return grade;
    }

    public int getStudentID(){
        return esid;
    }

    public int getCourseID(){
        return ecid;
    }
}
