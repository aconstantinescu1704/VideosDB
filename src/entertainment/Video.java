package entertainment;

import DB.UsersDatabase;

import java.util.ArrayList;
import java.util.Comparator;
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

    /**
     */
    public abstract void setRatingTotal();

    /**
     * @return
     */
    public abstract double getRatingTotal();
}
