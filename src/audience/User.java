package audience;

import DB.MovieDatabase;
import DB.ShowDatabase;

import java.util.ArrayList;
import java.util.Map;

public class User {

    private final String username;
    private final String subscriptionType;
    private final Map<String, Integer> history;
    private final ArrayList<String> favoriteMovies;

    public User(final String username, final String subscriptionType,
                final Map<String, Integer> history,
                final ArrayList<String> favoriteMovies) {
        this.username = username;
        this.subscriptionType = subscriptionType;
        this.favoriteMovies = favoriteMovies;
        this.history = history;
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



}
