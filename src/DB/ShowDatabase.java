package DB;

import entertainment.Show;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.List;

public class ShowDatabase {

    private final ArrayList<Show> serials = new ArrayList<>();

    public ShowDatabase(final List<SerialInputData> serials) {
        for (int i = 0; i < serials.size(); i++) {
            Show util = new Show(serials.get(i).getTitle(), serials.get(i).getCast(),
                    serials.get(i).getGenres(), serials.get(i).getNumberSeason(),
                    serials.get(i).getSeasons(),
                    serials.get(i).getYear());
            this.serials.add(util);
        }
    }

    public final ArrayList<Show> getSerials() {
        return serials;
    }


}
