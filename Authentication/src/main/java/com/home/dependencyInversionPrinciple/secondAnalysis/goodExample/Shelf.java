package com.home.dependencyInversionPrinciple.secondAnalysis.goodExample;

import java.util.ArrayList;

public class Shelf {

    ArrayList<Product> shelf = new ArrayList();

    public void addProduct(Product product) {
        shelf.add(product);
    }

    public String getProductReview(Product product) {
        for (int i = 0; i < shelf.size(); i++) {
            if (product.getTitle().equals(shelf.get(i).getTitle())) {
                return product.getReviews();
            }
        }
        return null;
    }

    public String getProductSample(Product product) {
        for (int i = 0; i < shelf.size(); i++) {
            if (product.getSample().equals(shelf.get(i).getSample())) {
                return product.getSample();
            }
        }
        return null;
    }
}