package actions;

import DB.*;
import fileio.ActionInputData;

public interface Action {


    static String execute(final ActionInputData action, final UsersDatabase users,
                          final VideoDatabase videos, final MovieDatabase movies,
                          final ShowDatabase shows, final ActorsDatabase actors) {

        String message = new String();
        switch (action.getActionType()) {
            case "command" :
                switch (action.getType()) {
                    case "favorite" :
                        message = Command.commandFavorite(users,
                                action.getUsername(), action.getTitle());
                        break;
                    case "view" :
                        message = Command.commandView(users,
                                action.getUsername(), action.getTitle());
                        break;
                    case "rating" :
                        message = Command.commandRating(users,
                                movies, shows, action.getUsername(), action.getTitle(),
                                action.getGrade(), action.getSeasonNumber());
                        break;

                }
                break;
            case "query":
                switch (action.getCriteria()) {
                    case "average" :
                        message = Query.averageActors(actors, movies, shows,
                                action.getNumber(), action.getSortType());
                        break;
                    case "awards" :
                        message = Query.awardsActors(actors, action.getFilters().get(3),
                                action.getSortType());
                        break;
                    case "filter_description" :
                        message = Query.filterDescription(actors, action.getFilters().get(2),
                                action.getSortType());
                        break;
                    case "ratings" :
                        switch((action.getObjectType())) {
                            case "movies" :
                                message = Query.ratingMovies(movies, action.getSortType(),
                                        action.getNumber(), action.getFilters());
                                break;
                            case "shows" :
                                message = Query.ratingShows(shows, action.getSortType(),
                                        action.getNumber(), action.getFilters());
                                break;
                        }
                        break;
                    case "favorite" :
                        message = Query.favouriteMovie(movies, action.getSortType(),
                                action.getNumber(), action.getFilters(), users,
                                action.getObjectType(), shows);
                        break;
                    case "favourite" :
                        message = Query.favouriteMovie(movies, action.getSortType(),
                                action.getNumber(), action.getFilters(), users,
                                action.getObjectType(), shows);
                        break;
                    case  "longest"  :
                        switch((action.getObjectType())) {
                            case "movies" :
                                message = Query.longestMovie(movies, action.getSortType(),
                                        action.getNumber(), action.getFilters());
                                break;
                            case "shows" :
                                message = Query.longestShow(shows, action.getSortType(),
                                        action.getNumber(), action.getFilters());
                                break;
                        }
                        break;
                    case "most_viewed" :
                        message = Query.mostViewed(movies, action.getSortType(),
                                action.getNumber(), action.getFilters(), users,
                                action.getObjectType(), shows);
                        break;
                    case "num_ratings" :
                        message = Query.ratingsUsers(movies, action.getSortType(),
                                action.getNumber(), users, shows);
                        break;
                }
                break;
            case "recommendation" :
                switch ((action.getType())) {
                    case "standard" :
                        message = Recommendation.standardRecommendation(users,
                                videos, action.getUsername());
                        break;
                    case "best_unseen" :
                        message = Recommendation.bestUnseen(users,
                                movies, shows, action.getUsername());
                        break;
                    case "popular" :
                        message = Recommendation.popularRecommendation(users,
                                videos, action.getUsername());
                        break;
                    case "favorite" :
                        message = Recommendation.favoriteRecommendation(users,
                                videos, action.getUsername());
                        break;
                    case "search" :
                        message = Recommendation.searchRecommendation(users,
                                videos, action.getUsername(),
                                action.getGenre());
                        break;
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action.getActionType());
        }

        return message;
    }
}
