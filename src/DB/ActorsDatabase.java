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

    /**
     *
     * @param actors
     * @param sortype
     */
    public static void sortAlphabetically(final ArrayList<Actor> actors, final String sortype) {
        if (sortype.equals("asc")) {
            actors.sort(new Comparator<>() {
                @Override
                public int compare(final Actor actor1, final Actor actor2) {
                    return actor1.getName().compareTo(actor2.getName());
                }
            });
        } else {
            actors.sort(new Comparator<>() {
                @Override
                public int compare(final Actor actor1, final Actor actor2) {
                    return actor2.getName().compareTo(actor1.getName());
                }
            });
        }
    }

    /**
     *
     * @param awards
     * @return
     */
    public final Map<String, Integer> awardedActorsTotal(final List<String> awards) {
        Map<String, Integer> awardsActorsTask = new HashMap<>();

        int nrAwards;
        boolean allRequiredAwards;

        for (var actor : actors) {
            nrAwards = 0;
            allRequiredAwards = true;
            for (var award : awards) {
                if (!actor.getAwards().containsKey(ActorsAwards.valueOf(award))) {
                    allRequiredAwards = false;
                }
            }
            if (allRequiredAwards) {
                for (var allAwards : actor.getAwards().entrySet()) {
                    nrAwards = nrAwards + allAwards.getValue();
                }

                awardsActorsTask.put(actor.getName(), nrAwards);
            }
        }
        return awardsActorsTask;
    }

    public final ArrayList<Actor> actorsCorrectDescription(final List<String> words) {
        ArrayList<Actor> actorsWithDescription = new ArrayList<>();
        boolean allWords;

        for (var actor : actors) {
            allWords = true;
            String descriptionCaseInsensitive = actor.getCareerDescription().toLowerCase();
            for (var word : words) {
                word = word.toLowerCase();
                String word1 = " " + word + " ";
                String word2 = " " + word + ".";
                String word3 = "," + word + " ";
                String word4 = " " + word + ",";
                String word5 = "-" + word + " ";

                if (!descriptionCaseInsensitive.contains(word1)
                        && !descriptionCaseInsensitive.contains(word2)
                        && !descriptionCaseInsensitive.contains(word3)
                        && !descriptionCaseInsensitive.contains(word4)
                        && !descriptionCaseInsensitive.contains(word5)) {
                    allWords = false;
                    break;
                }
            }
            if (allWords) {
                actorsWithDescription.add(actor);
            }
        }
        return  actorsWithDescription;
    }
}
