package database;

import entertainment.Show;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.List;

public class ShowDatabase {

    private final ArrayList<Show> serials = new ArrayList<>();

    public ShowDatabase(final List<SerialInputData> serials) {
        for (SerialInputData serial : serials) {
            Show util = new Show(serial.getTitle(), serial.getCast(),
                    serial.getGenres(), serial.getNumberSeason(),
                    serial.getSeasons(),
                    serial.getYear());
            this.serials.add(util);
        }
    }

    public final ArrayList<Show> getSerials() {
        return serials;
    }

    /** method that sorts alphabetically a show array based on the given sortType
     * @param shows the input given through the json and converted to objects
     * @param sortType sortType the type of sort : ascending or descending
     */
    public static void sortAlphabeticallyShow(final ArrayList<Show> shows,
                                              final String sortType) {
        if (sortType.equals("asc")) {
            shows.sort((show1, show2) -> show1.getName().compareTo(show2.getName()));
        } else {
            shows.sort((show1, show2) -> show2.getName().compareTo(show1.getName()));
        }
    }

}
