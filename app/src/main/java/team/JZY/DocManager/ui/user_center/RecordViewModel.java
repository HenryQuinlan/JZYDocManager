package team.JZY.DocManager.ui.user_center;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import team.JZY.DocManager.model.Record;
import team.JZY.DocManager.model.Record;

public class RecordViewModel extends ViewModel {

    private MutableLiveData<List<Record>> liveRecords;

    public LiveData<List<Record>> getLiveRecord() {
        if(liveRecords == null) {
            liveRecords = new MutableLiveData<List<Record>>(new ArrayList<Record>());
        }
        return liveRecords;
    }
    public void setRecords(List<Record> Records) {
        if(liveRecords == null) {
            liveRecords = new MutableLiveData<List<Record>>(Records);
        }
        else {
            liveRecords.setValue(Records);
        }
    }

}