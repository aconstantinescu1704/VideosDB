package DB;

import audience.User;
import fileio.UserInputData;

import java.util.ArrayList;
import java.util.List;

public class UsersDatabase {

    private final List<User> users;

    public UsersDatabase(final List<UserInputData> users) {
        List<User> list = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            User util = new User(users.get(i).getUsername(),
                    users.get(i).getSubscriptionType(), users.get(i).getHistory(),
                    users.get(i).getFavoriteMovies());

            list.add(util);
        }
        this.users = list;
    }

    public final List<User> getUsers() {
        return users;
    }
}
