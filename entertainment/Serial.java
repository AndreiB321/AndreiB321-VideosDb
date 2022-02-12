package entertainment;

import java.util.ArrayList;

public class Serial extends Video {

    private int duration;

    private double rating = 0;

    private int numberSeasons;

    private ArrayList<Season> seasons;

    /**
     * Constructorul de Serial.
     */
    public Serial(final String title, final int year, final ArrayList<String> cast,
                  final ArrayList<String> genres, final int numberSeasons,
                  final ArrayList<Season> seasons) {
        super(title, year, cast, genres);
        super.setInstanceClass("shows");
        this.numberSeasons = numberSeasons;
        this.seasons = seasons;
        duration = calculateDuration();
        rating = calculateRating();

    }

    /**
     * Se calculeaza durata serialului.
     */
    private int calculateDuration() {
        int time = 0;
        for (Season season : seasons) {
            time += season.getDuration();
        }
        return time;
    }

    /**
     * Se calculeaza ratingul in functie de ratingul sezoanelor.
     */
    private double calculateRating() {
        double totalRating = 0.0;
        double currentSeasonRating;
        for (Season season : seasons) {
            currentSeasonRating = 0.0;
            for (Double grade : season.getRatings()) {
                currentSeasonRating += grade;
            }
            if (currentSeasonRating != 0) {
                currentSeasonRating /= (double) season.getRatings().size();
                totalRating += currentSeasonRating;
            }

        }
        this.rating = totalRating / (double) numberSeasons;
        return totalRating / (double) numberSeasons;
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
     * Adds a rating to season.
     */
    @Override
    public void addRating(final double grade, final int season) {
        this.seasons.get(season - 1).getRatings().add(grade);
        this.calculateRating();
    }
}
