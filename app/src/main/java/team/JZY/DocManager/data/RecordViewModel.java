//package team.JZY.DocManager.data;
//
//import android.app.Application;
//import android.os.AsyncTask;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//
//import java.util.List;
//
//import team.JZY.DocManager.model.Record;
//
//public class RecordViewModel extends AndroidViewModel {
//    private RecordDao recordDao;
//    private RecordRepository recordRepository;
//    public RecordViewModel(@NonNull Application application) {
//        super(application);
//        recordRepository=RecordRepository.getInstance(application);
//    }
//
//    public void insertRecords(Record...records){
//        recordRepository.insertRecord();
//    }
//    public void updateRecords(Record...records){
//        recordRepository.;
//    }
//    public void deleteRecords(Record...records){
//        recordRepository.deleteRecords(records);
//    }
//    public void deleteALlRecords(){
//        recordRepository.deleteALlRecords();
//    }
//    public List<Record> findRecords(int type,String username){
//        return recordRepository.findRecords(type,username);
//    }
//    public boolean judgeFile(int docID,int operationType,String operator){
//        List<Record> records=recordRepository.findRecords(operationType,operator);
//        for(int i=0;i<records.size();i++){
//            if(records.get(i).getDocID()==docID){
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//}
//
