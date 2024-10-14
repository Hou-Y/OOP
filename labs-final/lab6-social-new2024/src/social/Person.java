package social;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Person {
    public String idp;
    private String name;
    private String surn;
    //salvare la Stringa ID è più facile ma salvare 
	//la persona mi permette di fare operazioni più complesse
    private List<String> f = new LinkedList<>();
    private Map<String, String>  postl = new TreeMap<>();

    public Person(String code, String name, String surname){
        this.idp = code;
        this.name = name;
        this.surn = surname;
    }

    public void addpost(String auth, String pid){
        postl.put(pid, auth);

    }

    public String getid(){
        return this.idp;
    }

    public String getname(){
        return this.name;
    }

    //public Collection<String> getFriends() {
		//if(Social.ALTERNATIVE){
			//return f;
		/* }else{ // OR
			return friendsPerson.stream()
			.map( Person::getCode )
			.collect(Collectors.toList());
		}*/
//	}


    /*public Stream<String> friendsOfFriends() {  //questo se hai usato una lista di Person come amici invece del loro ID
		return f.stream()
			.flatMap( s-> s.getFriends().stream() )
			;
	}*/


    public String getSurn(){
        return this.surn;
    }

    public void addf(String c){

        f.add(c);
    }

    public List<String> getFriend(){
        return f;
    }

    public Map<String, String> getpost(){
        return postl;
    }

    @Override
    public String toString(){
        return idp;
    }

}
