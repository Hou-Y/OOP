package it.polito.library;

public class Rental {
        private String start;
        private String end;
        private Book b;
        private Reader r;

        public Rental(Book b, Reader r, String st){
            this.b = b;
            this.r = r;
            this.start = st;
            this.end = "ONGOING";

        }

        public String getr(){
            return r.getID();
        }

        public String gettime(){
            return start+" "+end;
        }

        public void endr(String end){
            this.end = end;
        }

    

}
