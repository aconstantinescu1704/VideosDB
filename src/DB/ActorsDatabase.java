package DB;

import actor.Actor;
import actor.ActorsAwards;
import fileio.ActorInputData;

import java.util.*;

public class ActorsDatabase {
    private final ArrayList<Actor> actors = new ArrayList<>();

    public ActorsDatabase(final List<ActorInputData> actors) {
        for (int i = 0; i < actors.size(); i++) {
            Actor util = new Actor(actors.get(i).getName(),
                    actors.get(i).getCareerDescription(),
                    actors.get(i).getFilmography(), actors.get(i).getAwards());

            this.actors.add(util);
        }
    }
    public final ArrayList<Actor> getActors() {
        return actors;
    }

}
