package actor;

import entertainment.Shows;

import java.util.ArrayList;
import java.util.Map;

public class Actor {
    /**
     * actor name
     */
    private final String name;
    /**
     * description of the actor's career
     */
    private final String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private final Map<ActorsAwards, Integer> awards;

    private int numberAwards = 0;

    private double rating = 0;
    /**
     * Constructorul de actor.
     */
    public Actor(final String name, final String careerDescription,
                 final ArrayList<String> filmography,
                 final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
        for (Map.Entry<ActorsAwards, Integer> entryAward : this.awards.entrySet()) {
            this.numberAwards += entryAward.getValue();
        }
    }

    /**
     * Se calculeaza ratingul actorului in functie de show-urile in care au jucat.
     */
    public final double calculateRating(final Shows shows) {
        int counter = 0;
        double grade = 0.0;

        for (String movie : filmography) {

            if (shows.getRating(movie) > 0) {
                grade += shows.getRating(movie);
                counter++;
            }
        }
        if (counter == 0) {
            return 0;
        }
        grade /= (double) counter;
        this.rating = grade;
        return grade;
    }

    public final String getName() {
        return name;
    }

    public final String getCareerDescription() {
        return careerDescription;
    }

    public final Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public final int getNumberAwards() {
        return numberAwards;
    }

    public final double getRating() {
        return rating;
    }

    @Override
    public final String toString() {
        return name;
    }
}
