package actions;

import DB.MovieDatabase;
import DB.ShowDatabase;
import DB.UsersDatabase;

import java.util.Map;

public abstract class Command {


    public static String commandFavorite(final UsersDatabase usersDataBase, final String username,
                                         final String title) {
        String result = new String();
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

    public static String commandView(final UsersDatabase usersDataBase, final String username,
                                     final String title) {
        String result = new String();

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

    public static String commandRating(final UsersDatabase usersDataBase,
                                       final MovieDatabase movieDataBase,
                                       final ShowDatabase showDataBase, final String username,
                                       final String title, final double grade, final int seasonNumber) {
        String result = new String();
        boolean viewed = true;

        for (var users : usersDataBase.getUsers()) {
            if ((users.getUsername()).equals(username)) {
                if (!users.getHistory().containsKey(title)) {
                    viewed = false;
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
                    Double rating = shows.setRatingShow(username);
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
