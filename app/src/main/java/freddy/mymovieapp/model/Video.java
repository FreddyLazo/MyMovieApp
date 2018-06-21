package freddy.mymovieapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Freddy on 20/06/2018.
 * email : freddy.lazo@pucp.pe
 */

public class Video {

    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;

    public Video() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
