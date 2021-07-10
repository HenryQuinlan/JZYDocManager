package team.JZY.DocManager.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import team.JZY.DocManager.model.Record;

@Database(entities = {Record.class},version = 1,exportSchema = false)
public abstract class RecordDatabase extends RoomDatabase {
    private static RecordDatabase INSTANCE;
    static RecordDatabase getDatabase(Context context){
        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),RecordDatabase.class,"Record_db")
                    .allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
    public abstract RecordDao getRecordDao();
}

