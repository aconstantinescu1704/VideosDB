package DB;

import entertainment.Movie;
import fileio.MovieInputData;

import java.util.ArrayList;
import java.util.List;

public class MovieDatabase {

    private final ArrayList<Movie> movies = new ArrayList<>();

    public MovieDatabase(final List<MovieInputData> movies) {
        for (int i = 0; i < movies.size(); i++) {
            Movie util = new Movie(movies.get(i).getTitle(), movies.get(i).getCast(),
                    movies.get(i).getGenres(), movies.get(i).getYear(),
                    movies.get(i).getDuration());
            this.movies.add(util);
        }
    }

    public final ArrayList<Movie> getMovies() {
        return movies;
    }


}
