package com.home.creator.principle2.movieExample;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    private String story;
    private double budget;
    private List<String> cast = new ArrayList<String>();
    private List<String> crew = new ArrayList<>();
    private String producer;
    private boolean editing;

    public void setStory(String story) {
        this.story = story;
    }

    public String getStory() {
        return story;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getBudget() {
        return budget;
    }

    public void setCast(String castMember) {
        cast.add(castMember);
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCrew(String crewMember) {
        crew.add(crewMember);
    }

    public List<String> getCrew() {
        return crew;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getProducer() {
        return producer;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public boolean isEditing() {
        return editing;
    }

    public String toString() {
        return "Producer:" + producer + "\n" +
                "Cast: " + cast + "\n" +
                "Crew: " + crew + "\n" +
                "Story: " + story + "\n" +
                "Budget: " + budget + "\n" +
                "Editing is done: " + editing;
    }
}
