package freddy.mymovieapp;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import freddy.mymovieapp.Interfaces.SortMethodInterface;
import freddy.mymovieapp.adapter.MoviesAdapter;
import freddy.mymovieapp.api.Service;
import freddy.mymovieapp.data.PersistencePopularData;
import freddy.mymovieapp.model.Movie;
import freddy.mymovieapp.model.MovieResponses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements SortMethodInterface , SearchView.OnQueryTextListener , SearchView.OnCloseListener{

    @BindView(R.id.my_recycler_view)
    RecyclerView myList;

    private List<Movie> movieList;
    private MoviesAdapter adapter;
    private int currentSortMethod;
    private SearchView searchView;
    private PersistencePopularData favoriteDbHelper;



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
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
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
                saveData(movies);
            }

            @Override
            public void onFailure(Call<MovieResponses> call, Throwable t) {
                favoriteDbHelper = new PersistencePopularData(ctx);
                adapter = new MoviesAdapter(ctx,  favoriteDbHelper.getAllMovies());
                myList.setAdapter(adapter);
                myList.scrollToPosition(0);
            }
        });
    }

    private void saveData( List<Movie> movies) {
        favoriteDbHelper = new PersistencePopularData(ctx);
        for(Movie movie :movies ){
            favoriteDbHelper.saveData(movie);
        }
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

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d("query", newText);
        adapter.performQuery(newText);
        return false;
    }

    @Override
    public boolean onClose() {
        return false;
    }
}
