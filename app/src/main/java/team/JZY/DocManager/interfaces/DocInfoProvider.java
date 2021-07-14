package team.JZY.DocManager.interfaces;

import java.util.List;

import team.JZY.DocManager.model.DocInfo;

public interface DocInfoProvider {

    public List<DocInfo> request(long amount);
    public List<DocInfo> request(long amount,int classification);
    public List<DocInfo> request(String searchKeyWord);
    public List<Long> insert(List<DocInfo> docsInfo);
    public void update(long docId,long docVisits);

}
