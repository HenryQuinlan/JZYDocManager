package team.JZY.DocManager.data;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import team.JZY.DocManager.model.DocInfo;

public class DocInfoRepository {
    //private LiveData<List<DocInfo>> allDocLive;
    private DocInfoDao docInfoDao;
    public DocInfoRepository(Context context){
        DocManagerDataBase docManagerDataBase=DocManagerDataBase.getInstance(context.getApplicationContext());
        docInfoDao= docManagerDataBase.getDocInfoDao();
//        allDocLive=docInfoDao.getAllDocInfo();
    }

    public Long[] insertDocInfo(DocInfo docInfo){
        return new Insert(docInfoDao).doInBackground(docInfo);
    }//
    public void DleteDocInfo(DocInfo...docInfos){
        new Delete(docInfoDao).execute(docInfos);
    }//
    public void DeleteAllDocInfo(){
        new DeleteAll(docInfoDao).execute();
    }//
    public List<DocInfo> RandomRequestDocInfo(int amount){
        return new Request(docInfoDao,amount).doInBackground();
    }//
    public List<DocInfo> GetAllDocInfo(){
        return new GetAllDocInfo(docInfoDao).doInBackground();
    }//
    public void UpdateDocInfo(Long theID,int visiter){
        new Update(docInfoDao,theID,visiter).doInBackground();
    }//
    public List<DocInfo> Request(int amount,int classification){
        return new OtherRequest(docInfoDao,amount,classification).doInBackground();
    }

    static class Insert extends AsyncTask<DocInfo,Void,Long[]> {
        private DocInfoDao docInfoDao;

        public Insert(DocInfoDao docInfoDao) {
            this.docInfoDao = docInfoDao;
        }
        @Override
        protected Long[] doInBackground(DocInfo ...docInfos) {
            return docInfoDao.insert(docInfos);
        }
    }
    static class Delete extends AsyncTask<DocInfo,Void,Void>{
        private DocInfoDao docInfoDao;

        public Delete(DocInfoDao docInfoDao) {
            this.docInfoDao = docInfoDao;
        }
        @Override
        protected Void doInBackground(DocInfo ...docInfos) {
            docInfoDao.deleteDocInfo(docInfos);
            return null;
        }
    }
    static class  DeleteAll extends AsyncTask<DocInfo,Void,Void>{
        private DocInfoDao docInfoDao;
        public DeleteAll(DocInfoDao docInfoDao) {
            this.docInfoDao = docInfoDao;
        }
        @Override
        protected Void doInBackground(DocInfo ...docInfos) {
            docInfoDao.deleteAllDocInfo();
            return null;
        }
    }
    static class  Request extends AsyncTask<DocInfo,Void,List<DocInfo>>{
        private DocInfoDao docInfoDao;
        private int amount;

        public Request(DocInfoDao docInfoDao,int amount) {
            this.docInfoDao = docInfoDao;
            this.amount=amount;
        }
        @Override
        protected List<DocInfo> doInBackground(DocInfo ...docInfos) {
            return docInfoDao.request(amount);
        }
    }
    static class  GetAllDocInfo extends AsyncTask<DocInfo,Void,List<DocInfo>>{
        private DocInfoDao docInfoDao;
        public GetAllDocInfo(DocInfoDao docInfoDao) {
            this.docInfoDao = docInfoDao;
        }
        @Override
        protected List<DocInfo> doInBackground(DocInfo ...docInfos) {
            return docInfoDao.getAllDocInfo();
        }
    }
    static class  Update extends AsyncTask<DocInfo,Void,Void>{
        private DocInfoDao docInfoDao;
        private Long theid;
        private int visiters;
        public Update(DocInfoDao docInfoDao,Long theid,int visiters) {
            this.docInfoDao = docInfoDao;
            this.theid=theid;
            this.visiters=visiters;
        }
        @Override
        protected Void doInBackground(DocInfo ...docInfos) {
           docInfoDao.update(theid,visiters);
           return null;
        }
    }
    static class  OtherRequest extends AsyncTask<DocInfo,Void,List<DocInfo>>{
        private DocInfoDao docInfoDao;
        private int amount;
        private int classification;

        public OtherRequest(DocInfoDao docInfoDao,int amount,int classification) {
            this.docInfoDao = docInfoDao;
            this.amount=amount;
            this.classification=classification;
        }
        @Override
        protected List<DocInfo> doInBackground(DocInfo ...docInfos) {
            return docInfoDao.request(amount,classification);
        }
    }

}

