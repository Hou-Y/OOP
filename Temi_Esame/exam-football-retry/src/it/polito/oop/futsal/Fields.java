package it.polito.oop.futsal;

import java.util.stream.Collectors;
import java.util.*;

/**
 * Represents a infrastructure with a set of playgrounds, it allows teams
 * to book, use, and  leave fields.
 *
 */
public class Fields {
    private final Map<Integer, Field> campi = new TreeMap<>();
	public Integer n = 0;
    private Integer nind = 0;
    private Integer na = 0;
    private final Map<Integer, Associate> al = new HashMap<>();
    private String open;
    private String close;
    private Integer hopen ;
    private Integer mopen;
    private Integer hclose;
    private Integer mclose;
    private Set<Associate> booked = new HashSet<>();
    
    public static class Features {
        public final boolean indoor; // otherwise outdoor
        public final boolean heating;
        public final boolean ac;
        public Features(boolean i, boolean h, boolean a) {
            this.indoor=i; this.heating=h; this.ac = a;
        }
    }
    
	
    public void defineFields(Features... features) throws FutsalException {
        for(Features i : features){
            n++;
            Field d = new Field(i, n);
            if(!i.indoor && (i.heating || i.ac)) throw new FutsalException("campo non può avevre ac o heating");
            if(i.indoor) nind ++;
            campi.put(n, d);
            
        }
    }
    
    public long countFields() {
        return this.n;
    }

    public long countIndoor() {
        return this.nind;
    }
    
    public String getOpeningTime() {
        return open;
    }
    
    public void setOpeningTime(String time) {
        this.open = time;
        this.hopen = Integer.valueOf(time.split(":")[0]);
        this.mopen = Integer.valueOf(time.split(":")[1]);
    }
    
    public String getClosingTime() {
        return close;
    }
    
    public void setClosingTime(String time) {
        this.close = time;
        this.hclose = Integer.valueOf(time.split(":")[0]);
        this.mclose = Integer.valueOf(time.split(":")[1]);
    }

    public int newAssociate(String first, String last, String mobile) {
        na++;
        Associate a = new Associate(first, last, mobile, na);
        al.put(na, a);
        return na;
    }
    
    public String getFirst(int associate) throws FutsalException {
        Associate a = al.get(associate);
        if(a==null) throw new FutsalException();
        return a.getF();
        
        
        
    }
    
    public String getLast(int associate) throws FutsalException {
        Associate a = al.get(associate);
        if(a==null) throw new FutsalException();
        return a.getL();
    }
    
    public String getPhone(int associate) throws FutsalException {
        Associate a = al.get(associate);
        return a.getP();
    }
    
    public int countAssociates() {
        return na;

    }
    
    public void bookField(int field, int associate, String time) throws FutsalException {
        Field f = campi.get(field);
        Associate a = al.get(associate);
        Integer h =Integer.valueOf(time.split(":")[0]);
        Integer m = Integer.valueOf(time.split(":")[1]);
        booked.add(a);
        if((m.equals(mopen) && (h<=hclose && m<= mclose) && h>=hopen) && f != null && a != null)
            f.bookf(a, time, h, m,  hopen-hclose);
        else throw new FutsalException();
    }

    public boolean isBooked(int field, String time) {
        Field f  = campi.get(field);
        Integer h = Integer.valueOf(time.split(":")[0]);
        Integer m = Integer.valueOf(time.split(":")[1]);
        if ( h<hopen || h>hclose) return false;
        return f.checkBooked(h , m, time);
    }
    

    public int getOccupation(int field) {
        Field f = campi.get(field);
        return f.getnoc();
    }
    
    public List<FieldOption> findOptions(String time, Features required){
        List <Field> k = new LinkedList<>() ; boolean a = true;List <Field> s = campi.values().stream().filter(r ->r.match(required)).peek(System.out::println).collect(Collectors.toList()); for(Field i : s){  for(String j: i.book.keySet()){a = true; if((Integer.valueOf(time.split(":")[0]).equals(Integer.valueOf(j.split(":")[0])))){ a = false;} } if(a==true){k.add(i);}}
        return k.stream().sorted(Comparator.comparing(FieldOption::getOccupation).reversed().thenComparing(FieldOption::getField)).collect(Collectors.toList());
    }
    
    public long countServedAssociates() {
        return booked.size();
    }
    
    public Map<Integer,Long> fieldTurnover() {
        //perchè usare groupingBy e la method reference causano errori?
        /* perchè*/
        return campi.values().stream()
        //.collect(Collectors.groupingBy
        .collect(Collectors.toMap(
            Field::getField, 
            c -> (long) c.getOccupation()
            //(long)Field::getOccupation
        ));
    }
    
    public double occupation() {
        double d = hclose - hopen;
        double num = campi.values().stream()
            .flatMap(r -> r.book.values().stream())
            .collect(Collectors.counting());//.sum();
            //.collect(Collectors.
            //        groupingBy(s -> s, counting()));
        return num/(d*n);
    }
    
 }
