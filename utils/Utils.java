package utils;

import actor.ActorsAwards;
import common.Constants;
import entertainment.Genre;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

import static entertainment.Genre.ACTION;
import static entertainment.Genre.ACTION_ADVENTURE;
import static entertainment.Genre.ADVENTURE;
import static entertainment.Genre.ANIMATION;
import static entertainment.Genre.COMEDY;
import static entertainment.Genre.CRIME;
import static entertainment.Genre.DRAMA;
import static entertainment.Genre.FAMILY;
import static entertainment.Genre.FANTASY;
import static entertainment.Genre.HISTORY;
import static entertainment.Genre.HORROR;
import static entertainment.Genre.KIDS;
import static entertainment.Genre.MYSTERY;
import static entertainment.Genre.ROMANCE;
import static entertainment.Genre.SCIENCE_FICTION;
import static entertainment.Genre.SCI_FI_FANTASY;
import static entertainment.Genre.THRILLER;
import static entertainment.Genre.TV_MOVIE;
import static entertainment.Genre.WAR;
import static entertainment.Genre.WESTERN;

/**
 * The class contains static methods that helps with parsing.
 *
 * We suggest you add your static methods here or in a similar class.
 */
public final class Utils {
    /**
     * for coding style
     */
    private Utils() {
    }

    /**
     * Transforms a string into an enum
     * @param genre of video
     * @return an Genre Enum
     */
    public static Genre stringToGenre(final String genre) {
        return switch (genre.toLowerCase()) {
            case "action" -> ACTION;
            case "adventure" -> ADVENTURE;
            case "drama" -> DRAMA;
            case "comedy" -> COMEDY;
            case "crime" -> CRIME;
            case "romance" -> ROMANCE;
            case "war" -> WAR;
            case "history" -> HISTORY;
            case "thriller" -> THRILLER;
            case "mystery" -> MYSTERY;
            case "family" -> FAMILY;
            case "horror" -> HORROR;
            case "fantasy" -> FANTASY;
            case "science fiction" -> SCIENCE_FICTION;
            case "action & adventure" -> ACTION_ADVENTURE;
            case "sci-fi & fantasy" -> SCI_FI_FANTASY;
            case "animation" -> ANIMATION;
            case "kids" -> KIDS;
            case "western" -> WESTERN;
            case "tv movie" -> TV_MOVIE;
            default -> null;
        };
    }

    /**
     * Se asociaza enum-ul cu un string.
     */
    public static String genreToString(final Genre genre) {
        return switch (genre) {
            case ACTION -> "Action";
            case ADVENTURE -> "Adventure";
            case DRAMA -> "Drama";
            case COMEDY -> "Comedy";
            case CRIME -> "Crime";
            case ROMANCE -> "Romance";
            case WAR -> "War";
            case HISTORY -> "History";
            case THRILLER -> "Thriller";
            case MYSTERY -> "Mystery";
            case FAMILY -> "Family";
            case HORROR -> "Horror";
            case FANTASY -> "Fantasy";
            case SCIENCE_FICTION -> "Science Fiction";
            case ACTION_ADVENTURE -> "Action & Adventure";
            case SCI_FI_FANTASY -> "Sci-Fi & Fantasy";
            case ANIMATION -> "Animation";
            case KIDS -> "Kids";
            case WESTERN -> "Western";
            case TV_MOVIE -> "Tv Movie";
        };
    }

    /**
     * Transforms a string into an enum
     * @param award for actors
     * @return an ActorsAwards Enum
     */
    public static ActorsAwards stringToAwards(final String award) {
        return switch (award) {
            case "BEST_SCREENPLAY" -> ActorsAwards.BEST_SCREENPLAY;
            case "BEST_SUPPORTING_ACTOR" -> ActorsAwards.BEST_SUPPORTING_ACTOR;
            case "BEST_DIRECTOR" -> ActorsAwards.BEST_DIRECTOR;
            case "BEST_PERFORMANCE" -> ActorsAwards.BEST_PERFORMANCE;
            case "PEOPLE_CHOICE_AWARD" -> ActorsAwards.PEOPLE_CHOICE_AWARD;
            default -> null;
        };
    }

    /**
     * Transforms an array of JSON's into an array of strings
     * @param array of JSONs
     * @return a list of strings
     */
    public static ArrayList<String> convertJSONArray(final JSONArray array) {
        if (array != null) {
            ArrayList<String> finalArray = new ArrayList<>();
            for (Object object : array) {
                finalArray.add((String) object);
            }
            return finalArray;
        } else {
            return null;
        }
    }

    /**
     * Transforms an array of JSON's into a map
     * @param jsonActors array of JSONs
     * @return a map with ActorsAwardsa as key and Integer as value
     */
    public static Map<ActorsAwards, Integer> convertAwards(final JSONArray jsonActors) {
        Map<ActorsAwards, Integer> awards = new LinkedHashMap<>();

        for (Object iterator : jsonActors) {
            awards.put(stringToAwards((String) ((JSONObject) iterator).get(Constants.AWARD_TYPE)),
                    Integer.parseInt(((JSONObject) iterator).get(Constants.NUMBER_OF_AWARDS)
                            .toString()));
        }

        return awards;
    }

    /**
     * Transforms an array of JSON's into a map
     * @param movies array of JSONs
     * @return a map with String as key and Integer as value
     */
    public static Map<String, Integer> watchedMovie(final JSONArray movies) {
        Map<String, Integer> mapVideos = new LinkedHashMap<>();

        if (movies != null) {
            for (Object movie : movies) {
                mapVideos.put((String) ((JSONObject) movie).get(Constants.NAME),
                        Integer.parseInt(((JSONObject) movie).get(Constants.NUMBER_VIEWS)
                                .toString()));
            }
        } else {
            System.out.println("NU ESTE VIZIONAT NICIUN FILM");
        }

        return mapVideos;
    }
}
