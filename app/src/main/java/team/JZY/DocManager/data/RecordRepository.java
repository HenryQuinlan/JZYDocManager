package team.JZY.DocManager.data;

import android.content.Context;


import java.util.List;


import team.JZY.DocManager.interfaces.UserRecorder;

import team.JZY.DocManager.model.Record;


public class RecordRepository implements UserRecorder {
    //private LiveData<List<Record>> allWordsLive;
    private RecordDao recordDao;
    private static RecordRepository INSTANCE;
    private onRecordReceivedListener mListener;
    private RecordRepository() {
    }
    public static RecordRepository getInstance(Context context){
        if(INSTANCE == null) {
            INSTANCE = new RecordRepository();
            DocManagerDataBase docManagerDataBase = DocManagerDataBase.getInstance(context);
            INSTANCE.recordDao = docManagerDataBase.getRecordDao();
        }
        return INSTANCE;
    }
//    recordR.setLister(docsInfo->{
//        MainActivity.this.runOnUiThread(()->{
//            ui
//        });
//    }).get


    public RecordRepository setonRecordReceivedListener(onRecordReceivedListener listener) {
        mListener = listener;
        return this;
    }

    public void insertRecord(String username,int operation,long DocID) {
        new Thread(()->{
            //可能重写
            Record record=new Record(username,operation,DocID,"1",1);
            recordDao.insert(record);
        }).start();
    }
    @Override
    public List<Record> getDownloadRecord(String UserName) {
        new Thread(()->{
            List<Record> result = recordDao.findOperation(UserName,Record.TYPE_DOWNLOAD);
            if(mListener == null) return;
            mListener.receive(result);
            mListener=null;
        }).start();

        return null;
    }

    @Override
    public List<Record> getUploadRecord(String UserName) {
        new Thread(()->{
            List<Record> records=recordDao.findOperation(UserName,Record.TYPE_UPLOAD);
            if(mListener==null)return;
            mListener.receive(records);
            mListener=null;
        }).start();
        return null;
    }

    @Override
    public List<Record> getVisitRecord(String UserName) {
        new Thread(()->{
            List<Record> records=recordDao.findOperation(UserName,Record.TYPE_VISIT);
            if(mListener==null)return;
            mListener.receive(records);
            mListener=null;
        }).start();
        return null;
    }

    @Override
    public List<Record> getFavoriteRecord(String UserName) {
        new Thread(()->{
            List<Record> records=recordDao.findOperation(UserName,Record.TYPE_FAVORITE);
            if(mListener==null)return;
            mListener.receive(records);
            mListener=null;
        }).start();
        return null;
    }

    @Override
    public void deleteRecord(String UserName, int operationType,long DocID) {
        new Thread(()->{
            Record record=new Record(UserName,operationType,DocID,"1",1);
            record.setId(DocID);
            recordDao.deleteOperation(record);
            if(mListener==null)return;
            mListener=null;
        }).start();
    }


    public interface onRecordReceivedListener {
        public void receive(List<Record> records);
    }

//    public LiveData<List<Record>> getAllWordsLive() {
//        return allWordsLive;
//    }
//
//    void insertRecords(Record...records){
//        new InsertAsyncTask(recordDao).execute(records);
//    }
//    void updateRecords(Record...records){
//        new UpdateAsyncTask(recordDao).execute(records);
//    }
//    void deleteRecords(Record...records){
//        new DeleteAsyncTask(recordDao).execute(records);
//    }
//    void deleteALlRecords(Record...records){
//        new DeleteAllAsyncTask(recordDao).execute();
//    }
//    List<Record> findRecords(int type,String username){
//        return new FindAsyncTask(recordDao,type,username).execute();
//    }
//    static class InsertAsyncTask extends AsyncTask<Record,Void,Void> {
//        private RecordDao recordDao;
//        public InsertAsyncTask(RecordDao recordDao){
//            this.recordDao=recordDao;
//        }
//        @Override
//        protected Void doInBackground(Record... records) {
//            recordDao.insertOperation(records);
//            return null;
//        }
//    }
//
//    static class UpdateAsyncTask extends AsyncTask<Record,Void,Void>{
//        private RecordDao recordDao;
//        public UpdateAsyncTask(RecordDao recordDao){
//            this.recordDao=recordDao;
//        }
//        @Override
//        protected Void doInBackground(Record... records) {
//            recordDao.updateOperation(records);
//            return null;
//        }
//    }
//
//    static class DeleteAsyncTask extends AsyncTask<Record,Void,Void>{
//        private RecordDao recordDao;
//        public DeleteAsyncTask(RecordDao recordDao){
//            this.recordDao=recordDao;
//        }
//        @Override
//        protected Void doInBackground(Record... records) {
//            recordDao.deleteOperation(records);
//            return null;
//        }
//    }
//
//    static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
//        private RecordDao recordDao;
//        public DeleteAllAsyncTask(RecordDao recordDao){
//            this.recordDao=recordDao;
//        }
//        @Override
//        protected Void doInBackground(Void...Voids) {
//            recordDao.deleteAllRecord();
//            return null;
//        }
//    }
//
//    static class FindAsyncTask extends AsyncTask<Record,Void,List<Record>>{
//        private RecordDao recordDao;
//        private int operationType;
//        private String operator;
//        public FindAsyncTask(RecordDao recordDao, int operationType,String operator){
//            this.recordDao=recordDao;
//            this.operationType=operationType;
//            this.operator=operator;
//        }
//        @Override
//        protected List<Record> doInBackground(Record ...record) {
//            return recordDao.findOperation(operationType,operator);
//        }
//    }
}
