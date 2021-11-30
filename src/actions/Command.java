package actions;

import database.MovieDatabase;
import database.ShowDatabase;
import database.UsersDatabase;

import java.util.Map;

public abstract class Command {

    /** method that adds a movie to the favorite list of a user if the movies was viewed or
     * if the movie is not already in the list
     * @param usersDataBase the databased where are stored all information about users
     * @param username the user's username for which we are doing the command
     * @param title the video's title we want to add to favorite list
     * @return the string that should pe printed in the result json
     */
    public static String commandFavorite(final UsersDatabase usersDataBase, final String username,
                                         final String title) {
        String result = "";
        boolean gasit = false;

        for (var users : usersDataBase.getUsers()) {
            if ((users.getUsername()).equals(username)) {
                if (users.getFavoriteMovies().contains(title)) {
                    result = "error -> "
                            + title
                            + " is already in favourite list";
                } else {
                    for (Map.Entry<String, Integer> entry : users.getHistory().entrySet()) {
                        if ((entry.getKey()).equals(title)) {
                            result = "success -> "
                                    + title
                                    + " was added as favourite";
                            users.getFavoriteMovies().add(title);
                            gasit = true;
                        }
                    }
                    if (!gasit) {
                        result = "error -> " + title + " is not seen";
                    }
                }
            }
        }
        return result;
    }

    /** method that views a video or increases the number of views if the video was already seen
     * @param usersDataBase the databased where are stored all information about users
     * @param  username user's username for which we are doing the command
     * @param title he video's title we want to view
     * @return the string that should pe printed in the result json
     */
    public static String commandView(final UsersDatabase usersDataBase, final String username,
                                     final String title) {
        String result = "";

        for (var users : usersDataBase.getUsers()) {
            if ((users.getUsername()).equals(username)) {
                if (users.getHistory().containsKey(title)) {
                    Integer views = users.getHistory().get(title);
                    views++;
                    users.getHistory().replace(title, views);
                } else {
                    users.getHistory().put(title, 1);
                }
                result = "success -> "
                        + title
                        + " was viewed with total views of "
                        + users.getHistory().get(title);
            }
        }

        return result;
    }

    /** methods that gives  rating to a movie or show by a user
     * @param usersDataBase the databased where are stored all information about users
     * @param movieDataBase the databased where are stored all information about movies
     * @param showDataBase the databased where are stored all information about shows
     * @param username user's username for which we are doing the command
     * @param title the video's title we want to add to favorite list
     * @param grade the rating given
     * @param seasonNumber the season for which we are giving the rating
     * @return the string that should pe printed in the result json
     */
    public static String commandRating(final UsersDatabase usersDataBase,
                                       final MovieDatabase movieDataBase,
                                       final ShowDatabase showDataBase, final String username,
                                       final String title, final double grade,
                                       final int seasonNumber) {
        String result = "";

        for (var users : usersDataBase.getUsers()) {
            if ((users.getUsername()).equals(username)) {
                if (!users.getHistory().containsKey(title)) {
                    result = "error -> "
                            + title
                            + " is not seen";
                    return result;
                }
            }
        }

        for (var movies : movieDataBase.getMovies()) {
            if ((movies.getName()).equals(title)) {
                if (movies.getRatingsUsers().containsKey(username)) {
                    result = "error -> "
                            + title
                            + " has been already rated";
                } else {
                    result = "success -> "
                            + title
                            + " was rated with "
                            + grade
                            + " by "
                            + username;
                    movies.getRatingsUsers().put(username, grade);
                }
            }
        }

        for (var shows : showDataBase.getSerials()) {
            if ((shows.getName()).equals(title)) {
                if (shows.getSeasons().get(seasonNumber - 1).getRatingsUsers().
                        containsKey(username)) {
                    result = "error -> "
                            + title
                            + " has been already rated";
                } else {
                    shows.getSeasons().get(seasonNumber - 1).getRatingsUsers().put(username, grade);
                    shows.setRatingShow(username);
                    result = "success -> "
                            + title
                            + " was rated with "
                            + grade
                            + " by "
                            + username;
                }
            }
        }

        return result;

    }

}
