package jobOffers; 
import java.util.*;
import java.util.stream.*;
import java.util.Collections.*;


public class JobOffers  {
	public final Set<String> skill = new HashSet<>();
	private int n = 0;
	public final Map<String, Position>  lp= new TreeMap<>();
	public final Map<String, Candidate>  cands= new TreeMap<>();
	public final Map<String, Consultant>  conds= new TreeMap<>();

//R1
	public int addSkills (String... skills) {
		for(String i: skills)
			{skill.add(i);}
		return skill.stream().collect(Collectors.toList()).size();
	}
	
	public int addPosition (String position, String... skillLevels) throws JOException {
		Position p = new Position(position, skillLevels);
		if(lp.containsKey(position) ) throw new JOException(position);
		for(String i : skillLevels){
			if(!skill.contains(i.split(":")[0])) throw new JOException("skill does not exist");
            Integer lvl = Integer.valueOf(i.split(":")[1]);
			if(lvl > 8 || lvl < 4)	throw new JOException(" ss");
		}
		lp.put(position, p);
		return p.media();
	}
	
//R2	
	public int addCandidate (String name, String... skills) throws JOException {
		Candidate c = new Candidate(name, skills);
		for(String i : skills){
			if(!skill.contains(i))throw new JOException(" lvl non definito");
		}
		if(cands.containsKey(name)) throw new JOException("candato presente ");
		cands.put(name, c);
		return c.getn();
	}
	
	public List<String> addApplications (String candidate, String... positions) throws JOException {
		Candidate c = cands.get(candidate);
		List<String> sol = new LinkedList<>();
		if(c == null ) throw new JOException("non esiste");
		for(String i : positions) {
			if(!lp.containsKey(i)) throw new JOException("non esiste");
			Position p = lp.get(i);
			if(!p.check(c)){throw new JOException("non rispetta lvl");}
			else{
					p.addc(c);
					sol.add(candidate+":"+i);
			}
		}
		sol.sort(Comparator.naturalOrder());
		return sol;
	} 
	
	public TreeMap<String, List<String>> getCandidatesForPositions() {
		Map<String, List<String>> ok =  lp.values().stream()
		// ora ho un
		//.toMap(
		//.collect(Collectors.groupingBy(
			.collect(Collectors.toMap(
				Position::gname, 
				Position::apllier
		)
		)
		;
		return new TreeMap<>(ok);
	}
	
	
//R3
	public int addConsultant (String name, String... skills) throws JOException {
		Consultant c = new Consultant(name, skills);
		for(String i : skills){
			if(!skill.contains(i))throw new JOException(" lvl non definito");
		}
		if(conds.containsKey(name)) throw new JOException("consulente presente ");
		conds.put(name, c);
		return c.getn();
	}
	
	public Integer addRatings (String consultant, String candidate, String... skillRatings)  throws JOException {
		Candidate c =  cands.get(candidate);
		Consultant cc = conds.get(consultant);
		if(c == null || conds.get(consultant)==null)throw new JOException("consulente o candidato non presente ");
		for(String i : c.getS()) {
			if(!skill.contains(i)) throw new JOException("non esiste");
			Position p = lp.get(i);
		}	
		if(! cc.getS().containsAll(c.getS()))throw new JOException("consultant senza tt le skill");
		for(String i:skillRatings) {if(Integer.valueOf(i.split(":")[1]) > 10 || Integer.valueOf(i.split(":")[1])< 4) throw new JOException("");}
		c.addrev(skillRatings);
		return c.gemm();
	}
	
//R4
	public List<String> discardApplications() {
		//for(Integer i : lp.values())
		/*Prendo una LISTA di Candidate e controllo se hanno i requisiti per i lavori per cui hanno fatto una application, la lista di Candidate è quello di QUESTA classe (cands) */
		List<String> sol = new LinkedList<>();
		for(Position i :lp.values()){
			for(String j : i.apllier()){ 
				if(!cands.get(j).eligible(i.getreq())){sol.add(j+":"+i.gname());}
				//boolean r = IntStream.range(0, c.getS().size()).allMatch(k -> c.getr().getKey(k) >= i.getreq().get(k));
			 }//List <String> s = i.apllier().stream().filter( )
		}
		Collections.sort(sol);
		return sol ;
	}
	
	 
	public List<String> getEligibleCandidates(String position) {
		Set<String> sol = new TreeSet<>();
		for(Position i :lp.values()){
			for(String j : i.apllier()){ 
				if(cands.get(j).eligible(i.getreq())){sol.add(j);}}}  List<String > l = sol.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList()); return l;
		
	}
	

	
}

		
