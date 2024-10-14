package mountainhuts;

public class Ranges {
    public static final Ranges DEFAULT = new Ranges("0-"+Integer.MAX_VALUE);
    private final int start;
    private final int end;
    //come Working hours, estremo superiore INCLUSO
    //supponendo siano DISGIUNTI


    //costruttore
    public Ranges(String t){
        String[] hm=t.split("-");
		this.start = Integer.parseInt(hm[0]);
		this.end = Integer.parseInt(hm[1]);
    }

    public int getStart(){
        return start;
    }

    public int getEnd(){
        return end;
    }

    public boolean contains(Integer altitude){
         return start<=altitude && end>=altitude;
    }

    public String toString() {
		return start + "-" + (end==Integer.MAX_VALUE?"INF":end);
        //check ? cosa_fo_se_vero : cosa_fo_se_falso
	}

}
