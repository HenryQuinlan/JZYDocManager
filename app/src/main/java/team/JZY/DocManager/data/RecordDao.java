package team.JZY.DocManager.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import team.JZY.DocManager.model.Record;

@Dao
public interface RecordDao {
//    public void updateRecord(String userName, DocInfo docInfo, int operationType);
//    public List<Record>getDownloadRecord(String UserName);
//    public List<Record>getUploadRecord(String UserName);
//    public List<Record>getVisitRecord(String UserName);
//    public List<Record> getFavoriteRecord(String UserName);
//    public void deleteRecord(String userName,DocInfo docInfo,int operationType);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Record... records);

    @Query(value = "SELECT * FROM Record where  OperationType = :OperationType and Operator=:Operator ")
    List<Record>findOperation(String Operator,int OperationType);

    @Query(value = "SELECT * FROM Record where  OperationType = :OperationType and Operator=:Operator and docId = :docId")
    List<Record>findOperation(String Operator,int OperationType,long docId);


    @Delete
    void deleteOperation(Record...records);

    @Query("DELETE FROM Record")
    void deleteAllRecord();



}
