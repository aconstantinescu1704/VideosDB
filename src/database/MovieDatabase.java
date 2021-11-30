package database;

import entertainment.Movie;
import fileio.MovieInputData;

import java.util.ArrayList;
import java.util.List;

public class MovieDatabase {

    private final ArrayList<Movie> movies = new ArrayList<>();

    public MovieDatabase(final List<MovieInputData> movies) {
        for (MovieInputData movie : movies) {
            Movie util = new Movie(movie.getTitle(), movie.getCast(),
                    movie.getGenres(), movie.getYear(),
                    movie.getDuration());
            this.movies.add(util);
        }
    }

    public final ArrayList<Movie> getMovies() {
        return movies;
    }

    /** sorts method that sorts alphabetically a movie array based on the given sortType
     * @param movies the databased where are stored all information about movies
     * @param sortType the type of sort : ascending or descending
     */
    public static void sortAlphabeticallyMovie(final ArrayList<Movie> movies,
                                               final String sortType) {
        if (sortType.equals("asc")) {
            movies.sort((movie1, movie2) -> movie1.getName().compareTo(movie2.getName()));
        } else {
            movies.sort((movie1, movie2) -> movie2.getName().compareTo(movie1.getName()));
        }
    }

}
