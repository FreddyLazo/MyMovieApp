package freddy.mymovieapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import freddy.mymovieapp.R;
import freddy.mymovieapp.model.Video;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Video> trailerData;

    public VideoAdapter(Context ctx, List<Video> trailerData) {
       // this.ctx = ctx;
        this.trailerData = trailerData;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row_layout, parent, false);
        return new MovieViewHolderHelper.VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder genericHolder, int position) {
        final MovieViewHolderHelper.VideoViewHolder holder = (MovieViewHolderHelper.VideoViewHolder) genericHolder;
        holder.movieTrailerName.setText(trailerData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return trailerData.size();
    }
}
