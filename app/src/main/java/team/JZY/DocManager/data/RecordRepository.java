package team.JZY.DocManager.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import team.JZY.DocManager.model.Record;

public class RecordRepository {
    private LiveData<List<Record>> allWordsLive;
    private RecordDao recordDao;
    public RecordRepository(Context context){
        RecordDatabase recordDatabase=RecordDatabase.getDatabase(context.getApplicationContext());
        RecordDao recordDao=recordDatabase.getRecordDao();
        allWordsLive=recordDao.getAllRecordsLive();
    }

    public LiveData<List<Record>> getAllWordsLive() {
        return allWordsLive;
    }

    void insertRecords(Record...records){
        new InsertAsyncTask(recordDao).execute(records);
    }
    void updateRecords(Record...records){
        new UpdateAsyncTask(recordDao).execute(records);
    }
    void deleteRecords(Record...records){
        new DeleteAsyncTask(recordDao).execute(records);
    }
    void deleteALlRecords(Record...records){
        new DeleteAllAsyncTask(recordDao).doInBackground();
    }
    List<Record> findRecords(int type,String username){
        return new FindAsyncTask(recordDao,type,username).doInBackground();
    }
    static class InsertAsyncTask extends AsyncTask<Record,Void,Void> {
        private RecordDao recordDao;
        public InsertAsyncTask(RecordDao recordDao){
            this.recordDao=recordDao;
        }
        @Override
        protected Void doInBackground(Record... records) {
            recordDao.insertOperation(records);
            return null;
        }
    }

    static class UpdateAsyncTask extends AsyncTask<Record,Void,Void>{
        private RecordDao recordDao;
        public UpdateAsyncTask(RecordDao recordDao){
            this.recordDao=recordDao;
        }
        @Override
        protected Void doInBackground(Record... records) {
            recordDao.updateOperation(records);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<Record,Void,Void>{
        private RecordDao recordDao;
        public DeleteAsyncTask(RecordDao recordDao){
            this.recordDao=recordDao;
        }
        @Override
        protected Void doInBackground(Record... records) {
            recordDao.deleteOperation(records);
            return null;
        }
    }

    static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private RecordDao recordDao;
        public DeleteAllAsyncTask(RecordDao recordDao){
            this.recordDao=recordDao;
        }
        @Override
        protected Void doInBackground(Void...Voids) {
            recordDao.deleteAllRecord();
            return null;
        }
    }

    static class FindAsyncTask extends AsyncTask<Record,Void,List<Record>>{
        private RecordDao recordDao;
        private int operationType;
        private String operator;
        public FindAsyncTask(RecordDao recordDao, int operationType,String operator){
            this.recordDao=recordDao;
            this.operationType=operationType;
            this.operator=operator;
        }
        @Override
        protected List<Record> doInBackground(Record ...record) {
            return recordDao.findOperation(operationType,operator);
        }
    }
}
