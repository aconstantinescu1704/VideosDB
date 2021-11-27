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
            case "recommendation" :
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + action.getActionType());
        }

        return message;
    }
}
