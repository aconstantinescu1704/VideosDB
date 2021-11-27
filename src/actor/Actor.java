package actor;

import DB.MovieDatabase;
import DB.ShowDatabase;

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

    /**
     * @param movieDataBase
     * @param showDataBase
     */
    public final void setRatingFilmographyActor(final MovieDatabase movieDataBase,
                                                final ShowDatabase showDataBase) {

        ratingFilmographyActor = 0.0;
        int nrRatedMovies = 0;
        int nrRatedShows = 0;
        for (var film : filmography) {
            for (var movies : movieDataBase.getMovies()) {
                movies.setRatingTotal();
                if ((movies.getName()).equals(film)) {
                    if (movies.getRatingTotal() > 0) {
                        ratingFilmographyActor = ratingFilmographyActor + movies.getRatingTotal();
                        nrRatedMovies++;
                    }

                }
            }
            for (var shows : showDataBase.getSerials()) {
                shows.setRatingTotal();
                if ((shows.getName()).equals(film)) {
                    if (shows.getRatingTotal() > 0) {
                        ratingFilmographyActor = ratingFilmographyActor + shows.getRatingTotal();
                        nrRatedShows++;
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
