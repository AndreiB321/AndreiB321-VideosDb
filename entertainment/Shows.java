package entertainment;

import fileio.MovieInputData;
import fileio.SerialInputData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Shows {

    private final Map<String, Video> shows = new HashMap<>();
    private final ArrayList<String> showsArray = new ArrayList<>();

    public Shows() {
    }

    /**
     *  Se adauga toate filmele si serialele in lista, respectiv map.
     */
    public void addShows(final List<MovieInputData> movieInputData,
                         final List<SerialInputData> serialInputData) {
        for (MovieInputData movieInput : movieInputData) {
            this.addShow(movieInput.getTitle(), new Movie(movieInput.getTitle(),
                    movieInput.getYear(), movieInput.getCast(),
                    movieInput.getGenres(), movieInput.getDuration()));
        }

        for (SerialInputData serialInput : serialInputData) {
            this.addShow(serialInput.getTitle(), new Serial(serialInput.getTitle(),
                    serialInput.getYear(), serialInput.getCast(), serialInput.getGenres(),
                    serialInput.getNumberSeason(), serialInput.getSeasons()));
        }
    }

    /**
     * Se adauga un show in hashmap.
     */
    private void addShow(final String title, final Video video) {
        shows.put(title, video);
    }

    /**
     * Se adauga un show la vizionate.
     */
    public void addView(final String title) {
        shows.get(title).addView();
    }

    /**
     * Se adauga un show la favorite.
     */
    public void addFavourite(final String title) {
        shows.get(title).addFavourite();
    }

    /**
     * Se adauga un rating la show.
     */
    public void addRating(final String title, final double rating, final int season) {
        shows.get(title).addRating(rating, season);
    }

    /**
     * Se returneaza ratingul unui show specificat.
     */
    public final double getRating(final String title) {
        if (shows.containsKey(title) && shows.get(title) != null) {
            return shows.get(title).getRating();
        }
        return -1;
    }

    public final Map<String, Video> getShows() {
        return shows;
    }

    public final ArrayList<String> getShowsArray() {
        return showsArray;
    }

    /**
     * Comparator for each filter
     */
    public Comparator<Video> getComparator(final String filter) {

        return switch (filter) {
            case "longest" -> (Video o1, Video o2) ->  {
                if (o1.getDuration() == o2.getDuration()) {
                    return o1.getTitle().compareTo(o2.getTitle());
                } else {
                    return o1.getDuration() - o2.getDuration();
                }
            };
            case "favorite" -> (Video o1, Video o2) -> {
                if (o1.getFavouriteCounter() == o2.getFavouriteCounter()) {
                    return o1.getTitle().compareTo(o2.getTitle());
                } else {
                    return o1.getFavouriteCounter() - o2.getFavouriteCounter();
                }
            };
            case "ratings" -> (Video o1, Video o2) -> {
                if (o1.getRating() == o2.getRating()) {
                    return o1.getTitle().compareTo(o2.getTitle());
                } else {
                    return Double.compare(o1.getRating(), o2.getRating());
                }
            };
            default -> (Video o1, Video o2) -> {
                if (o1.getViewCounter() == o2.getViewCounter()) {
                    return o1.getTitle().compareTo(o2.getTitle());
                } else {
                    return o1.getViewCounter() - o2.getViewCounter();
                }
            };

        };
    }
}
