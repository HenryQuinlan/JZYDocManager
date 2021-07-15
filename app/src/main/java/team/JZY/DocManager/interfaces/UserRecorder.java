package team.JZY.DocManager.interfaces;

import java.util.List;

import team.JZY.DocManager.model.DocInfo;
import team.JZY.DocManager.model.Record;

public interface UserRecorder {
    public void insertRecord(String username,int operationType,long docId,String docName,int docType);
    public List<Record>getDownloadRecord(String UserName);
    public List<Record>getUploadRecord(String UserName);
    public List<Record>getVisitRecord(String UserName);
    public List<Record> getFavoriteRecord(String UserName);
    public List<Record> checkRecord(String UserName,int operationType,long docId);
    public void deleteRecord(Record... record);
}
