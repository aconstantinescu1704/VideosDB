package actor;

import database.VideoDatabase;

import java.util.ArrayList;
import java.util.Map;

public class Actor {

    private final String name;
    private final String careerDescription;
    private final ArrayList<String> filmography;
    private final Map<ActorsAwards, Integer> awards;
    private Double ratingFilmographyActor = 0.0;

    public Actor(final String name, final String careerDescription,
                 final ArrayList<String> filmography,
                 final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    public final String getName() {
        return name;
    }

    public final ArrayList<String> getFilmography() {
        return filmography;
    }

    public final Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }

    public final String getCareerDescription() {
        return careerDescription;
    }

    /** method that based on the previous commands sets the rating of an actor
     * the rating is calculated as the average sum of all his rated videos
     * @param videoDatabase the databased where are stored all information about videos
     */
    public final void setRatingFilmographyActor(final VideoDatabase videoDatabase) {

        ratingFilmographyActor = 0.0;
        int nrRatedMovies = 0;
        int nrRatedShows = 0;
        for (var film : filmography) {
            for (var movies : videoDatabase.getVideos()) {
                movies.setRatingTotal();
                if ((movies.getName()).equals(film)) {
                    if (movies.getRatingTotal() > 0) {
                        ratingFilmographyActor = ratingFilmographyActor + movies.getRatingTotal();
                        nrRatedMovies++;
                    }
                }
            }
        }

        ratingFilmographyActor = ratingFilmographyActor / (nrRatedMovies + nrRatedShows);
    }

    public final Double getRatingFilmographyActor() {
        return ratingFilmographyActor;
    }

}
