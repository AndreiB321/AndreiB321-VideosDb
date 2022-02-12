package execution;

import common.Constants;
import entertainment.Genre;
import entertainment.Shows;
import entertainment.Video;
import fileio.ActionInputData;
import user.User;
import user.Users;
import utils.Utils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Recommendation {

    public Recommendation() {
    }

    /**
     * Se filtreaza recomandarile.
     */
    public String execute(final Users users, final Shows shows, final ActionInputData action) {
        return switch (action.getType()) {
            case Constants.STANDARD -> executeStandardUsers(users, shows, action);
            case Constants.BEST_UNSEEN -> executeBestUnseenUsers(users, shows, action);
            case Constants.POPULAR -> executePopular(users, shows, action);
            case Constants.FAVOURITE -> executeFavorite(users, shows, action);
            case Constants.SEARCH -> executeSearch(users, shows, action);
            default -> "error -> invalid query";
        };
    }

    private String executeStandardUsers(final Users users, final Shows shows,
                                        final ActionInputData action) {
        User user = users.getUsers().get(action.getUsername());

        if (user == null) {
            return "error -> invalid user";
        }

        for (String video : shows.getShowsArray()) {
            if (!user.getHistory().containsKey(video)) {
                return "StandardRecommendation result: " + video;
            }
        }
        return "StandardRecommendation cannot be applied!";
    }

    private String executeBestUnseenUsers(final Users users, final Shows shows,
                                          final ActionInputData action) {
        User user = users.getUsers().get(action.getUsername());
        String solution = "";
        double maxRating = -1;
        double rating;

        if (user == null) {
            return "error -> invalid user";
        }

        for (String video : shows.getShowsArray()) {
            rating = shows.getShows().get(video).getRating();
            if (!user.getHistory().containsKey(video)) {
                if (Double.compare(rating, maxRating) > 0) {
                    solution = video;
                    maxRating = rating;
                }
            }
        }

        if (solution.equals("")) {
            return "BestRatedUnseenRecommendation cannot be applied!";
        } else {
            return "BestRatedUnseenRecommendation result: " + solution;
        }
    }

    private String executePopular(final Users users, final Shows shows,
                                  final ActionInputData action) {
        User user = users.getUsers().get(action.getUsername());
        Map<String, Integer> unsorted = new HashMap<>();
        String solution = "";
        String maxGenre = "";
        int counter;

        if (user == null) {
            return "error -> invalid user";
        }

        if (!user.getSubscriptionType().equals(Constants.PREMIUM)) {
            return "PopularRecommendation cannot be applied!";
        }

        for (Genre genre : Genre.values()) {
            counter = 0;
            for (Map.Entry<String, Video> element : shows.getShows().entrySet()) {
                if (element.getValue().getGenres().contains(Utils.genreToString(genre))) {
                    counter += shows.getShows().
                            get(element.getValue().getTitle()).getViewCounter();
                }
            }

            if (counter != 0) {
                unsorted.put(Utils.genreToString(genre), counter);
            }
        }

        List<Map.Entry<String, Integer>> list =
                new LinkedList<>(unsorted.entrySet());

        list.sort(((Comparator<Map.Entry<String, Integer>>)
                (o1, o2) -> ((o1.getValue()).compareTo(o2.getValue()))).reversed());

        for (Map.Entry<String, Integer> ent : list) {
            maxGenre = ent.getKey();
            for (String show : shows.getShowsArray()) {
                Video video = shows.getShows().get(show);
                if (!user.getHistory().containsKey(video.getTitle())) {
                    if (video.getGenres().contains(maxGenre)) {
                        solution = video.getTitle();
                        return "PopularRecommendation result: " + solution;
                    }
                }
            }
        }
        return "PopularRecommendation cannot be applied!";
    }

    private String executeFavorite(final Users users, final Shows shows,
                                   final ActionInputData action) {
        User user = users.getUsers().get(action.getUsername());
        String solution = "";
        int maxFavourite = 0;
        int favouriteShow;

        if (user == null) {
            return "error -> invalid user";
        }
        if (!user.getSubscriptionType().equals(Constants.PREMIUM)) {
            return "FavoriteRecommendation cannot be applied!";
        }

        for (Map.Entry<String, Video> entryShow : shows.getShows().entrySet()) {

            favouriteShow = shows.getShows().get(entryShow.getKey()).getFavouriteCounter();
            if (!user.getHistory().containsKey(entryShow.getKey())) {
                if (solution.equals("")) {
                    solution = entryShow.getKey();
                    maxFavourite = favouriteShow;
                }
                if (favouriteShow > maxFavourite) {
                    solution = entryShow.getKey();
                    maxFavourite = favouriteShow;
                }
            }
        }

        if (solution.equals("")) {
            return "FavoriteRecommendation cannot be applied!";
        } else {
            return "FavoriteRecommendation result: " + solution;
        }
    }


    private String executeSearch(final Users users, final Shows shows,
                                 final ActionInputData action) {
        User user = users.getUsers().get(action.getUsername());
        ArrayList<Video> solution = new ArrayList<>();
        String title;
        if (user == null) {
            return "error -> invalid user";
        }

        if (!user.getSubscriptionType().equals(Constants.PREMIUM)) {
            return "SearchRecommendation cannot be applied!";
        }

        for (Map.Entry<String, Video> entryShow : shows.getShows().entrySet()) {
            title = entryShow.getKey();
            if (!user.getHistory().containsKey(title)) {
                if (entryShow.getValue().getGenres().contains(action.getGenre())) {
                    solution.add(entryShow.getValue());
                }
            }
        }

        solution.sort(user.getCompareRating());
        if (solution.size() == 0) {
            return "SearchRecommendation cannot be applied!";
        }
        return "SearchRecommendation result: " + solution.toString();
    }
}
