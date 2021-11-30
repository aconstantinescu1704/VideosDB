package database;

import entertainment.Movie;
import entertainment.Video;

import java.util.ArrayList;

public class VideoDatabase {

    private final ArrayList<Video> videos = new ArrayList<>();

    public final ArrayList<Video> getVideos() {
        return videos;
    }

    public VideoDatabase(final MovieDatabase movies, final ShowDatabase serials) {
        for (var movie : movies.getMovies()) {
            videos.add(movie);
        }
        for (var serial : serials.getSerials()) {
            videos.add(serial);
        }
    }
    /** sorts method that sorts alphabetically a movie array based on the given sortType
     * @param videos the databased where are stored all information about videos
     * @param sortType the type of sort : ascending or descending
     */
    public static void sortAlphabeticallyVideo(final ArrayList<Video> videos,
                                               final String sortType) {
        if (sortType.equals("asc")) {
            videos.sort((video1, video2) -> video1.getName().compareTo(video2.getName()));
        } else {
            videos.sort((video1, video2) -> video2.getName().compareTo(video1.getName()));
        }
    }

}
