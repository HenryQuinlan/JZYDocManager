package team.JZY.DocManager.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


import team.JZY.DocManager.interfaces.DocInfoProvider;
import team.JZY.DocManager.model.DocInfo;
@Dao
public interface DocInfoDao {


    //要继承provider
    @Insert
    public List<Long> insert(List<DocInfo> docsInfo);

    @Query("select  * from DocInfo order by random() limit :amount")
    public List<DocInfo> request(long amount);

    @Query("select * from docinfo where classification = :classification order by random() limit :amount")
    public List<DocInfo> request(long amount, int classification);

    @Query("update DocInfo set visits = :docVisits where id = :docId")
    public void update(long docId, long docVisits);

    @Query("select id from docinfo order by id desc limit 1")
    public long getSize();

    @Query("delete from docinfo where 1")
    public void deleteAll();
}