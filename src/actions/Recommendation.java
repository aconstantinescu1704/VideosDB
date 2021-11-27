package actions;

import DB.MovieDatabase;
import DB.ShowDatabase;
import DB.UsersDatabase;
import DB.VideoDatabase;
import audience.User;
import entertainment.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class Recommendation {

    /**
     *
     * @param usersDataBase
     * @param videoDataBase
     * @param username
     * @return
     */
    public static String standardRecommendation(final UsersDatabase usersDataBase,
                                                final VideoDatabase videoDataBase,
                                                final String username) {
        String result;
        User userFound = null;
        for (var user : usersDataBase.getUsers()) {
            if (user.getUsername().equals(username)) {
                userFound = user;
            }
        }
        for (var video : videoDataBase.getVideos()) {
            if (userFound != null && !userFound.getHistory()
                    .containsKey(video.getName())) {
                result = "StandardRecommendation result: "
                        + video.getName();
                return result;
            }
        }
        result = "StandardRecommendation cannot be applied!";
        return result;
    }

    /**
     *
     * @param usersDataBase
     * @param movieDataBase
     * @param showDataBase
     * @param username
     * @return
     */
    public static String bestUnseen(final UsersDatabase usersDataBase,
                                    final MovieDatabase movieDataBase,
                                    final ShowDatabase showDataBase, final String username) {
        String result;
        User userFound = null;
        for (var user : usersDataBase.getUsers()) {
            if (user.getUsername().equals(username)) {
                userFound = user;
            }
        }
        ArrayList<Video> bestVideos = new ArrayList<>();
        for (var movie : movieDataBase.getMovies()) {
            movie.setRatingTotal();
            bestVideos.add(movie);
        }
        for (var show : showDataBase.getSerials()) {
            show.setRatingTotal();
            bestVideos.add(show);
        }

        bestVideos.sort((video1, video2) ->
                Double.compare(video2.getRatingTotal(), video1.getRatingTotal()));

        for (var video : bestVideos) {
            if (userFound != null && !userFound.getHistory().containsKey(video.getName())) {
                result = "BestRatedUnseenRecommendation result: " + video.getName();
                return result;
            }
        }
        result = "BestRatedUnseenRecommendation cannot be applied!";
        return result;

    }

    /**
     *
     * @param usersDataBase
     * @param videoDataBase
     * @param username
     * @return
     */
    public static String popularRecommendation(final UsersDatabase usersDataBase,
                                               final VideoDatabase videoDataBase,
                                               final String username) {
        String result;
        User userFound = null;
        for (var user : usersDataBase.getUsers()) {
            if (user.getUsername().equals(username)) {
                userFound = user;
            }
        }
        if (userFound == null) {
            return "error -> invalid user";
        }
        if (!userFound.getSubscriptionType().equals("PREMIUM")) {
            return "PopularRecommendation cannot be applied!";
        }
        Map<String, Integer> genresOrdered = new HashMap<>();
        for (var video : videoDataBase.getVideos()) {
            video.setNumberViews(usersDataBase);
            for (var genre : video.getGenres()) {
                if (!genresOrdered.containsKey(genre)) {
                    genresOrdered.put(genre, video.getNumberViews());
                } else {
                    int nrViews  = genresOrdered.get(genre);
                    nrViews = nrViews + video.getNumberViews();
                    genresOrdered.replace(genre, nrViews);
                }
            }
        }
        List<Map.Entry<String, Integer>> listGenres =
                new LinkedList<>(genresOrdered.entrySet());
        listGenres.sort((a1, a2) -> (a2.getValue()).compareTo(a1.getValue()));

        for (var genre : listGenres) {
            for (var video : videoDataBase.getVideos()) {
                if (video.getGenres().contains(genre.getKey())
                        && !userFound.getHistory().containsKey(video.getName())) {
                    result = "PopularRecommendation result: " + video.getName();
                    return result;
                }
            }
        }
        result = "PopularRecommendation cannot be applied!";
        return result;

    }

    /**
     *
     * @param usersDataBase
     * @param videoDataBase
     * @param username
     * @return
     */
    public static String favoriteRecommendation(final UsersDatabase usersDataBase,
                                                final VideoDatabase videoDataBase,
                                                final String username) {
        String result;
        User userFound = null;
        for (var user : usersDataBase.getUsers()) {
            if (user.getUsername().equals(username)) {
                userFound = user;
            }
        }
        ArrayList<Video> favoriteVideos = new ArrayList<>();
        for (var videos : videoDataBase.getVideos()) {
            videos.setFavoriteNumberUsers(usersDataBase);
            if (videos.getFavoriteNumberUsers() > 0) {
                favoriteVideos.add(videos);
            }
        }
        favoriteVideos.sort((video1, video2) ->
                Double.compare(video2.getFavoriteNumberUsers(), video1.getFavoriteNumberUsers()));

        for (var video : favoriteVideos) {
            if (userFound != null && !userFound.getHistory().containsKey(video.getName())) {
                result = "FavoriteRecommendation result: " + video.getName();
                return result;
            }
        }
        result = "FavoriteRecommendation cannot be applied!";
        return result;

    }

    /**
     *
     * @param usersDataBase
     * @param videoDataBase
     * @param username
     * @param genre
     * @return
     */
    public static String searchRecommendation(final UsersDatabase usersDataBase,
                                              final VideoDatabase videoDataBase,
                                              final String username, final String genre) {
        String result;
        User userFound = null;
        for (var user : usersDataBase.getUsers()) {
            if (user.getUsername().equals(username)) {
                userFound = user;
            }
        }
        if (userFound == null) {
            return "error -> invalid user";
        }
        if (!userFound.getSubscriptionType().equals("PREMIUM")) {
            return "SearchRecommendation cannot be applied!";
        }
        ArrayList<Video> searchVideos = new ArrayList<>();
        for (var video : videoDataBase.getVideos()) {
            video.setRatingTotal();
            if (video.getGenres().contains(genre)) {
                searchVideos.add(video);
            }
        }
        Video.sortAlphabetically(searchVideos, "asc");
        searchVideos.sort((video1, video2) ->
                Double.compare(video1.getRatingTotal(), video2.getRatingTotal()));

        ArrayList<Video> searchVideosUser = new ArrayList<>();
        for (var video : searchVideos) {
            if (!userFound.getHistory().containsKey(video.getName())) {
                searchVideosUser.add(video);
            }
        }

        if (searchVideosUser.isEmpty()) {
            return "SearchRecommendation cannot be applied!";
        } else {
            result = "SearchRecommendation result: [";
            for (int i = 0; i < searchVideosUser.size() - 1; i++) {
                result = result + searchVideosUser.get(i).getName() + ", ";
            }
            result = result
                    + searchVideosUser.get(searchVideosUser.size() - 1).getName()
                    + "]";
            return result;
        }
    }
}
