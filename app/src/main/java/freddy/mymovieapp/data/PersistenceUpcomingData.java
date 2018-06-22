package freddy.mymovieapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import freddy.mymovieapp.model.Movie;

/**
 * Created by Freddy on 22/06/2018.
 * email : freddy.lazo@pucp.pe
 */

public class PersistenceUpcomingData extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "upcoming.db";
    private static final int DATABASE_VERSION = 1;

    public PersistenceUpcomingData(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + SaveDataHelper.UpcomingEntry.TABLE_NAME + " (" +
                SaveDataHelper.UpcomingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SaveDataHelper.UpcomingEntry.COLUMN_MOVIEID + " INTEGER, " +
                SaveDataHelper.UpcomingEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                SaveDataHelper.UpcomingEntry.COLUMN_USERRATING + " REAL NOT NULL, " +
                SaveDataHelper.UpcomingEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                SaveDataHelper.UpcomingEntry.COLUMN_PLOT_SYNOPSIS + " TEXT NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SaveDataHelper.UpcomingEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public void saveData(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SaveDataHelper.UpcomingEntry.COLUMN_MOVIEID, movie.getId());
        values.put(SaveDataHelper.UpcomingEntry.COLUMN_TITLE, movie.getOriginalTitle());
        values.put(SaveDataHelper.UpcomingEntry.COLUMN_USERRATING, movie.getVoteAverage());
        values.put(SaveDataHelper.UpcomingEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(SaveDataHelper.UpcomingEntry.COLUMN_PLOT_SYNOPSIS, movie.getOverview());

        db.insert(SaveDataHelper.UpcomingEntry.TABLE_NAME, null, values);
        db.close();
    }

    public List<Movie> getAllMovies(){
        String[] columns = {
                SaveDataHelper.UpcomingEntry._ID,
                SaveDataHelper.UpcomingEntry.COLUMN_MOVIEID,
                SaveDataHelper.UpcomingEntry.COLUMN_TITLE,
                SaveDataHelper.UpcomingEntry.COLUMN_USERRATING,
                SaveDataHelper.UpcomingEntry.COLUMN_POSTER_PATH,
                SaveDataHelper.UpcomingEntry.COLUMN_PLOT_SYNOPSIS

        };
        String sortOrder =
                SaveDataHelper.UpcomingEntry._ID + " ASC";
        List<Movie> favoriteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SaveDataHelper.UpcomingEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()){
            do {
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SaveDataHelper.UpcomingEntry.COLUMN_MOVIEID))));
                movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(SaveDataHelper.UpcomingEntry.COLUMN_TITLE)));
                movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(SaveDataHelper.UpcomingEntry.COLUMN_USERRATING))));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(SaveDataHelper.UpcomingEntry.COLUMN_POSTER_PATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(SaveDataHelper.UpcomingEntry.COLUMN_PLOT_SYNOPSIS)));

                favoriteList.add(movie);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favoriteList;
    }
}
