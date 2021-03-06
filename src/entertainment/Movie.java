package entertainment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Movie extends Video {

    private final ArrayList<String> actors;
    private final int duration;
    private double ratingTotal = 0.0;
    /*
     hashmap that maps the username with his rating for the movie
     */
    private final Map<String, Double> ratingsUsers = new HashMap<>();

    public Movie(final String title, final ArrayList<String> cast,
                 final ArrayList<String> genres, final int year,
                 final int duration) {
        super(title, year, genres);
        this.actors = cast;
        this.duration = duration;
    }

    public final ArrayList<String> getActors() {
        return actors;
    }
    public final int getDuration() {
        return duration;
    }
    public final Map<String, Double> getRatingsUsers() {
        return ratingsUsers;
    }

    /**
     * method that sets the total rating of a movie based on all ratings given by users
     * the total rating is calculated as the average sum of all users rating
     */
    public final void setRatingTotal() {
        ratingTotal = 0;
        for (Map.Entry<String, Double> entry : ratingsUsers.entrySet()) {
            ratingTotal = ratingTotal + entry.getValue();
        }
        if (ratingsUsers.size() > 0) {
            ratingTotal = ratingTotal / ratingsUsers.size();
        }
    }
    public final double getRatingTotal() {
        return ratingTotal;
    }


}
