package com.home.creator.principle2.movieExample;

import java.util.Random;

public class MovieProduction {

    private String[] cast = {"Tom Cruise", "Kevin Costner", "Samuel L. Jackson", "Jennifer Garner", "Scarlett Johannsen"};
    private String[] crew = {"Walter Zumtobel", "Manuel Riedmann", "Werner Umlaut", "Tabea Baldauf", "Sarah Engel"};
    private String[] producer = {"Disney", "Universal", "Legendary", "Pixar"};
    private String[] story = {"Fantasy", "Science Fiction", "Western", "Thriller", "Crime", "Drama", "Love"};
    private double[] budget = {3000000.0, 4500000.0 , 1000000.0};
    private Random random = new Random();
    private int select = 0;

    private MovieTheater movieTheater = new MovieTheater();

    public MovieProduction() {
    }

    private String development() {
        select = random.nextInt(story.length);
        return story[select];
    }

    private double financing() {
        select = random.nextInt(budget.length);
        return budget[select];
    }

    private void preProduction(Movie movie) {
        System.out.println("...pre production...");
        select = random.nextInt(cast.length);
        movie.setCast(cast[select]);
        select = random.nextInt(producer.length);
        movie.setProducer(producer[select]);
    }

    private String production() {
        System.out.println("...production...");
        select = random.nextInt(crew.length);
        return crew[select];
    }

    private boolean postProduction() {
        System.out.println("...post production...");
        return true;
    }

    private void marketing() {
        System.out.println("...do some marketing...");
    }

    private void distribution(Movie movie) {
        movieTheater.setNextMovie(movie);
    }

    public void startingProcess() {
        Movie movie = new Movie();
        movie.setStory(development());
        movie.setBudget(financing());
        preProduction(movie);
        movie.setCrew(production());
        movie.setEditing(postProduction());
        marketing();
        distribution(movie);
    }
}
