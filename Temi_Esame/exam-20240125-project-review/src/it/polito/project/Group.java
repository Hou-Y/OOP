package it.polito.project;

import java.util.*;
import java.util.stream.*;

public class Group {
    private String name;
    private Integer ID;
    private final Map<String, Review> reviews = new TreeMap<>();


    public Group(String name, Integer n){
        this.name = name;
        this.ID = n;

    }

    public Review addReview(String title, String topic, Integer id){
        Review r = new Review(title, topic, "10"+id);
        reviews.put("10"+id, r);
        return r;

    }

    public Integer getid(){
        return ID;
    }

    public Map<String, Review> getReviews(){
        return reviews;
    }

}
