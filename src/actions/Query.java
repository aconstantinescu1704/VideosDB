package actions;

import database.*;
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

    /** the method sorts actors by their rating
     * Firstly, we set all actors' filmography ratings based on the ratings given by all users
     * to all the videos. After that we sort the actors who were rated by the given rating.
     * The second sorting criteria is the alphabetical order
     * @param actorsDataBase the database in which all information about actors are stored
     * @param videoDatabase the database in which all information about videos are stored
     * @param number maximum number of actors that should appear in the string
     * @param sortType the type of sort : ascending vs descending
     * @return the string that should pe printed in the result json
     */
    public static String averageActors(final ActorsDatabase actorsDataBase,
                                       final VideoDatabase videoDatabase,
                                       final int number, final String sortType) {
        StringBuilder result;
        final ArrayList<Actor> actorsRated = new ArrayList<>();

        for (var actors : actorsDataBase.getActors()) {
            actors.setRatingFilmographyActor(videoDatabase);
            if (!Double.isNaN(actors.getRatingFilmographyActor())) {
                actorsRated.add(actors);
            }
        }
        ActorsDatabase.sortAlphabetically(actorsRated, sortType);
        if (sortType.equals("asc")) {
            actorsRated.sort(Comparator.comparingDouble(Actor::getRatingFilmographyActor));
        } else {
            actorsRated.sort((actor1, actor2) -> Double.compare(actor2.getRatingFilmographyActor(),
                    actor1.getRatingFilmographyActor()));
        }

        result = new StringBuilder("Query result: [");
        int validNumber = Math.min(number, actorsRated.size());
        for (int i = 0; i <= validNumber - 2; i++) {
            result.append(actorsRated.get(i).getName()).append(", ");
        }
        if (validNumber > 0) {
            result.append(actorsRated.get(validNumber - 1).getName()).append("]");
        } else {
            result.append("]");
        }
        return result.toString();
    }

    /** the method sorts actors by the number of awards they have if and only if they have the
     * specified awards
     * The name of the actor and the number of awards for the admitted actors are stored in a
     * hashmap which is sorted by key and then by value
     * @param actorsDataBase the database in which all information about actors are stored
     * @param awards the array of awards that is specified in the input
     * @param sortType the type of sort : ascending or descending
     * @return the string that should pe printed in the result json
     */
    public static String awardsActors(final ActorsDatabase actorsDataBase,
                                      final List<String> awards, final String sortType) {

        StringBuilder result;
        Map<String, Integer> awardsActorsTask = actorsDataBase.awardedActorsTotal(awards);

        List<Map.Entry<String, Integer>> listAwards =
                new LinkedList<>(awardsActorsTask.entrySet());

        // Sort the list
        if (sortType.equals("asc")) {
            listAwards.sort((a1, a2) -> (a1.getKey()).compareTo(a2.getKey()));
            listAwards.sort((a1, a2) -> (a1.getValue()).compareTo(a2.getValue()));
        } else {
            listAwards.sort((a1, a2) -> (a2.getKey()).compareTo(a1.getKey()));
            listAwards.sort((a1, a2) -> (a2.getValue()).compareTo(a1.getValue()));
        }
        result = new StringBuilder("Query result: [");
        int indx = 0;
        for (Map.Entry<String, Integer> pair : listAwards) {
            if (indx == listAwards.size() - 1) {
                result.append(pair.getKey()).append("]");
            } else {
                result.append(pair.getKey()).append(", ");
                indx++;
            }
        }
        if (listAwards.size() == 0) {
            result.append("]");
        }
        return result.toString();
    }

    /** the method sorts alphabetically all actors that have certain given words
     * in their career description
     * @param actorsDataBase the database in which all information about actors are stored
     * @param words the array of words which is given in the action's input
     * @param sortType the type of sort : ascending or descending
     * @return the string that should pe printed in the result json
     */
    public static String filterDescription(final ActorsDatabase actorsDataBase,
                                           final List<String> words, final String sortType) {

        StringBuilder result = new StringBuilder("Query result: [");
        ArrayList<Actor> actorsWithDescription =
                actorsDataBase.actorsCorrectDescription(words);


        ActorsDatabase.sortAlphabetically(actorsWithDescription, sortType);

        for (int i = 0; i < actorsWithDescription.size() - 1; i++) {
            result.append(actorsWithDescription.get(i).getName()).append(", ");
        }
        if (actorsWithDescription.isEmpty()) {
            result.append("]");
        } else {
            result.append(actorsWithDescription.get(actorsWithDescription.size() - 1).getName()).append("]");
        }
        return result.toString();
    }

    /** the method sorts N (@param number) movies by their rating
     * @param movieDataBase the database in which all information about movies are stored
     * @param sortype the type of sort : ascending or descending
     * @param number maximum number of movies that should appear in the string
     * @param filters a list of lists of characteristics that all sorted movies must have
     * @return the string that should pe printed in the result json
     */
    public static String ratingMovies(final MovieDatabase movieDataBase,
                                      final String sortype, final int number,
                                      final List<List<String>> filters) {

        StringBuilder result;
        ArrayList<Movie> moviesOrderedByRating = new ArrayList<>();
        for (var movies : movieDataBase.getMovies()) {
            movies.setRatingTotal();
            if (movies.getRatingTotal() > 0 && movies.checkFiltersMatch(filters)) {
                moviesOrderedByRating.add(movies);
            }
        }
        MovieDatabase.sortAlphabeticallyMovie(moviesOrderedByRating, sortype);
        if (sortype.equals("asc")) {
            moviesOrderedByRating.sort((movie1, movie2) ->
                    Double.compare(movie1.getRatingTotal(), movie2.getRatingTotal()));
        } else {
            moviesOrderedByRating.sort((movie1, movie2) ->
                    Double.compare(movie2.getRatingTotal(), movie1.getRatingTotal()));
        }

        result = new StringBuilder("Query result: [");
        int validNumber = Math.min(number, moviesOrderedByRating.size());
        for (int i = 0; i <= validNumber - 2; i++) {
            result.append(moviesOrderedByRating.get(i).getName()).append(", ");
        }
        if (validNumber > 0) {
            result.append(moviesOrderedByRating.get(validNumber - 1).getName()).append("]");
        } else {
            result.append("]");
        }
        return result.toString();
    }

    /** the method sorts N (@param number) shows by their rating
     * @param showDataBase the database in which all information about shows are stored
     * @param sortType the type of sort : ascending or descending
     * @param number maximum number of shows that should appear in the string
     * @param filters a list of lists of characteristics that all sorted shows must have
     * @return the string that should pe printed in the result json
     */
    public static String ratingShows(final ShowDatabase showDataBase,
                                     final String sortType, final int number,
                                     final List<List<String>> filters) {

        StringBuilder result;
        ArrayList<Show> showsOrderedByRating = new ArrayList<>();
        for (var shows : showDataBase.getSerials()) {
            shows.setRatingTotal();
            if (shows.getRatingTotal() > 0 && shows.checkFiltersMatch(filters)) {
                showsOrderedByRating.add(shows);
            }
        }
        ShowDatabase.sortAlphabeticallyShow(showsOrderedByRating, sortType);
        if (sortType.equals("asc")) {
            showsOrderedByRating.sort((show1, show2) ->
                    Double.compare(show1.getRatingTotal(), show2.getRatingTotal()));
        } else {
            showsOrderedByRating.sort((show1, show2) ->
                    Double.compare(show2.getRatingTotal(), show1.getRatingTotal()));
        }

        result = new StringBuilder("Query result: [");
        int validNumber = Math.min(number, showsOrderedByRating.size());
        for (int i = 0; i <= validNumber - 2; i++) {
            result.append(showsOrderedByRating.get(i).getName()).append(", ");
        }
        if (validNumber > 0) {
            result.append(showsOrderedByRating.get(validNumber - 1).getName()).append("]");
        } else {
            result.append("]");
        }
        return result.toString();
    }

    /** method that sorts all videos by the number of appearances in users' favorite list
     * @param movieDataBase the database in which all information about movies are stored
     * @param sortType the type of sort : ascending or descending
     * @param number maximum number of videos that should appear in the string
     * @param filters a list of lists of characteristics that all sorted videos must have
     * @param usersDataBase the database in which all information about users are stored
     * @param objectType the type of objects which are sorted : movies or shows
     * @param showDataBase the database in which all information about shows are stored
     * @return the string that should pe printed in the result json
     */
    public static String favouriteMovie(final MovieDatabase movieDataBase,
                                        final String sortType, final int number,
                                        final List<List<String>> filters,
                                        final UsersDatabase usersDataBase,
                                        final String objectType, final ShowDatabase showDataBase) {

        StringBuilder result;
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
        VideoDatabase.sortAlphabeticallyVideo(favouriteVideos, sortType);
        if (sortType.equals("asc")) {
            favouriteVideos.sort((video1, video2) ->
                    Double.compare(video1.getFavoriteNumberUsers(),
                            video2.getFavoriteNumberUsers()));
        } else {
            favouriteVideos.sort((video1, video2) ->
                    Double.compare(video2.getFavoriteNumberUsers(),
                            video1.getFavoriteNumberUsers()));
        }

        result = new StringBuilder("Query result: [");
        int validNumber = Math.min(number, favouriteVideos.size());
        for (int i = 0; i <= validNumber - 2; i++) {
            result.append(favouriteVideos.get(i).getName()).append(", ");
        }
        if (validNumber > 0) {
            result.append(favouriteVideos.get(validNumber - 1).getName()).append("]");
        } else {
            result.append("]");
        }
        return result.toString();
    }

    /** method that sorts all movies by duration
     * @param movieDataBase the database in which all information about movies are stored
     * @param sortType the type of sort : ascending or descending
     * @param number maximum number of videos that should appear in the string
     * @param filters a list of lists of characteristics that all sorted videos must have
     * @return the string that should pe printed in the result json
     */
    public static String longestMovie(final MovieDatabase movieDataBase,
                                      final String sortType, final int number,
                                      final List<List<String>> filters) {
        StringBuilder result;
        ArrayList<Movie> moviesOrderedByDuration = new ArrayList<>();
        for (var movies : movieDataBase.getMovies()) {
            if (movies.checkFiltersMatch(filters)) {
                moviesOrderedByDuration.add(movies);
            }
        }
        MovieDatabase.sortAlphabeticallyMovie(moviesOrderedByDuration, sortType);
        if (sortType.equals("asc")) {
            moviesOrderedByDuration.sort((movie1, movie2) ->
                    Double.compare(movie1.getDuration(), movie2.getDuration()));
        } else {
            moviesOrderedByDuration.sort((movie1, movie2) ->
                    Double.compare(movie2.getDuration(), movie1.getDuration()));
        }
        result = new StringBuilder("Query result: [");
        int validNumber = Math.min(number, moviesOrderedByDuration.size());
        for (int i = 0; i <= validNumber - 2; i++) {
            result.append(moviesOrderedByDuration.get(i).getName()).append(", ");
        }
        if (validNumber > 0) {
            result.append(moviesOrderedByDuration.get(validNumber - 1).getName()).append("]");
        } else {
            result.append("]");
        }
        return result.toString();
    }

    /** method that sorts all shows by duration
     *  before sorting the array we have to set the duration for each movie based on their seasons' duration
     * @param showDataBase the database in which all information about shows are stored
     * @param sortType sortType the type of sort : ascending or descending
     * @param number maximum number of videos that should appear in the string
     * @param filters a list of lists of characteristics that all sorted videos must have
     * @return the string that should pe printed in the result json
     */
    public static String longestShow(final ShowDatabase showDataBase,
                                     final String sortType,
                                     final int number, final List<List<String>> filters) {
        StringBuilder result;
        ArrayList<Show> showsOrderedByDuration = new ArrayList<>();
        for (var shows : showDataBase.getSerials()) {
            shows.setDuration();
            if (shows.checkFiltersMatch(filters)) {
                showsOrderedByDuration.add(shows);
            }
        }
        ShowDatabase.sortAlphabeticallyShow(showsOrderedByDuration, sortType);
        if (sortType.equals("asc")) {
            showsOrderedByDuration.sort((show1, show2) ->
                    Double.compare(show1.getDuration(), show2.getDuration()));
        } else {
            showsOrderedByDuration.sort((show1, show2) ->
                    Double.compare(show2.getDuration(), show1.getDuration()));
        }
        result = new StringBuilder("Query result: [");
        int validNumber = Math.min(number, showsOrderedByDuration.size());
        for (int i = 0; i <= validNumber - 2; i++) {
            result.append(showsOrderedByDuration.get(i).getName()).append(", ");
        }
        if (validNumber > 0) {
            result.append(showsOrderedByDuration.get(validNumber - 1).getName()).append("]");
        } else {
            result.append("]");
        }
        return result.toString();
    }

    /** methods that sorts all videos by the number of times they were viewed
     * before sorting we set the number of views for all videos (also counting previous actions)
     * @param movieDataBase the database in which all information about movies are stored
     * @param sortType sortType the type of sort : ascending or descending
     * @param number maximum number of videos that should appear in the string
     * @param filters a list of lists of characteristics that all sorted videos must have
     * @param usersDataBase the database in which all information about users are stored
     * @param objectType the type of objects which are sorted : movies or shows
     * @param showDataBase the database in which all information about shows are stored
     * @return the string that should pe printed in the result json
     */
    public static String mostViewed(final MovieDatabase movieDataBase,
                                    final String sortType, final int number,
                                    final List<List<String>> filters,
                                    final UsersDatabase usersDataBase,
                                    final String objectType, final ShowDatabase showDataBase) {
        StringBuilder result;
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
        VideoDatabase.sortAlphabeticallyVideo(mostViewedVideos, sortType);
        if (sortType.equals("asc")) {
            mostViewedVideos.sort((video1, video2) ->
                    Double.compare(video1.getNumberViews(), video2.getNumberViews()));
        } else {
            mostViewedVideos.sort((video1, video2) ->
                    Double.compare(video2.getNumberViews(), video1.getNumberViews()));
        }

        result = new StringBuilder("Query result: [");
        int validNumber = Math.min(number, mostViewedVideos.size());
        for (int i = 0; i <= validNumber - 2; i++) {
            result.append(mostViewedVideos.get(i).getName()).append(", ");
        }
        if (validNumber > 0) {
            result.append(mostViewedVideos.get(validNumber - 1).getName()).append("]");
        } else {
            result.append("]");
        }
        return result.toString();
    }

    /** method that sorts all users by the number of ratings they gave
     * @param movieDataBase the database in which all information about movies are stored
     * @param sortType sortType the type of sort : ascending or descending
     * @param number maximum number of videos that should appear in the string
     * @param usersDataBase the database in which all information about users are stored
     * @param showDataBase the database in which all information about shows are stored
     * @return the string that should pe printed in the result json
     */
    public static String ratingsUsers(final MovieDatabase movieDataBase,
                                      final String sortType, final int number,
                                      final UsersDatabase usersDataBase,
                                      final ShowDatabase showDataBase) {
        StringBuilder result;
        ArrayList<User> usersWhoRated = new ArrayList<>();
        for (var user : usersDataBase.getUsers()) {
            user.setNumberRatings(movieDataBase, showDataBase);
            if (user.getNumberRatings() > 0) {
                usersWhoRated.add(user);
            }
        }

        if (sortType.equals("asc")) {
            usersWhoRated.sort((user1, user2) ->
                    user1.getUsername().compareTo(user2.getUsername()));
        } else {
            usersWhoRated.sort((user1, user2) ->
                    user2.getUsername().compareTo(user1.getUsername()));
        }

        if (sortType.equals("asc")) {
            usersWhoRated.sort((user1, user2) ->
                    Double.compare(user1.getNumberRatings(), user2.getNumberRatings()));
        } else {
            usersWhoRated.sort((user1, user2) ->
                    Double.compare(user2.getNumberRatings(),
                            user1.getNumberRatings()));
        }
        result = new StringBuilder("Query result: [");
        int validNumber = Math.min(number, usersWhoRated.size());
        for (int i = 0; i <= validNumber - 2; i++) {
            result.append(usersWhoRated.get(i).getUsername()).append(", ");
        }
        if (validNumber > 0) {
            result.append(usersWhoRated.get(validNumber - 1).getUsername()).append("]");
        } else {
            result.append("]");
        }
        return result.toString();
    }
}
