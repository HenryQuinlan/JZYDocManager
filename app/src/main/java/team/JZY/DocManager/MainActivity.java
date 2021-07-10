package team.JZY.DocManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;

import java.io.File;
import java.io.InputStream;

import team.JZY.DocManager.data.DocInfoViewModol;
import team.JZY.DocManager.databinding.ActivityMainBinding;
import team.JZY.DocManager.model.DocInfo;
import team.JZY.DocManager.model.User;
import team.JZY.DocManager.ui.UserViewModel;
import team.JZY.DocManager.data.CosLoader;

public class MainActivity extends AppCompatActivity {
    DocInfoViewModol docInfoViewModol;
    private final static int FILE_REQUEST_CODE = 400;
    private File file;

    private static final String LOGGED_IN_USER_NAME_KEY = "loggedInUserNameKey";
    private ActivityMainBinding binding;
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        docInfoViewModol= new ViewModelProvider(this).get(DocInfoViewModol.class);


        Intent intent = getIntent();
        String userName = intent.getStringExtra(LOGGED_IN_USER_NAME_KEY);
        User user = new User(userName);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.setLiveUser(user);


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
    private boolean onMenuItemUploadClicked()
    {
        //上传
        uploadAction();
        return false;
    }
    private void uploadAction(){
        Log.d( "MainActivity","here: ");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);  //  意图：文件浏览器
        intent.setType("*/*");//无类型限制
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);  //  关键！多选参数为true
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, FILE_REQUEST_CODE);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("MainActivity", "owhat: ");
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity", "onActivityResult: ");
        if (data.getData() != null) {
            Uri uri = data.getData();
            String[] arr = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(uri, arr, null, null, null);
            int img_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String img_path = cursor.getString(img_index);
            file = new File(img_path);
            long size=file.length();
            String defaultName="";
            int theClassification;
            //
            if(defaultName==""){

            }


            DocInfo docInfo =new DocInfo(defaultName,1,0,2,10);
            Long[] id=docInfoViewModol.insertDocInfo(docInfo);
            Long theId=id[0];
            CosLoader cosLoader=new CosLoader(this);
            cosLoader.upload(this,uri,theId);
        }
    }
    public static void start(Context context,String loggedInUserName) {
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra(LOGGED_IN_USER_NAME_KEY,loggedInUserName);
        context.startActivity(intent);
    }
}