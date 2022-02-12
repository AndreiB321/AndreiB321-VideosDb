package actor;

import fileio.ActorInputData;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Actors {
    private final ArrayList<Actor> actors = new ArrayList<>();

    public Actors() {
    }

    /**
     * Se adauga toti actorii in lista.
     */
    public void addActors(final List<ActorInputData> inputActors) {
        for (ActorInputData actor : inputActors) {
            this.actors.add(new Actor(actor.getName(),
                    actor.getCareerDescription(),
                    actor.getFilmography(),
                    actor.getAwards()));
        }
    }

    public final ArrayList<Actor> getActors() {
        return actors;
    }

    public final Comparator<Actor> getCompareRating() {
        return compareRating;
    }

    public final Comparator<Actor> getCompareAward() {
        return compareAward;
    }

    public final Comparator<Actor> getCompareName() {
        return compareName;
    }

    private Comparator<Actor> compareRating = (Actor o1, Actor o2) -> {
        if (o1.getRating() == o2.getRating()) {
            return o1.getName().compareTo(o2.getName());
        } else {
            return Double.compare(o1.getRating(), o2.getRating());
        }
    };

    private Comparator<Actor> compareAward = (Actor o1, Actor o2) -> {
        if (o1.getNumberAwards() == o2.getNumberAwards()) {
            return o1.getName().compareTo(o2.getName());
        } else {
            return o1.getNumberAwards() - o2.getNumberAwards();
        }
    };

    private Comparator<Actor> compareName =
            Comparator.comparing(Actor::getName);
}
