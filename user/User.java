package user;

import entertainment.Shows;
import entertainment.Video;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class User {
    /**
     * Name
     */
    private String username;
    /**
     * Subscription type
     */
    private String subscriptionType;
    /**
     * The history of the movies seen
     */
    private Map<String, Integer> history;
    /**
     * Movies added to favorites
     */
    private ArrayList<String> favoriteMovies;

    private final Map<String, Double> ratings = new HashMap<>();

    /**
     * Constructor de User.
     */
    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies, final Shows shows) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.history = history;
        this.favoriteMovies = favoriteMovies;

        for (Map.Entry<String, Integer> entryShow : history.entrySet()) {
            shows.getShows().get(entryShow.getKey()).addView(entryShow.getValue());
        }
        for (String favoriteMovie : favoriteMovies) {
            shows.getShows().get(favoriteMovie).addFavourite();
        }
    }

    /**
     * Se adauga un show la vizionate.
     */
    public void addView(final String title) {
        if (history.containsKey(title)) {
            history.put(title, history.get(title) + 1);
            return;
        }
        history.put(title, 1);
    }

    /**
     * Se adauga un show la favorite.
     */
    public int addFavourite(final String title) {
        if (favoriteMovies.contains(title)) {
            return 1;
        } else if (!history.containsKey(title)) {
            return 2;
        }
        favoriteMovies.add(title);
        return 0;
    }

    /**
     * Se adauga un rating la un show.
     */
    public int addRating(final String movieTitle, final Double value, final int season) {
        String key = "Video " + movieTitle + "; Season number: " + season;
        if (ratings.containsKey(key)) {
            return 1;
        } else if (history.containsKey(movieTitle)) {
            ratings.put(key, value);
            return 2;
        }
        return 0;
    }

    private Comparator<Video> compareRating = (Video o1, Video o2) -> {
        if (o1.getRating() == o2.getRating()) {
            return o1.getTitle().compareTo(o2.getTitle());
        } else {
            return Double.compare(o1.getRating(), o2.getRating());
        }
    };

    /**
     * Triggers rating comparator.
     */
    public Comparator<Video> getCompareRating() {
        return compareRating;
    }

    public final String getUsername() {
        return username;
    }

    public final String getSubscriptionType() {
        return subscriptionType;
    }

    public final Map<String, Integer> getHistory() {
        return history;
    }

    public final Map<String, Double> getRatings() {
        return ratings;
    }

    @Override
    public final String toString() {
        return username;
    }
}
