package execution;

import actor.Actors;
import entertainment.Shows;
import fileio.ActionInputData;
import common.Constants;
import fileio.Input;
import fileio.MovieInputData;
import fileio.SerialInputData;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import user.Users;
import java.util.ArrayList;
import java.util.List;



public class Commands {
    private ArrayList<ActionInputData> commands = new ArrayList<>();
    private Shows shows = new Shows();
    private Actors actors = new Actors();
    private Users users = new Users();

    /**
     * Se adauga actorii, show-urile si userii in liste.
     */
    public Commands(final Input input) {
        shows.addShows(input.getMovies(), input.getSerials());
        for (MovieInputData video : input.getMovies()) {
            shows.getShowsArray().add(video.getTitle());
        }
        for (SerialInputData serial : input.getSerials()) {
            shows.getShowsArray().add(serial.getTitle());
        }
        actors.addActors(input.getActors());
        users.addUsers(input.getUsers(), shows);
    }

    /**
     * Se adauga toate comenzile intr-o lista.
     */
    public void addCommands(final List<ActionInputData> actionInputData) {
        commands.addAll(actionInputData);
    }

    /**
     * Se genereaza json-ul cerut.
     */
    public final JSONArray executeCommands(final JSONArray arrayResult) {
        String solution;
        Command command = new Command();
        Query query = new Query();
        Recommendation recommendation = new Recommendation();

        for (ActionInputData action : commands) {
            solution = switch (action.getActionType()) {
                case Constants.COMMAND -> command.execute(users, shows, action);
                case Constants.QUERY -> query.execute(users,
                        actors, shows, action);
                case Constants.RECOMMENDATION -> recommendation.execute(users,
                        shows, action);
                default -> "Wrong input";
            };
            JSONObject tmp = new JSONObject();
            tmp.put("id", action.getActionId());
            tmp.put("message", solution);

            arrayResult.add(tmp);
        }
        return arrayResult;
    }
}
