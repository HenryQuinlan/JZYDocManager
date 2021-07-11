package team.JZY.DocManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.kathline.library.common.ZFileManageHelp;
import com.kathline.library.content.MimeType;
import com.kathline.library.content.ZFileBean;
import com.kathline.library.content.ZFileConfiguration;
import com.kathline.library.content.ZFileContent;
import com.kathline.library.listener.ZFileListener;
import com.kathline.library.util.ZFileHelp;
import com.kathline.library.util.ZFileOpenUtil;
import com.kathline.library.util.ZFileUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import team.JZY.DocManager.data.DocInfoRepository;
import team.JZY.DocManager.databinding.ActivityMainBinding;
import team.JZY.DocManager.databinding.UploadPopupwindowBinding;
import team.JZY.DocManager.model.DocInfo;
import team.JZY.DocManager.model.User;
import team.JZY.DocManager.ui.UserViewModel;
import team.JZY.DocManager.data.CosLoader;
import team.JZY.DocManager.util.Converter;
import team.JZY.DocManager.util.TextClassification;

public class MainActivity extends AppCompatActivity {


    private static final String LOGGED_IN_USER_NAME_KEY = "loggedInUserNameKey";
    private static final  int FILE_PICK_REQUEST_CODE = 4399;
    public static final String DOC = "application/msword";
    public static final String DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String PPT = "application/vnd.ms-powerpoint";
    public static final String PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    public static final String PDF = "application/pdf";
    private ActivityMainBinding binding;
    private UserViewModel userViewModel;
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
        Log.d("salkdjasljd",new File(getCacheDir(),"sjsj").toString());
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
    public static void start(Context context,String loggedInUserName) {
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra(LOGGED_IN_USER_NAME_KEY,loggedInUserName);
        context.startActivity(intent);
    }

    private void filePick() {
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
                int i=0;
                for(ZFileBean file:fileList) {
//                    try {
//                        docsInfo.add(Converter.getInfo(file));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    //Log.d("HHHHHHH", Uri.fromFile(new File(file.getFilePath())).toString());
                    CosLoader cosLoader = new CosLoader(this);
                    cosLoader.upload(this,Uri.fromFile(new File(file.getFilePath())),i++);
                }
//                DocInfoRepository.getInstance(getApplication())
//                        .setInsertListener(docsId ->{
//                            for(long a:docsId) {
//                                Log.d("PPPP",a+"");}
//                        }).insert(docsInfo);
        }).start();
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data.getData() != null) {
//            Uri uri = data.getData();
//            Snackbar.make(binding.getRoot(),"sdajk",Snackbar.LENGTH_SHORT);
////            CosLoader cosLoader=new CosLoader(this);
////            cosLoader.upload(this,uri);
//        }
//    }

}