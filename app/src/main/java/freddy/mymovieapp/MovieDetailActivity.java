package freddy.mymovieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import freddy.mymovieapp.model.Movie;

/**
 * Created by Freddy on 21/06/2018.
 * email : freddy.lazo@pucp.pe
 */

public class MovieDetailActivity extends BaseActivity {

    Movie movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(shouldInitActivity(getIntent())){
            movie = getIntent().getParcelableExtra("movies");

        }else{
            finish();
        }
    }

    private boolean shouldInitActivity(Intent intent) {
        return intent.hasExtra("movie_data") && intent.getParcelableExtra("movie_data") != null;
    }
}
