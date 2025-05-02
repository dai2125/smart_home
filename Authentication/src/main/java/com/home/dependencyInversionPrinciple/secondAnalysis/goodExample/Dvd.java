package com.home.dependencyInversionPrinciple.secondAnalysis.goodExample;

public class Dvd implements Product {

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

    public void setSample(String sample) {
        this.sample = sample;
    }

    @Override
    public String getReviews() {
        return review;
    }

    @Override
    public String getSample() {
        return sample;
    }
}
