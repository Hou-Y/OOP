package it.polito.oop.elective;
import java.util.*;
import java.util.stream.*;

public class Course {
    public String cname;
    public Integer cn;
    //private Integer[]  choose = new Integer[3];
    private List<Long>  choose = Arrays.asList(0L, 0L, 0L);
    //private List<Long> oo = new ArrayList<>();

    private List<Student> sl = new LinkedList<>();

    public Course(String name, int avai){
        this.cname = name;
        this.cn = avai;
        

    }

    public List<Student> getstulist(){return sl;}

    public void addchoice(Integer n){
        choose.set(n, choose.get(n)+1);
    }

    public void addStudent(Student s){
        sl.add(s);
    }

    public boolean isSpace(){
        if(sl.size() < cn)
            return true;
        return false;
    }

    public List<Long> getchoice (){
       // List<Integer> o = new ArrayList<>(choose);
        //return choose.stream()
        //.collect(Collectors.toList());
        //choose.toList();
        return choose;
    }

}
