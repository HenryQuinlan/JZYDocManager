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

import team.JZY.DocManager.MainActivity;

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
//    @SuppressLint("WrongConstant")
//    private static void open(String filePath, String type, Context context) {
//        try {
//            Intent intent = new Intent("android.intent.action.VIEW");
//            intent.addCategory("android.intent.category.DEFAULT");
//            intent.addFlags(268435456);
//            intent.addFlags(1);
//            Uri contentUri;
//            if (Build.VERSION.SDK_INT >= 24) {
//                String authority = context.getPackageName() + ".FileProvider";
//                contentUri = FileProvider.getUriForFile(context, authority, new File(filePath));
//                intent.setDataAndType(contentUri, type);
//            } else {
//                contentUri = Uri.fromFile(new File(filePath));
//                intent.setDataAndType(contentUri, type);
//            }
//
//            context.startActivity(intent);
//        } catch (Exception var6) {
//            var6.printStackTrace();
//            ZFileLog.e("ZFileConfiguration.authority 未设置？？？");
//            ZFileContent.toast(context, "文件类型可能不匹配或找不到打开该文件类型的程序，打开失败");
//        }
//    }
}
