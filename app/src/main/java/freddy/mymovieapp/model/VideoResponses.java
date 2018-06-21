package freddy.mymovieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Freddy on 20/06/2018.
 * email : freddy.lazo@pucp.pe
 */

public class VideoResponses {

    @SerializedName("id")
    private int id_trailer;
    @SerializedName("results")
    private List<Video> results;

    public VideoResponses() {
    }

    public int getIdTrailer() {
        return id_trailer;
    }

    public void seIdTrailer(int id_trailer) {
        this.id_trailer = id_trailer;
    }

    public List<Video> getResults() {
        return results;
    }
}
