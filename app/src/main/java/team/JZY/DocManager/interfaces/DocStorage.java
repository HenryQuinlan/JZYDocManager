package team.JZY.DocManager.interfaces;

import android.content.Context;
import android.net.Uri;

import team.JZY.DocManager.model.Record;

public interface DocStorage {
    public void upload(Context context, Uri uri,Long uploadFileId);
    public void download(Context context, long DocId, String savePathDir);
}

