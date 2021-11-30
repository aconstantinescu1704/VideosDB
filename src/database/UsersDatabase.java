package database;

import audience.User;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;

public class UsersDatabase {

    private final List<User> users;

    public UsersDatabase(final List<UserInputData> users) {
        List<User> list = new ArrayList<>();

        for (UserInputData user : users) {
            User util = new User(user.getUsername(),
                    user.getSubscriptionType(), user.getHistory(),
                    user.getFavoriteMovies());

            list.add(util);
        }
        this.users = list;
    }

    public final List<User> getUsers() {
        return users;
    }
}
