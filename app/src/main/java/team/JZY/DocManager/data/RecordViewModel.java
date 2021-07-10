package team.JZY.DocManager.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import team.JZY.DocManager.model.Record;

public class RecordViewModel extends AndroidViewModel {
    private RecordDao recordDao;
    private RecordRepository recordRepository;
    public RecordViewModel(@NonNull Application application) {
        super(application);
        recordRepository=new RecordRepository(application);
    }

    public LiveData<List<Record>> getAllWordsLive() {
        return recordRepository.getAllWordsLive();
    }
    void insertRecords(Record...records){
        recordRepository.insertRecords(records);
    }
    void updateRecords(Record...records){
        recordRepository.updateRecords(records);
    }
    void deleteRecords(Record...records){
        recordRepository.deleteRecords(records);
    }
    void deleteALlRecords(){
        recordRepository.deleteALlRecords();
    }
    List<Record> findRecords(int type,String username){
        return recordRepository.findRecords(type,username);
    }


}

