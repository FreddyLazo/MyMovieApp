package freddy.mymovieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

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

    public MoviesAdapter(Context ctx, List<Movie> movieData){
        this.ctx = ctx;
        this.movieData = movieData;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row_layout, parent, false);
        return new MovieViewHolderHelper.MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder genericHolder, int position) {
        final MovieViewHolderHelper.MovieViewHolder holder = (MovieViewHolderHelper.MovieViewHolder) genericHolder;
        holder.movieTittle.setText(movieData.get(position).getOriginalTitle());
        String url = "https://image.tmdb.org/t/p/w500" + movieData.get(position).getPosterPath();
        Glide.with(ctx)
                .load(url)
                .placeholder(R.drawable.image_loader_gif)
                .into(holder.movieImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie movieDataClicked = movieData.get(holder.getAdapterPosition());
                Intent intent = new Intent(ctx, MovieDetailActivity.class);
                intent.putExtra("movie_data", movieDataClicked );
                ctx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }
}
