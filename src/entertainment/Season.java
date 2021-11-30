package entertainment;

import java.util.HashMap;
import java.util.Map;

/**
 * Information about a season of a tv show
 * <p>
 * DO NOT MODIFY
 */
public final class Season {

    private final int currentSeason;
    private final int duration;
    private final Map<String, Double> ratingsUsers = new HashMap<>();
    private Double ratingTotal = 0.0;

    public Season(final int currentSeason, final int duration) {
        this.currentSeason = currentSeason;
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public Map<String, Double> getRatingsUsers() {
        return ratingsUsers;
    }

    /**
     * method that sets the total rating of a season based on all ratings given by users
     * the total rating is calculated as the average sum of all users rating
     */
    public void setRatingTotal() {
        ratingTotal = 0.0;
        for (Map.Entry<String, Double> entry : ratingsUsers.entrySet()) {
            ratingTotal = ratingTotal + entry.getValue();
        }
        if (ratingsUsers.size() > 0) {
            ratingTotal = ratingTotal / ratingsUsers.size();
        }
    }

    public Double getRatingTotal() {
        return ratingTotal;
    }

}

