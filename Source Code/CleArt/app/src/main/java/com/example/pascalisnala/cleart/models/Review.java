package com.example.pascalisnala.cleart.models;

public class Review {
    private User user;
    private int rating;
    private String review;
    private String datecreated;
    private Attraction attraction;
    private String image;

    public Review(User user, int rating, String review, String datecreated, Attraction attraction, String image) {
        this.user = user;
        this.rating = rating;
        this.review = review;
        this.datecreated = datecreated;
        this.attraction = attraction;
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public String getDatecreated() {
        return datecreated;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public String getImage() {
        return image;
    }
}
