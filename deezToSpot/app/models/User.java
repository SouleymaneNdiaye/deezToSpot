package models;

import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by sndiaye on 09/12/2016.
 */
@Entity
@Table(name="user")
public class User extends Model {

    @Column(name="user_id")
    public Long userId;

    @Column(name="nickname")
    public String nickname;

    public User(Long id, String nickname){
        this.userId=id;
        this.nickname=nickname;

    }


}
