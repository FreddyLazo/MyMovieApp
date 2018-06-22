package freddy.mymovieapp.controller;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import freddy.mymovieapp.ApplicationConstants;
import freddy.mymovieapp.Interfaces.SortMethodInterface;
import freddy.mymovieapp.R;
import freddy.mymovieapp.adapter.MoviesAdapter;
import freddy.mymovieapp.api.Service;
import freddy.mymovieapp.data.PersistencePopularData;
import freddy.mymovieapp.data.PersistenceTopRatedData;
import freddy.mymovieapp.data.PersistenceUpcomingData;
import freddy.mymovieapp.data.PreferencesHelper;
import freddy.mymovieapp.model.Movie;
import freddy.mymovieapp.model.MovieResponses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements SortMethodInterface, SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    @BindView(R.id.my_recycler_view)
    RecyclerView myList;
    @BindView(R.id.shimmer_layout)
    ShimmerFrameLayout shimmerLayout;

    private MoviesAdapter adapter;
    private int currentSortMethod;
    private SearchView searchView;
    private PersistencePopularData favoriteData;
    private PersistenceTopRatedData topRatedData;
    private PersistenceUpcomingData upcomingData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        loadDataFromApi();
    }

    /**
     * Init default sort movie method
     */
    private void initData() {
        currentSortMethod = ApplicationConstants.SortMovieMethods.POPULAR;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        searchView = (SearchView) search.getActionView();
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

    /**
     * Call a DialogFragment in order to show a RadioGroup
     */
    private void showSortOptionsDialogFragment() {
        SortMovieOptionsDialogFragment changelogDialogFragment = SortMovieOptionsDialogFragment.newInstance(currentSortMethod);
        changelogDialogFragment.show(getFragmentManager(), SortMovieOptionsDialogFragment.TAG);
    }

    /**
     * This method gonna load all the data from the MovieDb API
     */
    private void loadDataFromApi() {
        shimmerLayout.startShimmerAnimation();
        List<Movie> movieList = new ArrayList<>();
        adapter = new MoviesAdapter(this, movieList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ctx, 2);
        myList.setLayoutManager(gridLayoutManager);
        myList.setItemAnimator(new DefaultItemAnimator());
        myList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Service apiService = freddy.mymovieapp.api.Client.getClient().create(Service.class);
        Call<MovieResponses> call = apiService.getPopularMovies(ApplicationConstants.ApiKeyConstant.API_KEY_CONSTANT);
        loadJson(call, ApplicationConstants.SortMovieMethods.POPULAR);
    }

    /**
     * This method load the data depends on the sort movie method
     *
     * @param call
     * @param sortMovieMethod
     */
    private void loadJson(Call<MovieResponses> call, final int sortMovieMethod) {
        call.enqueue(new Callback<MovieResponses>() {
            @Override
            public void onResponse(Call<MovieResponses> call, Response<MovieResponses> response) {
                endAnimation();
                List<Movie> movies = response.body().getResults();
                adapter = new MoviesAdapter(ctx, movies);
                myList.setAdapter(adapter);
                myList.scrollToPosition(0);
                if (shouldSaveData(sortMovieMethod)) {
                    saveData(movies, sortMovieMethod);
                }
            }

            @Override
            public void onFailure(Call<MovieResponses> call, Throwable t) {
                endAnimation();
                getDataFromLocalDatabase(sortMovieMethod);
            }
        });
    }

    /**
     * End fancy animation
     */
    private void endAnimation() {
        shimmerLayout.stopShimmerAnimation();
        shimmerLayout.setVisibility(View.GONE);
    }

    /**
     * Method useful to load the data just once and avoid same movies
     *
     * @param sortMovieMethod
     * @return
     */
    private boolean shouldSaveData(int sortMovieMethod) {
        return !PreferencesHelper.getCachePreference(ctx, sortMovieMethod);
    }

    /**
     * This method call the local database in case we safe data other case the list gonna be empty
     *
     * @param sortMovieMethod
     */
    private void getDataFromLocalDatabase(int sortMovieMethod) {
        switch (sortMovieMethod) {
            case ApplicationConstants.SortMovieMethods.POPULAR:
                favoriteData = new PersistencePopularData(ctx);
                adapter = new MoviesAdapter(ctx, favoriteData.getAllMovies());
                break;
            case ApplicationConstants.SortMovieMethods.TOP_RATED:
                topRatedData = new PersistenceTopRatedData(ctx);
                adapter = new MoviesAdapter(ctx, topRatedData.getAllMovies());
                break;
            case ApplicationConstants.SortMovieMethods.UPCOMING:
                upcomingData = new PersistenceUpcomingData(ctx);
                adapter = new MoviesAdapter(ctx, upcomingData.getAllMovies());
                break;
        }
        myList.setAdapter(adapter);
        myList.scrollToPosition(0);
    }

    /**
     * This method save loaded data from the API
     *
     * @param movies
     * @param sortMovieMethod
     */
    private void saveData(List<Movie> movies, int sortMovieMethod) {
        saveCacheData(movies, sortMovieMethod);
        PreferencesHelper.setCachePreference(ctx, sortMovieMethod);
    }

    /**
     * This Method add all the movie data
     *
     * @param movies
     * @param sortMovieMethod
     */
    private void saveCacheData(List<Movie> movies, int sortMovieMethod) {
        switch (sortMovieMethod) {
            case ApplicationConstants.SortMovieMethods.POPULAR:
                favoriteData = new PersistencePopularData(ctx);
                for (Movie movie : movies) {
                    favoriteData.saveData(movie);
                }
                break;
            case ApplicationConstants.SortMovieMethods.TOP_RATED:
                topRatedData = new PersistenceTopRatedData(ctx);
                for (Movie movie : movies) {
                    topRatedData.saveData(movie);
                }
                break;
            case ApplicationConstants.SortMovieMethods.UPCOMING:
                upcomingData = new PersistenceUpcomingData(ctx);
                for (Movie movie : movies) {
                    upcomingData.saveData(movie);
                }
                break;
        }
    }

    @Override
    public void sortMethodSelected(int sort_method) {
        loadJson(getCallFromSortMethod(sort_method), sort_method);
    }

    /**
     * This method return a call from sortMethod
     * @param sort_method
     * @return
     */
    private Call<MovieResponses> getCallFromSortMethod(int sort_method) {
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
        return call;
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
        } else {
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
