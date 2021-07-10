package team.JZY.DocManager.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import team.JZY.DocManager.interfaces.DocInfoProvider;
import team.JZY.DocManager.model.DocInfo;
@Dao
public interface DocInfoDao extends DocInfoProvider {


    //要继承provider
        @Override
        @Insert//
        Long[] insert(DocInfo...docInfos);//返回id

        @Override//
        @Query("update DocInfo set visits = :docVisits where id = :docId")
        void update(Long docId,int docVisits);

        @Override//
        @Query("select  * from DocInfo order by random() limit :amount")
        List<DocInfo> request(int amount);//

    @Override
    @Query("select * from docinfo where classification = :classification order by random() limit :amount")
    List<DocInfo> request(int amount, int classification);

    @Query("select id from docinfo order by id desc limit 1")
    int getSize();
//    @Override
//    ---------------
//    List<DocInfo> request(String searchKeyWord);

        @Delete//
        void deleteDocInfo(DocInfo...docInfos);

        @Query("delete from docinfo")//
        void deleteAllDocInfo();


        @Query("select  * from DocInfo order by id desc")//
        List<DocInfo> getAllDocInfo();//获得全部的数据

}
