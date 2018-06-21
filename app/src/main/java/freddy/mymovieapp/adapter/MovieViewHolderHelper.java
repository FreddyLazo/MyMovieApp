package freddy.mymovieapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import freddy.mymovieapp.R;

/**
 * Created by Freddy on 20/06/2018.
 * email : freddy.lazo@pucp.pe
 */

class MovieViewHolderHelper {

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_image_view)
        ImageView movieImageView;
        @BindView(R.id.movie_text_view)
        TextView movieTittle;

        MovieViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

}
