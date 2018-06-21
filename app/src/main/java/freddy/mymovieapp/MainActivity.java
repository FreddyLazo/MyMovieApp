package freddy.mymovieapp;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import freddy.mymovieapp.Interfaces.SortMethodInterface;
import freddy.mymovieapp.adapter.MoviesAdapter;
import freddy.mymovieapp.api.Service;
import freddy.mymovieapp.model.Movie;
import freddy.mymovieapp.model.MovieResponses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements SortMethodInterface {

    @BindView(R.id.my_recycler_view)
    RecyclerView myList;

    private List<Movie> movieList;
    private MoviesAdapter adapter;
    private int currentSortMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        loadDataFromApi();
    }

    private void initData() {
        currentSortMethod = ApplicationConstants.SortMovieMethods.POPULAR;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sort_movies:
                showSortOptionsDialogFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSortOptionsDialogFragment() {
        SortMovieOptionsDialogFragment changelogDialogFragment = SortMovieOptionsDialogFragment.newInstance(currentSortMethod);
        changelogDialogFragment.show(getFragmentManager(), SortMovieOptionsDialogFragment.TAG);
    }

    /**
     * This method gonna load all the data from the MovieDb API
     */
    private void loadDataFromApi() {

        movieList = new ArrayList<>();
        adapter = new MoviesAdapter(this, movieList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ctx, 2);
        myList.setLayoutManager(gridLayoutManager);
        myList.setItemAnimator(new DefaultItemAnimator());
        myList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        // favoriteDbHelper = new FavoriteDbHelper(activity);
        Service apiService = freddy.mymovieapp.api.Client.getClient().create(Service.class);
        Call<MovieResponses> call  = apiService.getPopularMovies(ApplicationConstants.ApiKeyConstant.API_KEY_CONSTANT);
        loadJson(call);

    }

    private void loadJson(Call<MovieResponses> call) {

        // Client Client = new Client();

        call.enqueue(new Callback<MovieResponses>() {
            @Override
            public void onResponse(Call<MovieResponses> call, Response<MovieResponses> response) {
                List<Movie> movies = response.body().getResults();
                //Collections.sort(movies, Movie.BY_NAME_ALPHABETICAL);
                adapter = new MoviesAdapter(ctx, movies);
                myList.setAdapter(adapter);
                myList.scrollToPosition(0);
            }

            @Override
            public void onFailure(Call<MovieResponses> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(MainActivity.this, "Error Fetching Data!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void sortMethodSelected(int sort_method) {
        currentSortMethod = sort_method;
        Service apiService = freddy.mymovieapp.api.Client.getClient().create(Service.class);
        Call<MovieResponses> call;
        switch (sort_method) {
            case ApplicationConstants.SortMovieMethods.POPULAR:
                call = apiService.getPopularMovies(ApplicationConstants.ApiKeyConstant.API_KEY_CONSTANT);
                break;
            case ApplicationConstants.SortMovieMethods.TOP_RATED:
                call = apiService.getTopRatedMovies(ApplicationConstants.ApiKeyConstant.API_KEY_CONSTANT);
                break;
            case ApplicationConstants.SortMovieMethods.UPCOMING:
                call = apiService.getUpcomingMovies(ApplicationConstants.ApiKeyConstant.API_KEY_CONSTANT);
                break;
            default:
                call = apiService.getPopularMovies(ApplicationConstants.ApiKeyConstant.API_KEY_CONSTANT);
                break;
        }
        loadJson(call);
    }


}
