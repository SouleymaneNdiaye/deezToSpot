package models;

import com.google.gson.annotations.SerializedName;
import play.db.jpa.Model;

import java.util.Date;
import java.util.List;

/**
 * Created by Souleymane on 04/05/2017.
 */
public class Album extends Model {


    public String spotId;
    public String title;
    public String link;
    public String cover;
    public String cover_small;
    public String cover_medium;
    public String cover_big;
    public String cover_xl;
    public Long nb_tracks;
    public Date release_date;
    public String record_type;
    public String available;
    public String tracklist;
    public Boolean explicit_lyrics;
    public Long time_add;
    public String type;
    public Artist artist;

public Album(){

}


    }

