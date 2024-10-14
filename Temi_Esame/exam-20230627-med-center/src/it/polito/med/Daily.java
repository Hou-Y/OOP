package it.polito.med;
import java.util.*;
import java.util.stream.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class Daily {
    private Map<LocalTime, Boolean>  slots = new TreeMap<>();
    private Map<LocalTime, LocalTime>  slotDur= new TreeMap<>();
    public Map<String , String >  oo= new TreeMap<>();
    private String start;
    private String end;
    private Integer dur;
    public List<Patient> patientsApp = new LinkedList<>();

    public Daily(String start, String end, int duration){
        for(LocalTime i : getschedule(LocalTime.parse(start), LocalTime.parse(end),duration)){
            slots.put(i, false); //true == booked
            slotDur.put(i, i.plus(duration, ChronoUnit.MINUTES));
            oo.put(i.toString(), i.plus(duration, ChronoUnit.MINUTES).toString());
        }
        this.dur = duration;

    }

   
    public List<String> slotlist(){
        List<String > s = new LinkedList<>();
        for(String i : oo.keySet()){
            s.add(i+"-"+oo.get(i));
        }
        return s;
    }

    public Map<LocalTime, Boolean>  getslots(){return slots;}
    public Integer getdur(){return dur;}

    public List<LocalTime> getschedule(LocalTime da, LocalTime a, int duration){
		LocalTime current = da;
        ArrayList<LocalTime> res = new ArrayList<>();
        while( current.isBefore(a) ){
            res.add(current);
            current = current.plusMinutes(duration);}
			return res;
	}

}
