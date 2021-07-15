package team.JZY.DocManager.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    //暂时这么写 需要改的话UserViewModel那里的user构造也要改

    public User(String name){
        this.name=name;
    }
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String name;
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    @ColumnInfo(name="password")
    private String password;
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password =password;
    }

}

