package entertainment;

import java.util.ArrayList;

public abstract class Video {
    private final String title;

    private int year;

    private ArrayList<String> cast;

    private ArrayList<String> genres;

    private int favouriteCounter;

    private int viewCounter;

    private String instanceClass;

    /**
     * Se incrementeaza numarul de show favorit.
     */
    public void addFavourite() {
        favouriteCounter++;
    }

    /**
     * Se incrementeaza numarul de vizionari.
     */
    public void addView() {
        viewCounter++;
    }

    /**
     * Se adauga x vizionari.
     */
    public void addView(final int x) {
        viewCounter += x;
    }

    /**
     * Se returneaza durata show-ului.
     */
    public abstract int getDuration();

    /**
     * Se returneaza ratingul show-ului.
     */
    public abstract double getRating();

    /**
     * Se adauga un rating la show.
     */
    public abstract void addRating(double rating, int season);

    /**
     * Constructorul de Video.
     */
    public Video(final String title, final int year, final ArrayList<String> cast,
                 final ArrayList<String> genres) {
        this.title = title;
        this.year = year;
        this.cast = cast;
        this.genres = genres;
        favouriteCounter = 0;
        viewCounter = 0;
    }

    public final String getTitle() {
        return title;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    public final int getFavouriteCounter() {
        return favouriteCounter;
    }

    public final int getViewCounter() {
        return viewCounter;
    }

    @Override
    public final String toString() {
        return title;
    }

    public final String getInstanceClass() {
        return instanceClass;
    }

    /**
     * Se seteaza subclasa definita.
     */
    public void setInstanceClass(final String instanceClass) {
        this.instanceClass = instanceClass;
    }
}
