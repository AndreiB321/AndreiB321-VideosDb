package user;

import entertainment.Shows;
import fileio.UserInputData;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Users {
    private final Map<String, User> users = new HashMap<>();

    public Users() {
    }

    /**
     * Se adauga userii in hashmap.
     */
    public void addUsers(final List<UserInputData> userInputData, final Shows shows) {
        for (UserInputData userInput : userInputData) {
            this.addUser(userInput.getUsername(), new User(userInput.getUsername(),
                    userInput.getSubscriptionType(), userInput.getHistory(),
                    userInput.getFavoriteMovies(), shows));
        }
    }

    /**
     * Se adauga un user.
     */
    private void addUser(final String username, final User user) {
        users.put(username, user);
    }

    /**
     * Se adauga un show la view-uri.
     */
    public void addView(final String username, final String title) {
        users.get(username).addView(title);
    }

    /**
     * Se adauga la favorite un show.
     */
    public final int addFavourite(final String username, final String title) {
        return users.get(username).addFavourite(title);
    }

    /**
     * Se adauga un rating al unui user la un show.
     */
    public final int addRating(final String username, final String movieTitle,
                               final Double value, final int season) {
        return users.get(username).addRating(movieTitle, value, season);
    }

    public final Map<String, User> getUsers() {
        return users;
    }

    /**
     * Triggers rating comparator.
     */
    public Comparator<User> getCompareRating() {
        return compareRating;
    }

    private Comparator<User> compareRating = (User o1, User o2) -> {
        if (o1.getRatings().size() == o2.getRatings().size()) {
            return o1.getUsername().compareTo(o2.getUsername());
        } else {
            return Double.compare(o1.getRatings().size(), o2.getRatings().size());
        }
    };


}
