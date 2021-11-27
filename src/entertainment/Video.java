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
     *
     * @param filters
     * @return
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

    /**
     *
     * @param usersDataBase
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

    /**
     *
     * @param usersDataBase
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
     *
     * @param videos
     * @param sortype
     */
    public static void sortAlphabetically(final ArrayList<Video> videos,
                                          final String sortype) {
        if (sortype.equals("asc")) {
            videos.sort(new Comparator<>() {
                @Override
                public int compare(final Video video1, final Video video2) {
                    return video1.getName().compareTo(video2.getName());
                }
            });
        } else {
            videos.sort(new Comparator<>() {
                @Override
                public int compare(final Video video1, final Video video2) {
                    return video2.getName().compareTo(video1.getName());
                }
            });
        }
    }

    /**
     */
    public abstract void setRatingTotal();

    /**
     * @return
     */
    public abstract double getRatingTotal();
}
