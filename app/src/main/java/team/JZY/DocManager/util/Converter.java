package team.JZY.DocManager.util;

import android.util.Log;

import com.kathline.library.content.ZFileBean;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team.JZY.DocManager.model.DocInfo;

public class Converter {
//    Map<String,Integer>classificationMap;
    private static final Map<String,Integer>typeMap;
    private static final List<String>classificationList;
    private static final List<String>typeList;


    public static int TYPE_DOC = 0;
    public static int TYPE_DOCX = 1;
    public static int TYPE_PPT = 2;
    public static int TYPE_PPTX = 3;
    public static int TYPE_PDF = 4;

    public static int CLASSIFICATION_COLLEGE = 0;
    public static int CLASSIFICATION_SCHOOL = 1;
    public static int CLASSIFICATION_WORKPLACE = 2;
    public static int CLASSIFICATION_ECONOMY = 3;
    public static int CLASSIFICATION_IT = 4;
    public static int CLASSIFICATION_SCIENCE = 5;
    public static int CLASSIFICATION_HEALTH = 6;
    public static int CLASSIFICATION_CULTURE = 7;
    public static int CLASSIFICATION_AGRICULTURE = 8;
    public static int CLASSIFICATION_LITERATURE = 9;
    public static int CLASSIFICATION_LIFE = 10;
    public static int CLASSIFICATION_AMUSEMENT = 11;
    public static int CLASSIFICATION_FASHION = 12;
    public static int CLASSIFICATION_SPORT = 13;
    public static int CLASSIFICATION_SOCIETY = 14;
    public static int CLASSIFICATION_ANIME = 15;
    public static int CLASSIFICATION_OTHER = 16;


    static  {
//        classificationMap = new HashMap<String, Integer>();
//        classificationMap.add
        typeList = new ArrayList<String>(
                Arrays.asList("doc","docx","ppt","pptx","pdf")
        );
        classificationList = new ArrayList<String>(
                Arrays.asList("大学","中小学","职场","经济","计算机","科学","健康","人文",
                    "三农","文学","生活","娱乐","时尚","体育","社会","二次元",
                    "其他")
        );
        typeMap = new HashMap<String, Integer>();
        typeMap.put("doc",1);
        typeMap.put("docx",2);
        typeMap.put("ppt",3);
        typeMap.put("pptx",4);
        typeMap.put("pdf",5);
    }
    public static int getType(String type) {
        return typeMap.get(type);
    }
    public static String getType(int type) {
        return typeList.get(type);
    }
    public static String getClassification(int classification) {
        return classificationList.get(classification);
    }
    public static DocInfo getInfo(ZFileBean file) throws Exception {
        int nameStartIndex=file.getFileName().lastIndexOf(File.separatorChar)+1;
        String fileName = file.getFileName().substring(nameStartIndex);
        Log.d("SLDJDK", fileName);
        int preIndex = fileName.lastIndexOf(".") + 1;
        int lastIndex = fileName.length();
        int type = getType(fileName.substring(preIndex, lastIndex));
        String name = fileName.substring(0,preIndex-1);
        String size = file.getSize();
        int visits = 0;
        int classification =TextClassification.getInstance().getClassification(name);
        return new DocInfo(name,type,classification,visits,size);
    }
}
