package team.JZY.DocManager.data;

import android.content.Context;
import android.net.Uri;

import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.transfer.COSXMLUploadTask;
import com.tencent.cos.xml.transfer.TransferConfig;
import com.tencent.cos.xml.transfer.TransferManager;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider;

import team.JZY.DocManager.interfaces.DocStorage;
import team.JZY.DocManager.model.Record;

public class CosLoader implements DocStorage {
    private QCloudCredentialProvider myCredentialProvider;
    private CosXmlService cosXmlService;
    private  void InitQCloudCredentialProvider(Context context){
        if(myCredentialProvider == null) {
            String secretId = "AKID96nEOIvMaaKnztXu1rRjlW1FExPbWBZh"; //永久密钥 secretId
            String secretKey = "tVqvjGWVEOIvIODl34nFQKxJOCj0ziAL"; //永久密钥 secretKey
            myCredentialProvider = new ShortTimeCredentialProvider(secretId, secretKey, 300);
            // 存储桶所在地域简称，例如广州地区是 ap-guangzhou
            String region = "ap-nanjing";
// 创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
            CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                    .setRegion(region)
                    .isHttps(true) // 使用 HTTPS 请求, 默认为 HTTP 请求
                    .builder();

// 初始化 COS Service，获取实例
            cosXmlService = new CosXmlService(context,serviceConfig, myCredentialProvider);
        }
    }
    public CosLoader(Context context){
        myCredentialProvider=null;
        cosXmlService=null;
        InitQCloudCredentialProvider(context);
    }
    @Override
    public void upload(Context context, Uri uri,Long uploadFileId) {
        // 初始化 TransferConfig，这里使用默认配置，如果需要定制，请参考 SDK 接口文档
        TransferConfig transferConfig = new TransferConfig.Builder().build();
// 初始化 TransferManager
        TransferManager transferManager = new TransferManager(cosXmlService,
                transferConfig);
        String bucket = "myandroid-1306390087"; //存储桶，格式：BucketName-APPID
//        String srcPath = new File(context.getCacheDir(), uploadFileId)
//                .toString(); //本地文件的绝对路径
        String cosPath = uploadFileId.toString(); //对象在存储桶中的位置标识符，即称对象键
        String uploadId = null;
        COSXMLUploadTask cosxmlUploadTask=transferManager.upload(bucket,cosPath,uri,uploadId);
    }

    @Override
    public void download(Context context, long DocId, String savePathDir) {


    }
}
