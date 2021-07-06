package team.JZY.DocManager.interfaces;

import android.content.Context;
import android.net.Uri;

import team.JZY.DocManager.model.LocalDoc;

public interface DocStorage {
    public void upload(Context context, Uri uri);
    public LocalDoc download(Context context,int DocId,String savePathDir);
}

