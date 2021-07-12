package team.JZY.DocManager.interfaces;

import android.content.Context;
import android.net.Uri;

import java.util.List;

import team.JZY.DocManager.model.Record;

public interface DocStorage {
    public void upload(Context context, Uri srcUri,long docId);
    public void upload(Context context, List<Uri> srcUris, List<Long> docsId);
    public void download(Context context, long DocId, String savePathDir ,String savedFileName);
}

