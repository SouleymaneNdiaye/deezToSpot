package models;

import play.db.jpa.Model;

/**
 * Created by Souleymane on 04/05/2017.
 */
public class Artist extends Model {

    public String name;
    public String picture;
    public String picture_small;
    public String picture_medium;
    public String picture_big;
    public String picture_xl;
    public String tracklist;
    public String type;

    public Artist(){

    }
}
