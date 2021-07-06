package team.JZY.DocManager.interfaces;

import java.util.List;

import team.JZY.DocManager.model.LocalDoc;

public interface UserRecorder {
    public void updateRecord(String userName,LocalDoc localDoc,int operationType);
    public List<LocalDoc>getDownloadRecord(String UserName);
    public List<LocalDoc>getUploadRecord(String UserName);
    public List<LocalDoc>getVisitRecord(String UserName);
    public List<LocalDoc> getFavoriteRecord(String UserName);
    public void deleteRecord(String userName,LocalDoc localDoc,int operationType);

}
