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

public class PersistenceTopRatedData extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "favorite.db";

        private static final int DATABASE_VERSION = 1;

        public PersistenceTopRatedData(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FavoriteContract.TopRatedEntry.TABLE_NAME + " (" +
                    FavoriteContract.TopRatedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    FavoriteContract.TopRatedEntry.COLUMN_MOVIEID + " INTEGER, " +
                    FavoriteContract.TopRatedEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                    FavoriteContract.TopRatedEntry.COLUMN_USERRATING + " REAL NOT NULL, " +
                    FavoriteContract.TopRatedEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                    FavoriteContract.TopRatedEntry.COLUMN_PLOT_SYNOPSIS + " TEXT NOT NULL" +
                    "); ";

            sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.TopRatedEntry.TABLE_NAME);
            onCreate(sqLiteDatabase);
        }


        public void saveData(Movie movie){
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(FavoriteContract.TopRatedEntry.COLUMN_MOVIEID, movie.getId());
            values.put(FavoriteContract.TopRatedEntry.COLUMN_TITLE, movie.getOriginalTitle());
            values.put(FavoriteContract.TopRatedEntry.COLUMN_USERRATING, movie.getVoteAverage());
            values.put(FavoriteContract.TopRatedEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
            values.put(FavoriteContract.TopRatedEntry.COLUMN_PLOT_SYNOPSIS, movie.getOverview());

            db.insert(FavoriteContract.TopRatedEntry.TABLE_NAME, null, values);
            db.close();
        }

        public List<Movie> getAllMovies(){
            String[] columns = {
                    FavoriteContract.TopRatedEntry._ID,
                    FavoriteContract.TopRatedEntry.COLUMN_MOVIEID,
                    FavoriteContract.TopRatedEntry.COLUMN_TITLE,
                    FavoriteContract.TopRatedEntry.COLUMN_USERRATING,
                    FavoriteContract.TopRatedEntry.COLUMN_POSTER_PATH,
                    FavoriteContract.TopRatedEntry.COLUMN_PLOT_SYNOPSIS

            };
            String sortOrder =
                    FavoriteContract.FavoriteEntry._ID + " ASC";
            List<Movie> favoriteList = new ArrayList<>();

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME,
                    columns,
                    null,
                    null,
                    null,
                    null,
                    sortOrder);

            if (cursor.moveToFirst()){
                do {
                    Movie movie = new Movie();
                    movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavoriteContract.TopRatedEntry.COLUMN_MOVIEID))));
                    movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(FavoriteContract.TopRatedEntry.COLUMN_TITLE)));
                    movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(FavoriteContract.TopRatedEntry.COLUMN_USERRATING))));
                    movie.setPosterPath(cursor.getString(cursor.getColumnIndex(FavoriteContract.TopRatedEntry.COLUMN_POSTER_PATH)));
                    movie.setOverview(cursor.getString(cursor.getColumnIndex(FavoriteContract.TopRatedEntry.COLUMN_PLOT_SYNOPSIS)));

                    favoriteList.add(movie);

                }while(cursor.moveToNext());
            }
            cursor.close();
            db.close();

            return favoriteList;
        }
}
