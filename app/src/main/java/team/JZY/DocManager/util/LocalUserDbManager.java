package team.JZY.DocManager.util;

import java.util.List;

import team.JZY.DocManager.interfaces.UserRecorder;
import team.JZY.DocManager.model.LocalDoc;

public class LocalUserDbManager implements UserRecorder {
    @Override
    public void updateRecord(String userName, LocalDoc localDoc, int operationType) {

    }

    @Override
    public List<LocalDoc> getDownloadRecord(String UserName) {
        return null;
    }

    @Override
    public List<LocalDoc> getUploadRecord(String UserName) {
        return null;
    }

    @Override
    public List<LocalDoc> getVisitRecord(String UserName) {
        return null;
    }

    @Override
    public List<LocalDoc> getFavoriteRecord(String UserName) {
        return null;
    }

    @Override
    public void deleteRecord(String userName, LocalDoc localDoc, int operationType) {

    }
}
