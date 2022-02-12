package execution;

import actor.Actor;
import actor.Actors;
import common.Constants;
import entertainment.Shows;
import entertainment.Video;
import fileio.ActionInputData;
import user.User;
import user.Users;
import utils.Utils;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Query {

    public Query() {
    }

    /**
     * Se filtreaza query-urile.
     */
    public String execute(final Users users, final Actors actors, final Shows shows,
                          final ActionInputData action) {

        return switch (action.getObjectType()) {
            case "actors" -> executeActors(actors, shows, action);
            case "users" -> executeUsers(users, actors, shows, action);
            case "movies", "shows" -> executeVideos(actors, shows, action);
            default -> "Query result: []";
        };
    }



    private String executeActors(final Actors actors, final Shows shows,
                                 final ActionInputData action) {

        return switch (action.getCriteria()) {
            case Constants.AVERAGE -> queryActorsAverage(actors, shows, action);
            case Constants.AWARDS -> queryActorAwards(actors, action);
            case Constants.FILTER_DESCRIPTION -> queryActorsFilter(actors, action);
            default -> "Query result: []";
        };
    }

    private String executeVideos(final Actors actors, final Shows shows,
                                 final ActionInputData action) {
        String year = action.getFilters().get(0).get(0);
        String genre = action.getFilters().get(1).get(0);
        int size;
        ArrayList<Video> solution = new ArrayList<>();
        boolean check;

        for (Map.Entry<String, Video> element : shows.getShows().entrySet()) {
            Video video = element.getValue();
            check = !(year != null && !String.valueOf(video.getYear()).equals(year));

            check = !(genre != null && !video.getGenres().contains(genre))
                    && check;

            check = action.getObjectType().equals(video.getInstanceClass())
                    && check;

            check = !(action.getCriteria().equals("most_viewed") && video.getViewCounter() == 0)
                    && check;

            check = !(action.getCriteria().equals("favorite") && video.getFavouriteCounter() == 0)
                    && check;

            if (action.getCriteria().equals("ratings") && check) {
                check = video.getRating() != 0;
            }

            if (check) {
                solution.add(video);
            }
        }

        size =  Math.min(solution.size(), action.getNumber());

        if (action.getSortType().equals("desc")) {
            solution = (ArrayList<Video>) solution.stream().
                    sorted(shows.getComparator(action.getCriteria()).reversed()).
                    limit(size).collect(Collectors.toList());
        } else {
            solution = (ArrayList<Video>) solution.stream().
                    sorted(shows.getComparator(action.getCriteria())).
                    limit(size).collect(Collectors.toList());
        }

        return "Query result: " + solution.toString();
    }

    private String executeUsers(final Users users, final Actors actors, final Shows shows,
                                final ActionInputData action) {
        ArrayList<User> solution = new ArrayList<>();

        for (Map.Entry<String, User> entryUser : users.getUsers().entrySet()) {
            User value = entryUser.getValue();
            if (value.getRatings().size() != 0) {
                solution.add(value);
            }
        }

        if (action.getSortType().equals("desc")) {
            solution = (ArrayList<User>) solution.stream()
                    .sorted(users.getCompareRating().reversed())
                    .limit(action.getNumber()).collect(Collectors.toList());
        } else {
            solution = (ArrayList<User>) solution.stream()
                    .sorted(users.getCompareRating()).limit(action.getNumber())
                    .collect(Collectors.toList());
        }

        return "Query result: " + solution.toString();
    }

    private String queryActorsAverage(final Actors actors, final Shows shows,
                                      final ActionInputData action) {
        ArrayList<Actor> solution = new ArrayList<>();
        int size;
        for (Actor actor : actors.getActors()) {
            if (actor.calculateRating(shows) != 0) {
                solution.add(actor);
            }
        }
        size = Math.min(solution.size(), action.getNumber());
        if (action.getSortType().equals("desc")) {
            solution = (ArrayList<Actor>) solution.stream()
                    .sorted(actors.getCompareRating().reversed())
                    .limit(size).collect(Collectors.toList());
        } else {
            solution = (ArrayList<Actor>) solution.stream()
                    .sorted(actors.getCompareRating()).limit(size)
                    .collect(Collectors.toList());
        }

        return "Query result: " + solution.toString();

    }

    private String queryActorAwards(final Actors actors, final ActionInputData action) {
        ArrayList<Actor> solution = new ArrayList<>();
        boolean check;
        final int awardsNumber = 3;
        for (Actor actor : actors.getActors()) {
            check = true;
            for (String award : action.getFilters().get(awardsNumber)) {
                if (!actor.getAwards().containsKey(Utils.stringToAwards(award))) {
                    check = false;
                    break;
                }
            }

            if (check) {
                solution.add(actor);
            }
        }

        if (action.getSortType().equals("desc")) {
            solution = (ArrayList<Actor>) solution.stream()
                    .sorted(actors.getCompareAward().reversed())
                    .collect(Collectors.toList());
        } else {
            solution = (ArrayList<Actor>) solution.stream()
                    .sorted(actors.getCompareAward()).collect(Collectors.toList());
        }

        return "Query result: " + solution.toString();
    }

    private String queryActorsFilter(final Actors actors, final ActionInputData action) {
        ArrayList<Actor> solution = new ArrayList<>();
        Pattern pattern;
        String stringToFind;
        Matcher matcher;
        boolean check;

        for (Actor actor : actors.getActors()) {
            check = true;
            for (String word : action.getFilters().get(2)) {
                stringToFind = "[ -]" + word.toLowerCase() + "[ ,.]";
                pattern = Pattern.compile(stringToFind);
                matcher = pattern.matcher(actor.getCareerDescription().toLowerCase());
                check = matcher.find();
                if (!check) {
                    break;
                }
            }

            if (check) {
                solution.add(actor);
            }
        }

        if (action.getSortType().equals("desc")) {
            solution.sort(actors.getCompareName().reversed());
        } else {
            solution.sort(actors.getCompareName());
        }
        return "Query result: " + solution.toString();
    }
}
