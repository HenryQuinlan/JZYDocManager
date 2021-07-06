package team.JZY.DocManager.util;

import android.content.Context;
import android.net.Uri;

import team.JZY.DocManager.interfaces.DocStorage;
import team.JZY.DocManager.model.LocalDoc;

public class CosLoader implements DocStorage {
    @Override
    public void upload(Context context, Uri uri) {

    }

    @Override
    public LocalDoc download(Context context, int DocId, String savePathDir) {
        return null;
    }
}
