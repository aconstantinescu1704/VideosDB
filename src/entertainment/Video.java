package entertainment;

import database.UsersDatabase;

import java.util.ArrayList;
import java.util.List;

public abstract class Video {

    private final String name;
    private final int year;
    private final ArrayList<String> genres;
    private int favoriteNumberUsers;
    private int numberViews;


    public Video(final String title, final int year, final ArrayList<String> genres) {
        this.name = title;
        this.year = year;
        this.genres = genres;
        this.favoriteNumberUsers = 0;
        this.numberViews = 0;
    }

    public final String getName() {
        return name;
    }

    public final int getYear() {
        return year;
    }

    public final ArrayList<String> getGenres() {
        return genres;
    }

    /** method that checks if the required filters are respected by the video's characteristics
     * @param filters a list of lists of given filters
     * @return a boolean that expresses whether the filters were matched
     */
    public final boolean checkFiltersMatch(final List<List<String>> filters) {

        if (filters.get(0).get(0) != null) {
            int anFiltru = Integer.parseInt(filters.get(0).get(0));
            if (anFiltru != year) {
                return false;
            }
        }
        if (filters.get(1).get(0) != null) {
            for (var genre : filters.get(1)) {
                if (!genres.contains(genre)) {
                    return false;
                }
            }
        }
        return true;
    }

    /** method that sets the number of views for a video based on the users' activity
     * @param usersDataBase the databased where are stored all information about users
     */
    public final void setNumberViews(final UsersDatabase usersDataBase) {
        for (var user : usersDataBase.getUsers()) {
            if (user.getHistory().containsKey(name)) {
                numberViews = numberViews + user.getHistory().get(name);
            }
        }
    }

    public final int getNumberViews() {
        return numberViews;
    }

    /** method that sets the number of appearances in users' favorite list
     * @param usersDataBase the databased where are stored all information about users
     */
    public final void setFavoriteNumberUsers(final UsersDatabase usersDataBase) {
        for (var users : usersDataBase.getUsers()) {
            if (users.getFavoriteMovies().contains(name)) {
                favoriteNumberUsers++;
            }
        }
    }

    public final int getFavoriteNumberUsers() {
        return favoriteNumberUsers;
    }

    /**
     *  method that needs to be implemented by all classes that extend video
     *  it must set the total rating of the video
     */
    public abstract void setRatingTotal();

    /**
     *  method that needs to be implemented by all classes that extend video
     * @return the rating of a video
     */
    public abstract double getRatingTotal();
}
