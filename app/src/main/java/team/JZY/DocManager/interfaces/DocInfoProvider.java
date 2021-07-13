package team.JZY.DocManager.interfaces;

import java.util.List;

import team.JZY.DocManager.model.DocInfo;

public interface DocInfoProvider {

    public List<DocInfo> request(int amount);
    public List<DocInfo> request(int amount,int classification);
    //public List<DocInfo> request(String searchKeyWord);
    public Long[] insert(DocInfo...docInfos);
    public void update(Long docId,int docVisits);
}
