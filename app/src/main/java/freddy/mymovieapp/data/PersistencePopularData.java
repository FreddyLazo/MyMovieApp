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
 * Created by Freddy on 21/06/2018.
 * email : freddy.lazo@pucp.pe
 */

public class PersistencePopularData extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "popular.db";

    private static final int DATABASE_VERSION = 1;

    public PersistencePopularData(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + SaveDataHelper.FavoriteEntry.TABLE_NAME + " (" +
                SaveDataHelper.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SaveDataHelper.FavoriteEntry.COLUMN_MOVIEID + " INTEGER, " +
                SaveDataHelper.FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                SaveDataHelper.FavoriteEntry.COLUMN_USERRATING + " REAL NOT NULL, " +
                SaveDataHelper.FavoriteEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                SaveDataHelper.FavoriteEntry.COLUMN_PLOT_SYNOPSIS + " TEXT NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SaveDataHelper.FavoriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public void saveData(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SaveDataHelper.FavoriteEntry.COLUMN_MOVIEID, movie.getId());
        values.put(SaveDataHelper.FavoriteEntry.COLUMN_TITLE, movie.getOriginalTitle());
        values.put(SaveDataHelper.FavoriteEntry.COLUMN_USERRATING, movie.getVoteAverage());
        values.put(SaveDataHelper.FavoriteEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(SaveDataHelper.FavoriteEntry.COLUMN_PLOT_SYNOPSIS, movie.getOverview());

        db.insert(SaveDataHelper.FavoriteEntry.TABLE_NAME, null, values);
        db.close();
    }

    public List<Movie> getAllMovies(){
        String[] columns = {
                SaveDataHelper.FavoriteEntry._ID,
                SaveDataHelper.FavoriteEntry.COLUMN_MOVIEID,
                SaveDataHelper.FavoriteEntry.COLUMN_TITLE,
                SaveDataHelper.FavoriteEntry.COLUMN_USERRATING,
                SaveDataHelper.FavoriteEntry.COLUMN_POSTER_PATH,
                SaveDataHelper.FavoriteEntry.COLUMN_PLOT_SYNOPSIS

        };
        String sortOrder =
                SaveDataHelper.FavoriteEntry._ID + " ASC";
        List<Movie> favoriteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(SaveDataHelper.FavoriteEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()){
            do {
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SaveDataHelper.FavoriteEntry.COLUMN_MOVIEID))));
                movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(SaveDataHelper.FavoriteEntry.COLUMN_TITLE)));
                movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(SaveDataHelper.FavoriteEntry.COLUMN_USERRATING))));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(SaveDataHelper.FavoriteEntry.COLUMN_POSTER_PATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(SaveDataHelper.FavoriteEntry.COLUMN_PLOT_SYNOPSIS)));

                favoriteList.add(movie);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favoriteList;
    }
}
