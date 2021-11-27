package entertainment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Show extends Video {

    private final ArrayList<String> cast;
    private final int numberOfSeasons;
    private final ArrayList<Season> seasons;
    private final Map<String, Double> ratingsUsers = new HashMap<>();
    private double ratingTotal = 0.0;
    private int duration;

    public Show(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, genres);
        this.cast = cast;
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
        this.duration = 0;
    }

    public final ArrayList<String> getCast() {
        return cast;
    }

    public final int getNumberSeason() {
        return numberOfSeasons;
    }

    public final ArrayList<Season> getSeasons() {
        return seasons;
    }

    /**
     *
     * @return
     */
    public final Map<String, Double> getRatingsUsers() {
        return ratingsUsers;
    }

    /**
     *
     * @param user
     * @return
     */
    public final Double setRatingShow(final String user) {

        Double rating = 0.0;
        int contor = 0;
        for (var season : this.seasons) {
            if (season.getRatingsUsers().containsKey(user)) {
                contor++;
                rating += season.getRatingsUsers().get(user);
            }
        }
        if (contor == 1) {
            ratingsUsers.put(user, rating);
            return rating;
        }
        rating = rating / contor;
        ratingsUsers.replace(user, rating);
        return rating;
    }

    /**
     *
     */
    public final void setRatingTotal() {
        ratingTotal = 0;
        for (var season : seasons) {
            season.setRatingTotal();
            if (season.getRatingTotal() > 0) {
                ratingTotal = ratingTotal + season.getRatingTotal();
            }
        }
        ratingTotal = ratingTotal / numberOfSeasons;
    }

    public final int getDuration() {
        return duration;
    }
    public final double getRatingTotal() {
        return ratingTotal;
    }


}


