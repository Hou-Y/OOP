package it.polito.med;

import java.util.*;
import java.util.stream.*;
import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class MedManager {
	private final Set<String > specialityList = new HashSet<>();
	Map<String, Doc>docs = new  TreeMap<>();
	Map<String, Patient>pl = new  TreeMap<>();
	public Integer n = 0;
	private String curDate;
	private final Map<String , Appoint> alist = new TreeMap<>();

	/**
	 * add a set of medical specialities to the list of specialities
	 * offered by the med centre.
	 * Method can be invoked multiple times.
	 * Possible duplicates are ignored.
	 * 
	 * @param specialities the specialities
	 */
	public void addSpecialities(String... specialities) {
		for(String i : specialities){
			specialityList.add(i);
		}

		
	}

	/**
	 * retrieves the list of specialities offered in the med centre
	 * 
	 * @return list of specialities
	 */
	public Collection<String> getSpecialities() {
		return specialityList;
	}
	
	
	/**
	 * adds a new doctor with the list of their specialities
	 * 
	 * @param id		unique id of doctor
	 * @param name		name of doctor
	 * @param surname	surname of doctor
	 * @param speciality speciality of the doctor
	 * @throws MedException in case of duplicate id or non-existing speciality
	 */
	public void addDoctor(String id, String name, String surname, String speciality) throws MedException {
		if(docs.keySet().contains(id) || !specialityList.contains(speciality)) throw new MedException();
		Doc o = new Doc(id, name, surname, speciality);
		docs.put(id, o);

	}

	/**
	 * retrieves the list of doctors with the given speciality
	 * 
	 * @param speciality required speciality
	 * @return the list of doctor ids
	 */
	public Collection<String> getSpecialists(String speciality) {
		return docs.values().stream()
		.filter(s ->s.getspe().equals(speciality))
		.map(Doc::getid)
		.collect(Collectors.toCollection(LinkedList::new));
		//*/
		//return 
	}

	/**
	 * retrieves the name of the doctor with the given code
	 * 
	 * @param code code id of the doctor 
	 * @return the name
	 */
	public String getDocName(String code) {
		Doc d = docs.get(code);
		return d.getname();
	}

	/**
	 * retrieves the surname of the doctor with the given code
	 * 
	 * @param code code id of the doctor 
	 * @return the surname
	 */
	public String getDocSurname(String code) {
		Doc d = docs.get(code);
		return d.getsurn();
	}

	/**
	 * Define a schedule for a doctor on a given day.
	 * Slots are created between start and end hours with a 
	 * duration expressed in minutes.
	 * 
	 * @param code	doctor id code
	 * @param date	date of schedule
	 * @param start	start time
	 * @param end	end time
	 * @param duration duration in minutes
	 * @return the number of slots defined
	 */
	public List<LocalTime> getschedule(LocalTime da, LocalTime a, int duration){
		LocalTime current = da;
        ArrayList<LocalTime> res = new ArrayList<>();
        while( current.isBefore(a) ){
            res.add(current);
            current = current.plusMinutes(duration);}
			return res;
	}

	public int addDailySchedule(String code, String date, String start, String end, int duration) {
		Doc d = docs.get(code);
		Daily slots = d.addSche(date, start, end, duration);
		return slots.getslots().size();
	}

	/**
	 * retrieves the available slots available on a given date for a speciality.
	 * The returned map contains an entry for each doctor that has slots scheduled on the date.
	 * The map contains a list of slots described as strings with the format "hh:mm-hh:mm",
	 * e.g. "14:00-14:30" describes a slot starting at 14:00 and lasting 30 minutes.
	 * 
	 * @param date			date to look for
	 * @param speciality	required speciality
	 * @return a map doc-id -> list of slots in the schedule
	 */
	public Map<String, List<String>> findSlots(String date, String speciality) {
		return docs.values().stream()
		.filter(s -> s.getspe().equals(speciality) && s.getsche().containsKey(date))
		//.filter( s )
		 .collect(Collectors.toMap(
			Doc::getid,
			d ->d.getsche().get(date)
			.slotlist()
			//.getslots()
			//.map()
		))
		;
	}

	/**
	 * Define an appointment for a patient in an existing slot of a doctor's schedule
	 * 
	 * @param ssn		ssn of the patient
	 * @param name		name of the patient
	 * @param surname	surname of the patient
	 * @param code		code id of the doctor
	 * @param date		date of the appointment
	 * @param slot		slot to be booked
	 * @return a unique id for the appointment
	 * @throws MedException	in case of invalid code, date or slot
	 */
	public String setAppointment(String ssn, String name, String surname, String code, String date, String slot) throws MedException {
		if(!docs.containsKey(code)) throw new MedException();
		Doc d = docs.get(code);
		if(!d.getsche().keySet().contains(date))throw new MedException();
		/*for(){

		}*/
		//manca cambiare booleano da vero a falso e check sullo slot
		Patient p = new Patient(ssn, name, surname);
		n++;
		Appoint a = new Appoint(p , d, slot, date, n);
		String s = slot.split("-")[0]; String e = slot.split("-")[1];
		if(!d.schedule.get(date).oo.keySet().contains(s) || !d.schedule.get(date).oo.values().contains(e) || !LocalTime.parse(e).minus(d.schedule.get(date).getdur(), ChronoUnit.MINUTES).equals(LocalTime.parse(s))){ throw new MedException();}
		alist.put(n.toString(), a);
		d.adda(a);
		return n.toString();
	}

	/**
	 * retrieves the doctor for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return doctor code id
	 */
	public String getAppointmentDoctor(String idAppointment) {
		Appoint a = alist.get(idAppointment);
		return a.getdoc();
	}

	/**
	 * retrieves the patient for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return doctor patient ssn
	 */
	public String getAppointmentPatient(String idAppointment) {
		Appoint a = alist.get(idAppointment);
		return a.getp().getssn();
	}

	/**
	 * retrieves the time for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return time of appointment
	 */
	public String getAppointmentTime(String idAppointment) {
		Appoint a = alist.get(idAppointment);
		return a.getappstart();
	}

	/**
	 * retrieves the date for an appointment
	 * 
	 * @param idAppointment id of appointment
	 * @return date
	 */
	public String getAppointmentDate(String idAppointment) {
		Appoint a = alist.get(idAppointment);
		return a.getappd();
	}

	/**
	 * retrieves the list of a doctor appointments for a given day.
	 * Appointments are reported as string with the format
	 * "hh:mm=SSN"
	 * 
	 * @param code doctor id
	 * @param date date required
	 * @return list of appointments
	 */
	public Collection<String> listAppointments(String code, String date) {
		return docs.get(code)
		.geta().stream()
		.filter(s ->s.getappd().equals(date))
		//.map(Appoint::getdoc)
		//.map(t -> t.get)
		//.map(Appoint::getd)
		.map(t -> t.getappstart()+"="+t.getp().getssn())
		.collect(Collectors.toCollection(LinkedList::new))

		;
	}

	/**
	 * Define the current date for the medical centre
	 * The date will be used to accept patients arriving at the centre.
	 * 
	 * @param date	current date
	 * @return the number of total appointments for the day
	 */
	public int setCurrentDate(String date) {
		curDate = date;
		return alist.values().stream().filter( s->s.getappd().equals(date)).collect(Collectors.summingInt( r -> 1));
	}

	/**
	 * mark the patient as accepted by the med centre reception
	 * 
	 * @param ssn SSN of the patient
	 */
	private Map<String, Set<Appoint>> docAppointToday = new TreeMap<>();
	Integer f = 0;
	List<Patient> l = new LinkedList<>();
	public void accept(String ssn) {
		//voglio aggiungere String, List<> come una entry di docAppointToday, come fo?
		Map<String, Set<Appoint>> tmp = (alist.values().stream().filter(s ->s.getappd().equals(curDate) && (s.getp().getssn().equals(ssn)|| l.contains(pl.get(ssn))))// || docAppointToday.containsKey(s.getdoc())) )
		//.filter(docAppointToday.values().stream().flatMap(f ->f.stream().map(Appoint::getp)))
			.collect(Collectors.groupingBy(Appoint::getdoc, Collectors.toCollection(TreeSet::new))));
		String k = tmp.keySet().stream().findFirst().get();
		docAppointToday.put(k, tmp.get(k)); l.add(pl.get(ssn)); f++;
		//se ho due pazienti diversi che verranno visitati lo stesso giorno dallo stesso dottore
		//questa struttura dati NON mi permette di avere ENTRAMBI i pazienti
	}

	/**
	 * returns the next appointment of a patient that has been accepted.
	 * Returns the id of the earliest appointment whose patient has been
	 * accepted and the appointment not completed yet.
	 * Returns null if no such appointment is available.
	 * 
	 * @param code	code id of the doctor
	 * @return appointment id
	 */
	public String nextAppointment(String code) {
		return docAppointToday.entrySet().stream()
		.filter(s ->s.getKey().equals(code))
		.flatMap(s ->s.getValue().stream()) //da Set<Appoint> -> stream di Appoint
		.map(Appoint::getaid) //cosicchè sullo stream sia in grado di fare questa operazione di map!
		.findFirst().orElse(null);
		//return docs.get(code).aa.stream().filter(pa.contains(Appoint::getp));
	}

	/**
	 * mark an appointment as complete.
	 * The appointment must be with the doctor with the given code
	 * the patient must have been accepted
	 * 
	 * @param code		doctor code id
	 * @param appId		appointment id
	 * @throws MedException in case code or appointment code not valid,
	 * 						or appointment with another doctor
	 * 						or patient not accepted
	 * 						or appointment not for the current day
	 */
	public void completeAppointment(String code, String appId)  throws MedException {
		if(!docs.containsKey(code) || !alist.containsKey(appId) || !docAppointToday.containsKey(code)) throw new MedException();
		alist.get(appId).complete = true; 
		docAppointToday.get(code).remove(alist.get(appId));
		//ogni volta che completo un appuntamento lo TOLGO dagli appuntamenti di oggi MA
		//showRate necessita di TUTTI gli appuntamenti, sia quelli completati che quelli NON completati!!
	}

	/**
	 * computes the show rate for the appointments of a doctor on a given date.
	 * The rate is the ratio of accepted patients over the number of appointments
	 *  
	 * @param code		doctor id
	 * @param date		reference date
	 * @return	no show rate
	 */
	public double showRate(String code, String date) {
		Doc d = docs.get(code); 
		/*double k = ((double)docAppointToday.entrySet().stream()
		.filter(s ->s.getKey().equals(code)).flatMap(s ->s.getValue().stream()).collect(Collectors.toList()).size());
		int kk = d.aa.stream().filter(r ->r.getappd().equals(date)).collect(Collectors.toList()).size();*/
		//se non trasformi ALMENO UNA DELLE due in un double avrai un TRONCAMENTO!!
		return (double)f/ d.aa.stream().filter(r ->r.getappd().equals(date)).collect(Collectors.toList()).size();
		/*return ((double)docAppointToday.entrySet().stream()
		.filter(s ->s.getKey().equals(code)).flatMap(s ->s.getValue().stream()).collect(Collectors.toList()).size())
		/d.aa.stream().filter(r ->r.getappd().equals(date)).collect(Collectors.toList()).size();*/
	}

	/**
	 * computes the schedule completeness for all doctors of the med centre.
	 * The completeness for a doctor is the ratio of the number of appointments
	 * over the number of slots in the schedule.
	 * The result is a map that associates to each doctor id the relative completeness
	 * 
	 * @return the map id : completeness
	 */
	public Map<String, Double> scheduleCompleteness() {
		return docs.values().stream().collect(Collectors.toMap(Doc::getid,  Doc::gc));
	}

}
