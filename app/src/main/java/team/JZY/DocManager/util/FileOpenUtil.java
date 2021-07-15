package team.JZY.DocManager.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import com.kathline.library.content.ZFileContent;
import com.kathline.library.util.ZFileLog;

import java.io.File;

import team.JZY.DocManager.DocManagerApplication;
import team.JZY.DocManager.MainActivity;
import team.JZY.DocManager.data.CosLoader;
import team.JZY.DocManager.model.DocInfo;

public class FileOpenUtil {

    public static final String DOC = "application/msword";
    public static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String PPT = "application/vnd.ms-powerpoint";
    public static final String PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    public static final String PDF = "application/pdf";


    public static void open(File file,int type,Context context) {
        if(type == ConvertUtil.TYPE_DOC)open(file,DOC,context);
        else if(type == ConvertUtil.TYPE_DOCX)open(file,DOCX,context);
        else if(type == ConvertUtil.TYPE_PPT)open(file,PPT,context);
        else if(type == ConvertUtil.TYPE_PPTX)open(file,PPTX,context);
        else if(type == ConvertUtil.TYPE_PDF)open(file,PDF,context);
        else ;
    }
    private static void open(File file,String type,Context context) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //intent.addFlags(Intent.FLAG_ACTIVITY_);
            Uri contentUri;
            if (Build.VERSION.SDK_INT >= 24) {
                String authority = context.getPackageName() + ".FileProvider";
                contentUri = FileProvider.getUriForFile(context, authority, file);
                intent.setDataAndType(contentUri, type);
            } else {
                contentUri = Uri.fromFile(file);
                intent.setDataAndType(contentUri, type);
            }
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public static void downloadAndView(DocManagerApplication.Activity activity,DocInfo docInfo) {
        long docId = docInfo.getId();
        String savePathDir = activity.getSavePathDir();
        String savedFileName = docInfo.getName()+"."+ ConvertUtil.TypeConvertToString(docInfo.getType());
        File file = new File(savePathDir,savedFileName);

        if(!file.exists()) {
            CosLoader cosLoader = new CosLoader(activity);
            cosLoader.setResultListener((download,result)->{
                if(result.equals("success")) {
                    FileOpenUtil.open(file,docInfo.getType(),activity);
                }
            }).download(activity,docInfo.getId(),savePathDir,savedFileName);
        }
        else {
            FileOpenUtil.open(file,docInfo.getType(),activity);
        }
    }

    public static void preview(DocManagerApplication.Activity activity,DocInfo docInfo) {
        long docId = docInfo.getId();
        String savePathDir = activity.getSavePathDir();
        String saveTempDir = activity.getTempPathDir();
        String savedFileName = docInfo.getName()+"."+ ConvertUtil.TypeConvertToString(docInfo.getType());
        File saveFile = new File(savePathDir,savedFileName);
        File tempFile = new File(saveTempDir,savedFileName);
        if(saveFile.exists()){
            FileOpenUtil.open(saveFile,docInfo.getType(),activity);
        }else if(tempFile.exists()) {
            FileOpenUtil.open(tempFile,docInfo.getType(),activity);
        }else {
            CosLoader cosLoader = new CosLoader(activity);
            cosLoader.setResultListener((download,result)->{
                if(result.equals("success")) {
                    FileOpenUtil.open(tempFile,docInfo.getType(),activity);
                }
                else {

                }
            }).download(activity,docInfo.getId(),saveTempDir,savedFileName);
        }
    }
    public static boolean isFileDownloaded(DocManagerApplication.Activity activity,DocInfo docInfo) {
        long docId = docInfo.getId();
        String savePathDir = activity.getSavePathDir();
        String savedFileName = docInfo.getName()+"."+ ConvertUtil.TypeConvertToString(docInfo.getType());
        File file = new File(savePathDir,savedFileName);
        return file.exists();
    }
}
