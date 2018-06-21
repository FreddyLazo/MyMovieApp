package freddy.mymovieapp;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import freddy.mymovieapp.adapter.VideoAdapter;
import freddy.mymovieapp.api.Service;
import freddy.mymovieapp.model.Movie;
import freddy.mymovieapp.model.Video;
import freddy.mymovieapp.model.VideoResponses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Freddy on 21/06/2018.
 * email : freddy.lazo@pucp.pe
 */

public class MovieDetailActivity extends BaseActivity {

    @BindView(R.id.movie_image_view)
    ImageView movieImageView;
    @BindView(R.id.movie_name)
    TextView movieNameTextView;
    @BindView(R.id.movie_overview)
    TextView movieOverviewTextView;
    @BindView(R.id.movie_release_date)
    TextView movieDateTextView;
    @BindView(R.id.my_toolbar)
    Toolbar toolbar;
    @BindView(R.id.trailer_list)
    RecyclerView myTrailerList;
    @BindView(R.id.movie_rating_bar)
    RatingBar movieRatingBar;


    private String movieUrl;
    private String movieName;
    private String movieOverview;
    private String dateOfRelease;
    private Double movieVoteAverage;
    private Movie movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (shouldInitActivity(getIntent())) {
            setContentView(R.layout.detail_movie_layuot);
            ButterKnife.bind(this);
            SetUpToolbar();
            movie = getIntent().getParcelableExtra("movie_data");
            initMovieData(movie);
            setTitle(movieName);
            initUI();
            myTrailerList.setLayoutManager(new LinearLayoutManager(ctx));
            loadMovieTrailers(movie.getId());
        } else {
            finish();
        }
    }

    private void loadMovieTrailers(Integer id) {
        Service apiService = freddy.mymovieapp.api.Client.getClient().create(Service.class);
        Call<VideoResponses> call = apiService.getMovieVideos(id, ApplicationConstants.ApiKeyConstant.API_KEY_CONSTANT);
        call.enqueue(new Callback<VideoResponses>() {
            @Override
            public void onResponse(Call<VideoResponses> call, Response<VideoResponses> response) {
                List<Video> trailer = response.body().getResults();
                myTrailerList.setAdapter(new VideoAdapter(ctx, trailer));
                myTrailerList.smoothScrollToPosition(0);
            }

            @Override
            public void onFailure(Call<VideoResponses> call, Throwable t) {


            }
        });
    }

    private void SetUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initUI() {
        String glideUrl = "https://image.tmdb.org/t/p/w500" + movieUrl;

        Glide.with(this)
                .load(glideUrl)
                .placeholder(R.drawable.image_loader_gif)
                .into(movieImageView);

        movieNameTextView.setText(movieName);
        movieOverviewTextView.setText(movieOverview);
        movieDateTextView.setText(dateOfRelease);
        movieRatingBar.setRating(movieVoteAverage.floatValue()/2);
    }

    private void initMovieData(Movie movie) {
        movieUrl = movie.getPosterPath();
        movieName = movie.getOriginalTitle();
        movieOverview = movie.getOverview();
        dateOfRelease = movie.getReleaseDate();
        movieVoteAverage = movie.getVoteAverage();
    }

    private boolean shouldInitActivity(Intent intent) {
        return intent.hasExtra("movie_data") && intent.getParcelableExtra("movie_data") != null;
    }
}
