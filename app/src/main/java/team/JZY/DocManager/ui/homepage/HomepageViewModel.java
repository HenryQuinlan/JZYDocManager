package team.JZY.DocManager.ui.homepage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import team.JZY.DocManager.model.DocInfo;

public class HomepageViewModel extends ViewModel {

    private MutableLiveData<List<DocInfo>> liveDocsInfo;

    public LiveData<List<DocInfo>> getLiveInfo() {
        if(liveDocsInfo == null) {
            liveDocsInfo = new MutableLiveData<List<DocInfo>>(new ArrayList<DocInfo>());
        }
        return liveDocsInfo;
    }
    public void setDocsInfo(List<DocInfo> docsInfo) {
        if(liveDocsInfo == null) {
            liveDocsInfo = new MutableLiveData<List<DocInfo>>(docsInfo);
        }
        else {
            liveDocsInfo.setValue(docsInfo);
        }
    }

    // TODO: Implement the ViewModel
}