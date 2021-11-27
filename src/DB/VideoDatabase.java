package DB;

import entertainment.Movie;
import entertainment.Show;
import entertainment.Video;
import fileio.MovieInputData;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.List;

public class VideoDatabase {

    private final ArrayList<Video> videos = new ArrayList<>();


    public final ArrayList<Video> getVideos() {
        return videos;
    }

    public VideoDatabase(final List<MovieInputData> movies, final List<SerialInputData> serials) {
        for (int i = 0; i < movies.size(); i++) {
            Movie util1 = new Movie(movies.get(i).getTitle(), movies.get(i).getCast(),
                    movies.get(i).getGenres(), movies.get(i).getYear(),
                    movies.get(i).getDuration());
            videos.add(util1);
        }
        for (int i = 0; i < serials.size(); i++) {
            Show util2 = new Show(serials.get(i).getTitle(), serials.get(i).getCast(),
                    serials.get(i).getGenres(), serials.get(i).getNumberSeason(),
                    serials.get(i).getSeasons(),
                    serials.get(i).getYear());

            videos.add(util2);
        }
    }

}
