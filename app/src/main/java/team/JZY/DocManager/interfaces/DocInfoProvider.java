package team.JZY.DocManager.interfaces;

import java.util.List;

import team.JZY.DocManager.model.DocInfo;

public interface DocInfoProvider {
    public List<DocInfo> request(int amount);
    public List<DocInfo> request(int amount,int classification);
    public void update(DocInfo docInfo);
    public void modify(String docName,int docVisits);
}
