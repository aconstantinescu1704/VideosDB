package audience;

import database.MovieDatabase;
import database.ShowDatabase;

import java.util.ArrayList;
import java.util.Map;

public class User {

    private final String username;
    private final String subscriptionType;
    private final Map<String, Integer> history;
    private final ArrayList<String> favoriteMovies;
    private int numberRatings;

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
        this.numberRatings = 0;
    }

    public final String getUsername() {
        return username;
    }

    public final Map<String, Integer> getHistory() {
        return history;
    }

    public final String getSubscriptionType() {
        return subscriptionType;
    }

    public final ArrayList<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    /** method that sets the number of ratings given by a user based on previous commands
     * @param movieDataBase the databased where are stored all information about movies
     * @param showDataBase the databased where are stored all information about shows
     */
    public final void setNumberRatings(final MovieDatabase movieDataBase,
                                       final ShowDatabase showDataBase) {
        numberRatings = 0;
        for (var movie : movieDataBase.getMovies()) {
            if (movie.getRatingsUsers().containsKey(username)) {
                numberRatings++;
            }
        }
    }

    public final int getNumberRatings() {
        return numberRatings;
    }

}
