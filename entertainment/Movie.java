package entertainment;

import java.util.ArrayList;

public class Movie extends Video {

    private int duration;

    private double rating;

    private final ArrayList<Double> ratings = new ArrayList<Double>();

    /**
     * Constructorul de movie.
     */
    public Movie(final String title, final int year, final ArrayList<String> cast,
                 final ArrayList<String> genres, final int duration) {
        super(title, year, cast, genres);
        super.setInstanceClass("movies");
        this.duration = duration;

    }

    private double calculateRating() {

        double totalRating = 0.0;
        for (Double grade : ratings) {
            totalRating += grade;
        }

        if (ratings.size() != 0) {
            this.rating = totalRating / (double) ratings.size();
            return totalRating / (double) ratings.size();
        }
        return 0.0;
    }

    @Override
    public final int getDuration() {
        return duration;
    }

    @Override
    public final double getRating() {
        return rating;
    }

    /**
     * Adds and recalculates rating
     */
    @Override
    public void addRating(final double grade, final int season) {

        this.ratings.add(grade);
        this.calculateRating();
    }

    public final ArrayList<Double> getRatings() {
        return ratings;
    }

}
