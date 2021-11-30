package actions;

import database.ActorsDatabase;
import database.MovieDatabase;
import database.ShowDatabase;
import database.UsersDatabase;
import database.VideoDatabase;
import fileio.ActionInputData;

public interface Action {

    /** action that executes for a given action input, the specified actions (commands,
     * queries or recommendations). The choice is made based on the given action characteristics
     *
     * @param action the input given through the json and converted to objects
     * @param users the databased where are stored all information about users
     * @param videos the databased where are stored all information about videos
     * @param movies the databased where are stored all information about movies
     * @param shows the databased where are stored all information about shows
     * @param actors the databased where are stored all information about actors
     * @return the string that should pe printed in the result json
     */
    static String execute(final ActionInputData action, final UsersDatabase users,
                          final VideoDatabase videos, final MovieDatabase movies,
                          final ShowDatabase shows, final ActorsDatabase actors) {

        String message = "";
        switch (action.getActionType()) {
            case "command" :
                switch (action.getType()) {
                    case "favorite" -> {
                        message = Command.commandFavorite(users,
                                action.getUsername(), action.getTitle());
                    }
                    case "view" -> {
                        message = Command.commandView(users,
                                action.getUsername(), action.getTitle());
                    }
                    case "rating" -> {
                        message = Command.commandRating(users,
                                movies, shows, action.getUsername(), action.getTitle(),
                                action.getGrade(), action.getSeasonNumber());
                    }
                    default -> throw new IllegalStateException("Unexpected value: "
                            + action.getType());
                }
                break;
            case "query":
                message = switch (action.getCriteria()) {
                    case "average" -> Query.averageActors(actors, videos,
                            action.getNumber(), action.getSortType());
                    case "awards" -> Query.awardsActors(actors, action.getFilters().get(3),
                            action.getSortType());
                    case "filter_description" -> Query.filterDescription(actors,
                            action.getFilters().get(2), action.getSortType());
                    case "ratings" -> switch ((action.getObjectType())) {
                        case "movies" -> Query.ratingMovies(movies, action.getSortType(),
                                action.getNumber(), action.getFilters());
                        case "shows" -> Query.ratingShows(shows, action.getSortType(),
                                action.getNumber(), action.getFilters());
                        default -> message;
                    };
                    case "favorite" -> Query.favouriteMovie(movies, action.getSortType(),
                            action.getNumber(), action.getFilters(), users,
                            action.getObjectType(), shows);
                    case "favourite" -> Query.favouriteMovie(movies, action.getSortType(),
                            action.getNumber(), action.getFilters(), users,
                            action.getObjectType(), shows);
                    case "longest" -> switch ((action.getObjectType())) {
                        case "movies" -> Query.longestMovie(movies, action.getSortType(),
                                action.getNumber(), action.getFilters());
                        case "shows" -> Query.longestShow(shows, action.getSortType(),
                                action.getNumber(), action.getFilters());
                        default -> message;
                    };
                    case "most_viewed" -> Query.mostViewed(movies, action.getSortType(),
                            action.getNumber(), action.getFilters(), users,
                            action.getObjectType(), shows);
                    case "num_ratings" -> Query.ratingsUsers(movies, action.getSortType(),
                            action.getNumber(), users, shows);
                    default -> message;
                };
                break;
            case "recommendation" :
                message = switch ((action.getType())) {
                    case "standard" -> Recommendation.standardRecommendation(users,
                            videos, action.getUsername());
                    case "best_unseen" -> Recommendation.bestUnseen(users,
                            movies, shows, action.getUsername());
                    case "popular" -> Recommendation.popularRecommendation(users,
                            videos, action.getUsername());
                    case "favorite" -> Recommendation.favoriteRecommendation(users,
                            videos, action.getUsername());
                    case "search" -> Recommendation.searchRecommendation(users,
                            videos, action.getUsername(),
                            action.getGenre());
                    default -> message;
                };
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action.getActionType());
        }

        return message;
    }
}
