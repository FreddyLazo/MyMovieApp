package freddy.mymovieapp.api;

import freddy.mymovieapp.model.MovieResponses;
import freddy.mymovieapp.model.VideoResponses;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Freddy on 20/06/2018.
 * email : freddy.lazo@pucp.pe
 */

public interface Service {

    @GET("movie/popular")
    Call<MovieResponses> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponses> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MovieResponses> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<VideoResponses> getMovieVideos(@Path("movie_id") int id, @Query("api_key") String apiKey);

}
