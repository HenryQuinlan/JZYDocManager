package team.JZY.DocManager.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import team.JZY.DocManager.model.DocInfo;

@Database(entities = {DocInfo.class},version = 1,exportSchema = false)
public abstract class DocManagerDataBase extends RoomDatabase {
    private static DocManagerDataBase INSTANCE;
    static DocManagerDataBase getInstance(Context context){//必须通过这个函数获得数据库实例
        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),DocManagerDataBase.class,"Doc_Database")
                    .build();
        }
        return INSTANCE;
    }
    public abstract DocInfoDao getDocInfoDao();
}
