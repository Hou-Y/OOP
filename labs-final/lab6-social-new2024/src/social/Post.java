package social;

import java.util.*;

public class Post {
    public String id;
    private String auth;
    private String text;
    private Long time;
    private static int n = 0;

    public Post(String id, String text){
        this.auth = id;
        this.text = text;
        this.time = System.currentTimeMillis();

    }

    public String addID(){
        this.id = auth+ n++ +time.toString();
        return auth+ n++ +time.toString();
    }

    public String getPContent(){
         return text;
    }
   
    public Long getTime(){
        return time;
    }

}
