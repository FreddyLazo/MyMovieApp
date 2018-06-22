package freddy.mymovieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import freddy.mymovieapp.MovieDetailActivity;
import freddy.mymovieapp.R;
import freddy.mymovieapp.model.Movie;

/**
 * Created by Freddy on 20/06/2018.
 * email : freddy.lazo@pucp.pe
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context ctx;
    private List<Movie> movieData;
    private List<Movie> filteredMovieData;
    private List<Movie> generalData;
    private boolean isFiltering;

    public MoviesAdapter(Context ctx, List<Movie> movieData) {
        this.ctx = ctx;
        this.movieData = movieData;
        this.isFiltering = false;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row_layout, parent, false);
        return new MovieViewHolderHelper.MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder genericHolder, int position) {
        final MovieViewHolderHelper.MovieViewHolder holder = (MovieViewHolderHelper.MovieViewHolder) genericHolder;
        generalData = isFiltering ? filteredMovieData : movieData;
        holder.movieTittle.setText(generalData.get(position).getOriginalTitle());
        String url = "https://image.tmdb.org/t/p/w500" + generalData.get(position).getPosterPath();
        Glide.with(ctx)
                .load(url)
                .placeholder(R.drawable.image_loader)
                .into(holder.movieImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie movieDataClicked = generalData.get(holder.getAdapterPosition());
                Intent intent = new Intent(ctx, MovieDetailActivity.class);
                intent.putExtra("movie_data", movieDataClicked);
                ctx.startActivity(intent);
            }
        });

    }

    public void performQuery(String query) {
        if (!query.isEmpty()) {
            query = query.toLowerCase().trim();
            filteredMovieData = new ArrayList<>();
            for (Movie movie : movieData) {
                if (movie.getOriginalTitle().toLowerCase().startsWith(query)) {
                    filteredMovieData.add(movie);
                }
            }
            isFiltering = true;
        } else {
            isFiltering = false;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return isFiltering ? filteredMovieData.size() : movieData.size();
    }
}
