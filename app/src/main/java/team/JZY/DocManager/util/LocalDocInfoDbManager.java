package team.JZY.DocManager.util;

import java.util.List;

import team.JZY.DocManager.interfaces.DocInfoProvider;
import team.JZY.DocManager.model.DocInfo;

public class LocalDocInfoDbManager implements DocInfoProvider {
    @Override
    public List<DocInfo> request(int amount) {
        return null;
    }

    @Override
    public List<DocInfo> request(int amount, int classification) {
        return null;
    }

    @Override
    public void update(DocInfo docInfo) {

    }

    @Override
    public void modify(String docName, int docVisits) {

    }
}
