package team.JZY.DocManager.data;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import team.JZY.DocManager.interfaces.DocInfoProvider;
import team.JZY.DocManager.model.DocInfo;

public class DocInfoRepository implements DocInfoProvider {

    private DocInfoDao docInfoDao;
    private static DocInfoRepository INSTANCE;
    private InsertListener mInsertListener;
    private RequestListener mRequestListener;
    private RecommendListener mRecommendListener;
    private DocInfoRepository(){

    }
    public static DocInfoRepository getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE =new DocInfoRepository();
            DocManagerDataBase docManagerDataBase = DocManagerDataBase.getInstance(context);
            INSTANCE.docInfoDao = docManagerDataBase.getDocInfoDao();
        }
        return INSTANCE;
    }
    public DocInfoRepository setInsertListener(InsertListener listener) {
        mInsertListener = listener;
        return this;
    }
    public DocInfoRepository setRequestListener(RequestListener listener) {
        mRequestListener = listener;
        return this;
    }

    public DocInfoRepository setRecommendListener(RecommendListener listener) {
        mRecommendListener = listener;
        return this;
    }

    @Override
    public List<DocInfo> request(long amount) {
        new Thread(()->{
            long size = docInfoDao.getSize();
            List<DocInfo> result = docInfoDao.request(amount<size?amount:size);
            if(mRequestListener != null)mRequestListener.getResponse(result);
            mRequestListener = null;
        }).start();
        return null;
    }

    @Override
    public List<DocInfo> request(long amount, int classification) {
        new Thread(()->{
            long size = docInfoDao.getSize();
            List<DocInfo> result = docInfoDao.request(amount<size?amount:size,classification);
            if(mRequestListener != null)mRequestListener.getResponse(result);
            mRequestListener = null;
        }).start();
        return null;
    }

    @Override
    public List<DocInfo> request(String searchKeyWord) {
        new Thread(()->{
            long size = docInfoDao.getSize();
            List<DocInfo> result = docInfoDao.request(searchKeyWord);
            if(mRequestListener != null)mRequestListener.getResponse(result);
            mRequestListener = null;
        }).start();
        return null;
    }

    @Override
    public List<String> searchRecommend(String searchKeyWord) {
        new Thread(()->{
            List<String> result = docInfoDao.recommend(searchKeyWord);
            if(mRecommendListener != null)mRecommendListener.getResponse(result);
            mRecommendListener = null;
        }).start();
        return null;
    }

    @Override
    public List<Long> insert(List<DocInfo> docsInfo) {
        new Thread(()->{
            List<Long> docsId = docInfoDao.insert(docsInfo);
            if(mInsertListener != null)mInsertListener.getResponse(docsId);
            mInsertListener = null;
        }).start();
        return null;
    }

    @Override
    public void update(long docId, long docVisits) {
        new Thread(()->{
            docInfoDao.update(docId,docVisits);
        }).start();
    }

    public interface RequestListener {
        public void getResponse(List<DocInfo> docsInfo);
    }

    public interface InsertListener {
        public void getResponse(List<Long> docsId);
    }
    public interface RecommendListener {
        public void getResponse(List<String> docsName);
    }

}

