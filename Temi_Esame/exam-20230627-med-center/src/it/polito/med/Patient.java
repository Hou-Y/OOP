package it.polito.med;
import java.util.*;
import java.util.stream.*;
import java.time.*;

public class Patient {
    private String ssn;
    private String name;
    private String sur;

    private List<Appoint> apl = new LinkedList<>();

    public Patient(String ssn, String name, String surname){
        this.ssn = ssn;
        this.name = name;
        this.sur = surname;

    }

    public String getssn(){return ssn;}

}
