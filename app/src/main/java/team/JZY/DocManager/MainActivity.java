package team.JZY.DocManager;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kathline.library.common.ZFileManageHelp;
import com.kathline.library.content.ZFileBean;
import com.kathline.library.content.ZFileConfiguration;
import com.kathline.library.content.ZFileContent;
import com.kathline.library.listener.ZFileListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import team.JZY.DocManager.data.DocInfoRepository;
import team.JZY.DocManager.data.RecordRepository;
import team.JZY.DocManager.databinding.ActivityMainBinding;
import team.JZY.DocManager.databinding.UploadPopupwindowBinding;
import team.JZY.DocManager.model.DocInfo;
import team.JZY.DocManager.model.Record;
import team.JZY.DocManager.model.User;
import team.JZY.DocManager.ui.UserViewModel;
import team.JZY.DocManager.data.CosLoader;
import team.JZY.DocManager.util.ConvertUtil;

public class MainActivity extends DocManagerApplication.Activity {


    private static final String LOGGED_IN_USER_NAME_KEY = "loggedInUserNameKey";
    private static final  int FILE_PICK_REQUEST_CODE = 4399;
    public static final String DOC = "application/msword";
    public static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String PPT = "application/vnd.ms-powerpoint";
    public static final String PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    public static final String PDF = "application/pdf";
    private ActivityMainBinding binding;
    private UserViewModel userViewModel;
    private DocInfoRepository docInfoRepository;
    private RecordRepository recordRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        Intent intent = getIntent();
        String userName = intent.getStringExtra(LOGGED_IN_USER_NAME_KEY);
        User user = new User(userName);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.setUser(user);

        docInfoRepository = DocInfoRepository.getInstance(this);

        initNavigation();
    }

    private void initNavigation(){

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        MenuItem menuItemUpload = binding.navView.getMenu().findItem(R.id.menu_item_upload);
        menuItemUpload.setOnMenuItemClickListener(m-> onMenuItemUploadClicked());
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homepage_fragment,
                R.id.classification_fragment,
                R.id.local_download_fragment,
                R.id.user_center_fragment)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public boolean onMenuItemUploadClicked()
    {
        createPopupWindow();
        return false;
    }

    private void createPopupWindow(){
        UploadPopupwindowBinding popupBinding = UploadPopupwindowBinding.inflate(LayoutInflater.from(this),null,false);

        final PopupWindow popupWindow = new PopupWindow(popupBinding.getRoot(),
                400, 350, true);

        popupWindow.setAnimationStyle(R.anim.anim_pop);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popupWindow设置一个背景才有效

        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popupWindow.showAsDropDown(binding.navView, 340, -500);
        popupBinding.getRoot().setOnClickListener(v->filePick());
        popupBinding.buttonUpload.setOnClickListener(v->filePick());
        popupBinding.textView.setOnClickListener(v->filePick());

    }

    private void filePick() {
        //TODO MORE_SUPPORT TYPE
        if(!checkPermission())return;
        final ZFileConfiguration configuration = new ZFileConfiguration.Build()
                //.resources(resources)
                .maxLength(9)
                .boxStyle(ZFileConfiguration.STYLE1)
                .fileFilterArray(Build.VERSION.SDK_INT >= 30 ?
                        new String[]{DOC,DOCX,PPT,PPTX,PDF} :
                        new String[]{"doc","docx","ppt","pptx","pdf"})
                .build();
        ZFileContent.getZFileHelp()
                .setConfiguration(configuration)
                .init(new ZFileListener.ZFileImageListener() {
                    @Override
                    public void loadImage(ImageView imageView, File file) {
                        Glide.with(imageView.getContext())
                                .load(file)
                                .apply(new RequestOptions().placeholder(R.drawable.ic_zfile_other).error(R.drawable.ic_zfile_other))
                                .into(imageView);
                    }
                });
        ZFileContent.getZFileHelp().start(this,(requestCode,resultCode,data)->{
            List<ZFileBean> fileList = ZFileManageHelp.getInstance().getSelectData(getBaseContext(), requestCode, resultCode, data);
            if (fileList == null || fileList.size() <= 0) {
                return;
            }
            uploadAction(fileList);
        });
    }

    private void uploadAction(List<ZFileBean> fileList) {

        new Thread(()-> {
                List<DocInfo>docsInfo = new ArrayList<DocInfo>();
                List<Uri>docsUri = new ArrayList<Uri>();
                for(ZFileBean file:fileList) {
                    try {
                        docsInfo.add(ConvertUtil.FileConvertToDocInfo(file));
                        docsUri.add(Uri.fromFile(new File(file.getFilePath())));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for(int i=0;i<docsInfo.size();i++){
                    recordRepository.insertRecord(userViewModel.getUser().getName(),Record.TYPE_UPLOAD,docsInfo.get(i).getId());
                }

               docInfoRepository.setInsertListener(docsId ->{
                            CosLoader cosLoader = new CosLoader(MainActivity.this);
                            cosLoader.setResultListener((upload,result)->{

                            }).upload(this,docsUri,docsId);
               }).insert(docsInfo);

        }).start();

    }

    public boolean checkPermission()
    {
        if(!Environment.isExternalStorageManager()) {
            String message = String.format("您拒绝了相关权限，无法正常使用上传功能。请前往 设置->应用管理->%s->权限管理中启用权限", getString(R.string.app_name));

            AlertDialog alertDialog = (new AlertDialog.Builder(this)).
                    setTitle("权限被禁用").
                    setMessage(message).
                    setCancelable(false).
                    setNegativeButton("返回", (d,w)->{}).
                    setPositiveButton("去设置",(d,w)-> {
                                Intent intent;
                                if (Build.VERSION.SDK_INT >= 30) {
                                    intent = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION");
                                    intent.setData(Uri.parse("package:" + getPackageName()));
                                }

                                else {
                                    intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                                    intent.setData(Uri.parse("package:" + getPackageName()));
                                }
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                    ).create();
            alertDialog.show();
            return false;
        }
        return true;
    }


}