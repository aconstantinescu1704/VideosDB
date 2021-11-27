package actions;

import DB.ActorsDatabase;
import DB.MovieDatabase;
import DB.ShowDatabase;
import DB.UsersDatabase;
import actor.Actor;

import audience.User;
import entertainment.Movie;
import entertainment.Show;
import entertainment.Video;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Query {

    /**
     *
     * @param actorsDataBase
     * @param movieDataBase
     * @param showDataBase
     * @param number
     * @param sortype
     * @return
     */
    public static String averageActors(final ActorsDatabase actorsDataBase,
                                       final MovieDatabase movieDataBase, final ShowDatabase showDataBase,
                                       final int number, final String sortype) {
        String result;
        final ArrayList<Actor> actorsRated = new ArrayList<>();

        for (var actors : actorsDataBase.getActors()) {
            actors.setRatingFilmographyActor(movieDataBase, showDataBase);
            if (!Double.isNaN(actors.getRatingFilmographyActor())) {
                actorsRated.add(actors);
            }
        }
        ActorsDatabase.sortAlphabetically(actorsRated, sortype);
        if (sortype.equals("asc")) {
            actorsRated.sort(Comparator.comparingDouble(Actor::getRatingFilmographyActor));
        } else {
            actorsRated.sort((actor1, actor2) -> Double.compare(actor2.getRatingFilmographyActor(),
                    actor1.getRatingFilmographyActor()));
        }

        result = "Query result: [";
        int validNumber = Math.min(number, actorsRated.size());
        for (int i = 0; i <= validNumber - 2; i++) {
            result = result
                    + actorsRated.get(i).getName()
                    + ", ";
        }
        if (validNumber > 0) {
            result = result
                    + actorsRated.get(validNumber - 1).getName()
                    + "]";
        } else {
            result = result + "]";
        }
        return result;
    }

    /**
     *
     * @param actorsDataBase
     * @param awards
     * @param sortype
     * @return
     */
    public static String awardsActors(final ActorsDatabase actorsDataBase,
                                      final List<String> awards, final String sortype) {

        String result;
        Map<String, Integer> awardsActorsTask = actorsDataBase.awardedActorsTotal(awards);

        List<Map.Entry<String, Integer>> listAwards =
                new LinkedList<>(awardsActorsTask.entrySet());

        // Sort the list
        if (sortype.equals("asc")) {
            listAwards.sort((a1, a2) -> (a1.getKey()).compareTo(a2.getKey()));
            listAwards.sort((a1, a2) -> (a1.getValue()).compareTo(a2.getValue()));
        } else {
            listAwards.sort((a1, a2) -> (a2.getKey()).compareTo(a1.getKey()));
            listAwards.sort((a1, a2) -> (a2.getValue()).compareTo(a1.getValue()));
        }
        result = "Query result: [";
        int indx = 0;
        for (Map.Entry<String, Integer> pair : listAwards) {
            if (indx == listAwards.size() - 1) {
                result = result
                        + pair.getKey()
                        + "]";
            } else {
                result = result
                        + pair.getKey()
                        + ", ";
                indx++;
            }
        }
        if (listAwards.size() == 0) {
            result = result + "]";
        }
        return result;
    }

    /**
     *
     * @param actorsDataBase
     * @param words
     * @param sortype
     * @return
     */
    public static String filterDescription(final ActorsDatabase actorsDataBase,
                                           final List<String> words, final String sortype) {

        String result = "Query result: [";
        ArrayList<Actor> actorsWithDescription =
                actorsDataBase.actorsCorrectDescription(words);


        ActorsDatabase.sortAlphabetically(actorsWithDescription, sortype);

        for (int i = 0; i < actorsWithDescription.size() - 1; i++) {
            result = result
                    + actorsWithDescription.get(i).getName()
                    + ", ";
        }
        if (actorsWithDescription.isEmpty()) {
            result = result + "]";
        } else {
            result = result
                    + actorsWithDescription.get(actorsWithDescription.size() - 1).getName()
                    + "]";
        }
        return result;
    }

    /**
     *
     * @param movieDataBase
     * @param sortype
     * @param number
     * @param filters
     * @return
     */
    public static String ratingMovies(final MovieDatabase movieDataBase,
                                      final String sortype, final int number,
                                      final List<List<String>> filters) {

        String result;
        ArrayList<Movie> moviesOrderedByRating = new ArrayList<>();
        for (var movies : movieDataBase.getMovies()) {
            movies.setRatingTotal();
            if (movies.getRatingTotal() > 0 && movies.checkFiltersMatch(filters)) {
                moviesOrderedByRating.add(movies);
            }
        }
        Movie.sortAlphabeticallyMovie(moviesOrderedByRating, sortype);
        if (sortype.equals("asc")) {
            moviesOrderedByRating.sort((movie1, movie2) ->
                    Double.compare(movie1.getRatingTotal(), movie2.getRatingTotal()));
        } else {
            moviesOrderedByRating.sort((movie1, movie2) ->
                    Double.compare(movie2.getRatingTotal(), movie1.getRatingTotal()));
        }

        result = "Query result: [";
        int validNumber = Math.min(number, moviesOrderedByRating.size());
        for (int i = 0; i <= validNumber - 2; i++) {
            result = result
                    + moviesOrderedByRating.get(i).getName()
                    + ", ";
        }
        if (validNumber > 0) {
            result = result
                    + moviesOrderedByRating.get(validNumber - 1).getName()
                    + "]";
        } else {
            result = result + "]";
        }
        return result;
    }

    /**
     *
     * @param showDataBase
     * @param sortype
     * @param number
     * @param filters
     * @return
     */
    public static String ratingShows(final ShowDatabase showDataBase,
                                     final String sortype, final int number,
                                     final List<List<String>> filters) {

        String result;
        ArrayList<Show> showsOrderedByRating = new ArrayList<>();
        for (var shows : showDataBase.getSerials()) {
            shows.setRatingTotal();
            if (shows.getRatingTotal() > 0 && shows.checkFiltersMatch(filters)) {
                showsOrderedByRating.add(shows);
            }
        }
        Show.sortAlphabeticallyShow(showsOrderedByRating, sortype);
        if (sortype.equals("asc")) {
            showsOrderedByRating.sort((show1, show2) ->
                    Double.compare(show1.getRatingTotal(),
                            show2.getRatingTotal()));
        } else {
            showsOrderedByRating.sort((show1, show2) ->
                    Double.compare(show2.getRatingTotal(),
                            show1.getRatingTotal()));
        }

        result = "Query result: [";
        int validNumber = Math.min(number, showsOrderedByRating.size());
        for (int i = 0; i <= validNumber - 2; i++) {
            result = result
                    + showsOrderedByRating.get(i).getName()
                    + ", ";
        }
        if (validNumber > 0) {
            result = result + showsOrderedByRating.get(validNumber - 1).getName() + "]";
        } else {
            result = result + "]";
        }
        return result;
    }

    /**
     *
     * @param movieDataBase
     * @param sortype
     * @param number
     * @param filters
     * @param usersDataBase
     * @param objectType
     * @param showDataBase
     * @return
     */
    public static String favouriteMovie(final MovieDatabase movieDataBase,
                                        final String sortype, final int number,
                                        final List<List<String>> filters,
                                        final UsersDatabase usersDataBase,
                                        final String objectType, final ShowDatabase showDataBase) {

        String result;
        ArrayList<Video> favouriteVideos = new ArrayList<>();

        if ((objectType).equals("movies")) {
            for (var movies : movieDataBase.getMovies()) {
                movies.setFavoriteNumberUsers(usersDataBase);
                if (movies.getFavoriteNumberUsers() > 0 && movies.checkFiltersMatch(filters)) {
                    favouriteVideos.add(movies);
                }
            }
        } else {
            for (var shows : showDataBase.getSerials()) {
                shows.setFavoriteNumberUsers(usersDataBase);
                if (shows.getFavoriteNumberUsers() > 0 && shows.checkFiltersMatch(filters)) {
                    favouriteVideos.add(shows);
                }
            }
        }
        Video.sortAlphabetically(favouriteVideos, sortype);
        if (sortype.equals("asc")) {
            favouriteVideos.sort((video1, video2) ->
                    Double.compare(video1.getFavoriteNumberUsers(),
                            video2.getFavoriteNumberUsers()));
        } else {
            favouriteVideos.sort((video1, video2) ->
                    Double.compare(video2.getFavoriteNumberUsers(),
                            video1.getFavoriteNumberUsers()));
        }

        result = "Query result: [";
        int validNumber = Math.min(number, favouriteVideos.size());
        for (int i = 0; i <= validNumber - 2; i++) {
            result = result
                    + favouriteVideos.get(i).getName()
                    + ", ";
        }
        if (validNumber > 0) {
            result = result
                    + favouriteVideos.get(validNumber - 1).getName()
                    + "]";
        } else {
            result = result + "]";
        }
        return result;
    }

    /**
     *
     * @param movieDataBase
     * @param sortype
     * @param number
     * @param filters
     * @return
     */
    public static String longestMovie(final MovieDatabase movieDataBase,
                                      final String sortype, final int number,
                                      final List<List<String>> filters) {
        String result;
        ArrayList<Movie> moviesOrderedByDuration = new ArrayList<>();
        for (var movies : movieDataBase.getMovies()) {
            if (movies.checkFiltersMatch(filters)) {
                moviesOrderedByDuration.add(movies);
            }
        }
        Movie.sortAlphabeticallyMovie(moviesOrderedByDuration, sortype);
        if (sortype.equals("asc")) {
            moviesOrderedByDuration.sort((movie1, movie2) ->
                    Double.compare(movie1.getDuration(), movie2.getDuration()));
        } else {
            moviesOrderedByDuration.sort((movie1, movie2) ->
                    Double.compare(movie2.getDuration(), movie1.getDuration()));
        }
        result = "Query result: [";
        int validNumber = Math.min(number, moviesOrderedByDuration.size());
        for (int i = 0; i <= validNumber - 2; i++) {
            result = result
                    + moviesOrderedByDuration.get(i).getName()
                    + ", ";
        }
        if (validNumber > 0) {
            result = result
                    + moviesOrderedByDuration.get(validNumber - 1).getName()
                    + "]";
        } else {
            result = result + "]";
        }
        return result;
    }

    /**
     *
     * @param showDataBase
     * @param sortype
     * @param number
     * @param filters
     * @return
     */
    public static String longestShow(final ShowDatabase showDataBase, final String sortype,
                                     final int number, final List<List<String>> filters) {
        String result;
        ArrayList<Show> showsOrderedByDuration = new ArrayList<>();
        for (var shows : showDataBase.getSerials()) {
            shows.setDuration();
            if (shows.checkFiltersMatch(filters)) {
                showsOrderedByDuration.add(shows);
            }
        }
        Show.sortAlphabeticallyShow(showsOrderedByDuration, sortype);
        if (sortype.equals("asc")) {
            showsOrderedByDuration.sort((show1, show2) ->
                    Double.compare(show1.getDuration(),
                            show2.getDuration()));
        } else {
            showsOrderedByDuration.sort((show1, show2) ->
                    Double.compare(show2.getDuration(),
                            show1.getDuration()));
        }
        result = "Query result: [";
        int validNumber = Math.min(number, showsOrderedByDuration.size());
        for (int i = 0; i <= validNumber - 2; i++) {
            result = result
                    + showsOrderedByDuration.get(i).getName()
                    + ", ";
        }
        if (validNumber > 0) {
            result = result
                    + showsOrderedByDuration.get(validNumber - 1).getName()
                    + "]";
        } else {
            result = result + "]";
        }
        return result;
    }

    /**
     *
     * @param movieDataBase
     * @param sortype
     * @param number
     * @param filters
     * @param usersDataBase
     * @param objectType
     * @param showDataBase
     * @return
     */
    public static String mostViewed(final MovieDatabase movieDataBase,
                                    final String sortype, final int number,
                                    final List<List<String>> filters,
                                    final UsersDatabase usersDataBase,
                                    final String objectType, final ShowDatabase showDataBase) {
        String result;
        ArrayList<Video> mostViewedVideos = new ArrayList<>();

        if ((objectType).equals("movies")) {
            for (var movies : movieDataBase.getMovies()) {
                movies.setNumberViews(usersDataBase);
                if (movies.getNumberViews() > 0 && movies.checkFiltersMatch(filters)) {
                    mostViewedVideos.add(movies);
                }
            }
        } else {
            for (var shows : showDataBase.getSerials()) {
                shows.setNumberViews(usersDataBase);
                if (shows.getNumberViews() > 0 && shows.checkFiltersMatch(filters)) {
                    mostViewedVideos.add(shows);
                }
            }
        }
        Video.sortAlphabetically(mostViewedVideos, sortype);
        if (sortype.equals("asc")) {
            mostViewedVideos.sort((video1, video2) ->
                    Double.compare(video1.getNumberViews(),
                            video2.getNumberViews()));
        } else {
            mostViewedVideos.sort((video1, video2) ->
                    Double.compare(video2.getNumberViews(),
                            video1.getNumberViews()));
        }

        result = "Query result: [";
        int validNumber = Math.min(number, mostViewedVideos.size());
        for (int i = 0; i <= validNumber - 2; i++) {
            result = result
                    + mostViewedVideos.get(i).getName()
                    + ", ";
        }
        if (validNumber > 0) {
            result = result
                    + mostViewedVideos.get(validNumber - 1).getName()
                    + "]";
        } else {
            result = result + "]";
        }
        return result;
    }

    /**
     *
     * @param movieDataBase
     * @param sortype
     * @param number
     * @param usersDataBase
     * @param showDataBase
     * @return
     */
    public static String ratingsUsers(final MovieDatabase movieDataBase,
                                      final String sortype, final int number,
                                      final UsersDatabase usersDataBase,
                                      final ShowDatabase showDataBase) {
        String result;
        ArrayList<User> usersWhoRated = new ArrayList<>();
        for (var user : usersDataBase.getUsers()) {
            user.setNumberRatings(movieDataBase, showDataBase);
            if (user.getNumberRatings() > 0) {
                usersWhoRated.add(user);
            }
        }

        if (sortype.equals("asc")) {
            usersWhoRated.sort((user1, user2) ->
                    user1.getUsername().compareTo(user2.getUsername()));
        } else {
            usersWhoRated.sort((user1, user2) ->
                    user2.getUsername().compareTo(user1.getUsername()));
        }

        if (sortype.equals("asc")) {
            usersWhoRated.sort((user1, user2) ->
                    Double.compare(user1.getNumberRatings(),
                            user2.getNumberRatings()));
        } else {
            usersWhoRated.sort((user1, user2) ->
                    Double.compare(user2.getNumberRatings(),
                            user1.getNumberRatings()));
        }
        result = "Query result: [";
        int validNumber = Math.min(number, usersWhoRated.size());
        for (int i = 0; i <= validNumber - 2; i++) {
            result = result
                    + usersWhoRated.get(i).getUsername()
                    + ", ";
        }
        if (validNumber > 0) {
            result = result
                    + usersWhoRated.get(validNumber - 1).getUsername()
                    + "]";
        } else {
            result = result + "]";
        }
        return result;
    }
}
