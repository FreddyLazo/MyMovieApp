package freddy.mymovieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import freddy.mymovieapp.R;
import freddy.mymovieapp.model.Video;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Video> trailerData;
    private  Context ctx;

    public VideoAdapter(Context ctx, List<Video> trailerData) {
        this.ctx = ctx;
        this.trailerData = trailerData;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_row_layout, parent, false);
        return new MovieViewHolderHelper.VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder genericHolder, final int position) {
        final MovieViewHolderHelper.VideoViewHolder holder = (MovieViewHolderHelper.VideoViewHolder) genericHolder;
        holder.movieTrailerName.setText(trailerData.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String videoKey = trailerData.get(position).getKey();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+videoKey));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("VIDEO_ID", videoKey);
                ctx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailerData.size();
    }
}
