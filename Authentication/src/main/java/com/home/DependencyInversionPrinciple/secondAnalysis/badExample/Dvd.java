package com.home.DependencyInversionPrinciple.secondAnalysis.badExample;

public class Dvd {

    private String title;
    private String review;
    private String sample;

    public Dvd(String title, String review, String sample) {
        this.title = title;
        this.review = review;
        this.sample = sample;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getReview() {
        return review;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getSample() {
        return sample;
    }
}
