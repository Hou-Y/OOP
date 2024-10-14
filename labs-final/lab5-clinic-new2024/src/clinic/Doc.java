package clinic;

public class Doc extends Pat {
    private String f; private String  l ;
    public String ssn;
    public Integer did;
    private String spec;

    public Doc(Integer did, String spec, String f, String l ,String ssn) {
        super(f, l ,ssn);
        this.did = did;
        this.spec = spec;
    }

   public String gets(){
    return spec;
   }

   public String getpfn(){return f;}
    public String getpln(){return l;}
    public String getpssn(){return ssn;}


    @Override
    public String toString(){
        //return getpln(+" "+f+" "+ssn;
        return null;
    }
}
