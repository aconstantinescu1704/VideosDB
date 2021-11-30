package database;

import actor.Actor;
import actor.ActorsAwards;
import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActorsDatabase {
    private final ArrayList<Actor> actors = new ArrayList<>();

    public ActorsDatabase(final List<ActorInputData> actors) {
        for (ActorInputData actor : actors) {
            Actor util = new Actor(actor.getName(),
                    actor.getCareerDescription(),
                    actor.getFilmography(), actor.getAwards());

            this.actors.add(util);
        }
    }

    public final ArrayList<Actor> getActors() {
        return actors;
    }

    /** method that sorts alphabetically an actors array based on the given sortType
     * @param actors actors the databased where are stored all information about actors
     * @param sortType the type of sort : ascending or descending
     */
    public static void sortAlphabetically(final ArrayList<Actor> actors, final String sortType) {
        if (sortType.equals("asc")) {
            actors.sort((actor1, actor2) -> actor1.getName().compareTo(actor2.getName()));
        } else {
            actors.sort((actor1, actor2) -> actor2.getName().compareTo(actor1.getName()));
        }
    }

    /** method that returns all actors that have the specified awards and their total
     * amount of prices won
     * the result is stored in a map(name of actor, nr of prices)
     * @param awards the array of awards that is specified in the input
     * @return a hash map that maps the name of the valid actor and the number of prices won
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

    /** method that returns all actors that have the specified words in their career description
     * @param words the array of words which is given in the action's input
     * @return an array with all actors with the requested type of description
     */
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
