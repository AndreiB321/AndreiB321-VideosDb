package execution;

import common.Constants;
import entertainment.Shows;
import fileio.ActionInputData;
import user.Users;


public class Command {

    public Command() {
    }

    /**
     * Se filtreaza comenzile de tip command.
     */
    public String execute(final Users users, final Shows shows,
                          final ActionInputData action) {
        return switch (action.getType()) {
            case Constants.FAVOURITE -> this.executeFavourite(action, users, shows);
            case Constants.VIEW -> this.executeView(action, users, shows);
            case Constants.RATING -> this.executeRating(action, users, shows);
            default -> "error -> invalid command";
        };
    }

    /**
     * Se executa comanda favourite care adauga un show la favorite.
     */
    private String executeFavourite(final ActionInputData action,
                                    final Users users, final Shows shows) {
        switch (users.addFavourite(action.getUsername(),
                action.getTitle())) {
            case 0:
                shows.addFavourite(action.getTitle());
                return "success -> " + action.getTitle() + " was added as favourite";
            case 1:
                return "error -> " +  action.getTitle() + " is already in favourite list";
            case 2:
                return "error -> " + action.getTitle() + " is not seen";
            default:
                return "error -> invalid command";
        }
    }

    /**
     * Se adauga un show la lista de vizionate a unui user.
     */
    private String executeView(final ActionInputData action,
                               final Users users, final Shows shows) {
        shows.addView(action.getTitle());
        users.addView(action.getUsername(), action.getTitle());
        return "success -> " + action.getTitle()
                + " was viewed with total views of "
                + users.getUsers().get(action.getUsername())
                .getHistory().get(action.getTitle());
    }

    private String executeRating(final ActionInputData action,
                                 final Users users, final Shows shows) {
        switch (users.addRating(action.getUsername(), action.getTitle(),
                action.getGrade(), action.getSeasonNumber())) {
            case 2:
                shows.addRating(action.getTitle(), action.getGrade(),
                        action.getSeasonNumber());
                return "success -> " + action.getTitle() + " was rated with "
                        + action.getGrade() + " by " + action.getUsername();
            case 0:
                return "error -> " + action.getTitle() + " is not seen";
            case 1:
                return "error -> " + action.getTitle() + " has been already rated";
            default:
                return "error -> invalid command";
        }
    }

}
