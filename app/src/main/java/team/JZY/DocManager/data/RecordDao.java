package team.JZY.DocManager.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import team.JZY.DocManager.model.Record;

@Dao
public interface RecordDao {
    @Insert
    void insertOperation(Record...records);
    @Update
    void updateOperation(Record...records);
    @Delete
    void deleteOperation(Record...records);
    @Query("DELETE FROM Record")
    void deleteAllRecord();
    @Query("SELECT * FROM Record ORDER BY ID DESC")
    LiveData<List<Record>> getAllRecordsLive();
    @Query(value = "SELECT * FROM Record where  OperationType = :OperationType and Operator=:Operator order by id DESC")
        //livedata
    List<Record>findOperation(int OperationType,String Operator);

}
