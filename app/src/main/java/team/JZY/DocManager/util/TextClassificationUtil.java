package team.JZY.DocManager.util;

import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class TextClassificationUtil {
    private static final String CHARSET = "UTF-8";
    private static final String ALGORITHM = "HmacSHA1";
    private static TextClassificationUtil INSTANCE;

    private static Map<ClassName,Integer>map;
    private TextClassificationUtil() {
        initMap();
    }

    public static TextClassificationUtil getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new TextClassificationUtil();
        }
        return INSTANCE;
    }
    private String sign(String encryptText, String encryptKey) throws Exception {

        byte[] data = encryptKey.getBytes(CHARSET);
        SecretKey secretKey = new SecretKeySpec(data, ALGORITHM);
        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(CHARSET);
        byte[] digest = mac.doFinal(text);

        return Base64.encodeToString(digest,Base64.NO_WRAP);
    }
    private String getStringToSign(TreeMap<String, Object> params) {
        StringBuilder s2s = new StringBuilder("GETnlp.tencentcloudapi.com/?");
        // 签名时要求对参数进行字典排序，此处用TreeMap保证顺序
        for (String k : params.keySet()) {
            s2s.append(k).append("=").append(params.get(k).toString()).append("&");
        }
        return s2s.toString().substring(0, s2s.length() - 1);
    }
    private String getUrl(TreeMap<String, Object> params) throws UnsupportedEncodingException {
        StringBuilder url = new StringBuilder("https://nlp.tencentcloudapi.com/?");
        // 实际请求的url中对参数顺序没有要求
        for (String k : params.keySet()) {
            // 需要对请求串进行urlencode，由于key都是英文字母，故此处仅对其value进行urlencode
            url.append(k).append("=").append(URLEncoder.encode(params.get(k).toString(), CHARSET)).append("&");
        }

        return url.toString().substring(0, url.length() - 1);
    }
    public int getClassification(String text) throws Exception {
        TreeMap<String, Object> params = new TreeMap<String, Object>(); // TreeMap可以自动排序
        // 实际调用时应当使用随机数，例如：params.put("Nonce", new Random().nextInt(java.lang.Integer.MAX_VALUE));
        params.put("Nonce", new Random().nextInt(java.lang.Integer.MAX_VALUE)); // 公共参数
        // 实际调用时应当使用系统当前时间，例如：   params.put("Timestamp", System.currentTimeMillis() / 1000);
        params.put("Timestamp", System.currentTimeMillis()/1000); // 公共参数
        params.put("SecretId", "AKIDCGBtNbaJm0yg9MJ0ThhT0IMBn8D5qLdi"); // 公共参数
        params.put("Action", "TextClassification"); // 公共参数
        params.put("Version", "2019-04-08"); // 公共参数
        params.put("Language","zh-CN");
        params.put("Region", "ap-guangzhou"); // 公共参数
        params.put("Text", text); // 业务参数
        params.put("Flag", 2); // 业务参数
        params.put("Signature", sign(getStringToSign(params), "ta4KMBm9JFyUqvnRr56rMdfRxDEzUklX")); // 公共参数
        String responseBody = sendRequestWithHttpURLConnection(getUrl(params));
        return parseJsonWithJSONObject(responseBody);
    }
    private String sendRequestWithHttpURLConnection(String urlString) throws IOException {

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(8000);
        connection.setReadTimeout(8000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder responseBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBody.append(line);
        }
        reader.close();
        connection.disconnect();
        return responseBody.toString();
    }
    private int parseJsonWithJSONObject(String responseBody) {
        try {
            JSONObject body = new JSONObject(responseBody);
            JSONObject response = body.optJSONObject("Response");
            JSONArray ClassArray = response.optJSONArray("Classes");
            if(ClassArray != null) {
                    JSONObject Class = ClassArray.getJSONObject(0);
                    String firstClassName = Class.optString("FirstClassName");
                    String secondClassName = Class.optString("SecondClassName");
                    ClassName className = new ClassName(firstClassName,"*");

                    if(map.containsKey(className))
                        return map.get(className);
                    className = new ClassName(firstClassName,secondClassName);
                    if(map.containsKey(className))
                        return map.get(className);
                    return 17;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 17;
    }
    private static class ClassName {
        String firstClassName;
        String secondClassName;

        public ClassName(String firstClassName, String secondClassName) {
            this.firstClassName = firstClassName;
            this.secondClassName = secondClassName;
        }

        @Override
        public boolean equals(@Nullable @org.jetbrains.annotations.Nullable Object obj) {
            if(this == obj) return true;
            if(obj instanceof ClassName) {
                ClassName className = (ClassName) obj;
                return firstClassName.equals(className.firstClassName) && secondClassName.equals(className.secondClassName);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return firstClassName.hashCode()*17+secondClassName.hashCode();
        }
    }
    private void initMap() {
        map = new ArrayMap<ClassName, Integer>();
        //map = new <ClassName,Integer>();
        map.put(new ClassName("旅游","*"),10);
        map.put(new ClassName("情感","*"),17);
        map.put(new ClassName("数码","*"),11);
        map.put(new ClassName("文化","读书"),9);
        map.put(new ClassName("文化","思想"),7);
        map.put(new ClassName("文化","民俗"),7);
        map.put(new ClassName("文化","文学"),9);
        map.put(new ClassName("文化","艺术"),17);
        map.put(new ClassName("军事","*"),17);
        map.put(new ClassName("摄影","*"),10);
        map.put(new ClassName("美食","*"),10);
        map.put(new ClassName("星座","*"),17);
        map.put(new ClassName("二次元","*"),16);
        map.put(new ClassName("宗教","*"),17);
        map.put(new ClassName("搞笑","*"),12);
        map.put(new ClassName("汽车","*"),11);
        map.put(new ClassName("美容","*"),13);
        map.put(new ClassName("时尚","*"),13);
        map.put(new ClassName("健康","*"),6);
        map.put(new ClassName("游戏","*"),12);
        map.put(new ClassName("宠物","*"),10);
        map.put(new ClassName("三农","*"),8);
        map.put(new ClassName("生活","*"),11);
        map.put(new ClassName("育儿","*"),11);
        map.put(new ClassName("科学","*"),5);
        map.put(new ClassName("历史","*"),7);
        map.put(new ClassName("教育","高等教育"),0);
        map.put(new ClassName("教育","外语学习"),0);
        map.put(new ClassName("教育","留学"),0);
        map.put(new ClassName("教育","考试"),0);
        map.put(new ClassName("教育","演讲"),17);
        map.put(new ClassName("教育","中学教育"),1);
        map.put(new ClassName("教育","高考"),1);
        map.put(new ClassName("教育","高中教育"),1);
        map.put(new ClassName("教育","小学教育"),1);
        map.put(new ClassName("教育","学前教育"),1);
        map.put(new ClassName("教育","教育产业"),17);
        map.put(new ClassName("天气","*"),11);
        map.put(new ClassName("房产","*"),11);
        map.put(new ClassName("职场","*"),2);
        map.put(new ClassName("法律","*"),17);
        map.put(new ClassName("彩票","*"),11);
        map.put(new ClassName("财经","*"),3);
        map.put(new ClassName("非体育分类","*"),17);
        map.put(new ClassName("科技","传统IT行业"),4);
        map.put(new ClassName("科技","创投"),3);
        map.put(new ClassName("科技","互联网+"),4);
        map.put(new ClassName("科技","互联网金融"),3);
        map.put(new ClassName("科技","机械"),17);
        map.put(new ClassName("科技","家电产业"),0);
        map.put(new ClassName("科技","科技大佬"),17);
        map.put(new ClassName("科技","科技股"),3);
        map.put(new ClassName("科技","科学"),5);
        map.put(new ClassName("科技","企业服务"),4);
        map.put(new ClassName("科技","内容文化产业"),17);
        map.put(new ClassName("科技","汽车科技"),11);
        map.put(new ClassName("科技","区块链"),4);
        map.put(new ClassName("科技","人工智能"),4);
        map.put(new ClassName("科技","软件工具"),4);
        map.put(new ClassName("科技","社交平台"),15);
        map.put(new ClassName("科技","网络安全"),4);
        map.put(new ClassName("科技","智能硬件"),4);
        map.put(new ClassName("科技","游戏"),12);
        map.put(new ClassName("社会","*"),15);
        map.put(new ClassName("时政","*"),15);
        map.put(new ClassName("体育","*"),14);
        map.put(new ClassName("娱乐","*"),12);
        map.put(new ClassName("人工智能","*"),4);

    }
}
