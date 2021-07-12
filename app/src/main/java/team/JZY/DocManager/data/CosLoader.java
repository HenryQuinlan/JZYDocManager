package team.JZY.DocManager.data;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.transfer.COSXMLDownloadTask;
import com.tencent.cos.xml.transfer.COSXMLUploadTask;
import com.tencent.cos.xml.transfer.TransferConfig;
import com.tencent.cos.xml.transfer.TransferManager;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider;

import java.util.List;

import team.JZY.DocManager.R;
import team.JZY.DocManager.interfaces.DocStorage;
import team.JZY.DocManager.model.Record;

public class CosLoader implements DocStorage {
    private QCloudCredentialProvider myCredentialProvider;
    private CosXmlService cosXmlService;
    private OnResultListener mListener;
    private  void InitQCloudCredentialProvider(Context context){
        if(myCredentialProvider == null) {
            String secretId = "AKID96nEOIvMaaKnztXu1rRjlW1FExPbWBZh"; //永久密钥 secretId
            String secretKey = "tVqvjGWVEOIvIODl34nFQKxJOCj0ziAL"; //永久密钥 secretKey
            myCredentialProvider = new ShortTimeCredentialProvider(secretId, secretKey, 5000);
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
    public void upload(Context context, Uri srcUri,long docId) {
        // 初始化 TransferConfig，这里使用默认配置，如果需要定制，请参考 SDK 接口文档
        TransferConfig transferConfig = new TransferConfig.Builder().build();
// 初始化 TransferManager
        TransferManager transferManager = new TransferManager(cosXmlService,
                transferConfig);
        String bucket = "myandroid-1306390087"; //存储桶，格式：BucketName-APPID
//        String srcPath = new File(context.getCacheDir(), uploadFileId)
//                .toString(); //本地文件的绝对路径
        String cosPath = String.valueOf(docId); //对象在存储桶中的位置标识符，即称对象键
        String uploadId = null;
        COSXMLUploadTask cosxmlUploadTask=transferManager.upload(bucket,cosPath,srcUri,uploadId);
        cosxmlUploadTask.setCosXmlResultListener(new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                if(mListener!=null)mListener.onResult("upload","success");
                mListener = null;
            }
            @Override
            public void onFail(CosXmlRequest request,
                               CosXmlClientException clientException,
                               CosXmlServiceException serviceException) {
                if (clientException != null) {
                    clientException.printStackTrace();
                } else {
                    serviceException.printStackTrace();
                }
                if(mListener != null)mListener.onResult("download","fail");
                mListener = null;
            }
        });
    }
    @Override
    public void upload(Context context, List<Uri> srcUris, List<Long> docsId) {
        // 初始化 TransferConfig，这里使用默认配置，如果需要定制，请参考 SDK 接口文档
        if(srcUris.size() != docsId.size())return ;
        TransferConfig transferConfig = new TransferConfig.Builder().build();
// 初始化 TransferManager
        TransferManager transferManager = new TransferManager(cosXmlService,
                transferConfig);
        String bucket = "myandroid-1306390087"; //存储桶，格式：BucketName-APPID
        String uploadId = null;

        for(int i = 0; i <docsId.size() ; i++) {
            String cosPath = docsId.get(i).toString();
            Uri srcUri = srcUris.get(i);
            COSXMLUploadTask cosxmlUploadTask=transferManager.upload(bucket,cosPath,srcUri,uploadId);
            if(i == docsId.size()-1)cosxmlUploadTask.setCosXmlResultListener(new CosXmlResultListener() {
                @Override
                public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                    if(mListener!=null)mListener.onResult("upload","success");
                    mListener = null;
                }
                @Override
                public void onFail(CosXmlRequest request,
                                   CosXmlClientException clientException,
                                   CosXmlServiceException serviceException) {
                    if (clientException != null) {
                        clientException.printStackTrace();
                    } else {
                        serviceException.printStackTrace();
                    }
                    if(mListener != null)mListener.onResult("download","fail");
                    mListener = null;
                }
            });
        }
    }


    @Override
    public void download(Context context, long docId, String savePathDir , String savedFileName) {

        TransferConfig transferConfig = new TransferConfig.Builder().build();
//初始化 TransferManager
        TransferManager transferManager = new TransferManager(cosXmlService,
                transferConfig);

        String bucket = "myandroid-1306390087"; //存储桶，格式：BucketName-APPID
        String cosPath = String.valueOf(docId); //对象在存储桶中的位置标识符，即称对象键
//本地目录路径
//本地保存的文件名，若不填（null），则与 COS 上的文件名一样

        Context applicationContext = context.getApplicationContext(); // application
// context
        COSXMLDownloadTask cosxmlDownloadTask =
                transferManager.download(applicationContext,
                        bucket, cosPath, savePathDir, savedFileName);
        cosxmlDownloadTask.setCosXmlResultListener(new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult cosXmlResult) {
                if(mListener!=null)mListener.onResult("upload","success");
                mListener = null;
            }
            @Override
            public void onFail(CosXmlRequest request,
                               CosXmlClientException clientException,
                               CosXmlServiceException serviceException) {
                if (clientException != null) {
                    clientException.printStackTrace();
                } else {
                    serviceException.printStackTrace();
                }
                if(mListener != null)mListener.onResult("download","fail");
                mListener = null;
            }
        });
    }

    public CosLoader setResultListener(OnResultListener listener) {
        mListener = listener;
        return this;
    }
    public interface OnResultListener {
        void onResult(String loadType,String resultType);
    }
}
