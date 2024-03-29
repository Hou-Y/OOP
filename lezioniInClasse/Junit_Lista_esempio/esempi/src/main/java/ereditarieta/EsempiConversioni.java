package ereditarieta;

public class EsempiConversioni {

    public static void main(String[] args) {

        double d = 3.14;
        int j = (int)d; // cast (primitivi): cambia il valore
        
        Number[] numeri = { 1, 3.14, 42}; // autoboxing

        int acc=0;
        for( Number n : numeri){

            if( n instanceof Integer){
                Integer i = (Integer)n; // down-cast: cambia il tipo del riferimento

                Number m = i;  // up-cast è sempre corretto

                acc += i;
            }else{
                System.out.println("Non Integer: " + n);
            }

        }

        System.out.println(acc);
    }
}
