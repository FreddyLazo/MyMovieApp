package freddy.mymovieapp.data;

import android.provider.BaseColumns;

/**
 * Created by Freddy on 21/06/2018.
 * email : freddy.lazo@pucp.pe
 */

class SaveDataHelper implements BaseColumns {

    static final class FavoriteEntry implements BaseColumns {
        static final String TABLE_NAME = "favorite";
        static final String COLUMN_MOVIEID = "movieid";
        static final String COLUMN_TITLE = "title";
        static final String COLUMN_USERRATING = "userrating";
        static final String COLUMN_POSTER_PATH = "posterpath";
        static final String COLUMN_PLOT_SYNOPSIS = "overview";
    }

    static final class TopRatedEntry implements BaseColumns {
        static final String TABLE_NAME = "topRated";
        static final String COLUMN_MOVIEID = "movieid";
        static final String COLUMN_TITLE = "title";
        static final String COLUMN_USERRATING = "userrating";
        static final String COLUMN_POSTER_PATH = "posterpath";
        static final String COLUMN_PLOT_SYNOPSIS = "overview";
    }

    static final class UpcomingEntry implements BaseColumns {
        static final String TABLE_NAME = "upcoming";
        static final String COLUMN_MOVIEID = "movieid";
        static final String COLUMN_TITLE = "title";
        static final String COLUMN_USERRATING = "userrating";
        static final String COLUMN_POSTER_PATH = "posterpath";
        static final String COLUMN_PLOT_SYNOPSIS = "overview";
    }
}
