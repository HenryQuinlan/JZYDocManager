package team.JZY.DocManager;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import team.JZY.DocManager.data.CosLoader;
import team.JZY.DocManager.model.DocInfo;
import team.JZY.DocManager.model.User;
import team.JZY.DocManager.util.ConvertUtil;
import team.JZY.DocManager.util.FileOpenUtil;

public class DocManagerApplication extends Application {
    private String loggedInUserName;
    public final String getLoggedInUserName() {
        return loggedInUserName;
    }
    public void setLoggedInUserName(String userName) {
        loggedInUserName = userName;
    }
    public String getSavePathDir() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return new File(getExternalFilesDir(null), loggedInUserName).toString();
        }
        return new File(getFilesDir(), loggedInUserName).toString();
    }

    public String getTempPathDir() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return new File(getExternalCacheDir(),loggedInUserName).toString();
        }
        return new File(getCacheDir(), loggedInUserName).toString();
    }
    public static class Activity extends AppCompatActivity {
        private DocManagerApplication mApplication;

        @Override
        protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mApplication = ((DocManagerApplication)getApplication());
        }
        public static void start(Context context) {
            Intent intent = new Intent(context,MainActivity.class);
            context.startActivity(intent);
        }
        public final String getLoggedInUserName() {
            return mApplication.loggedInUserName;
        }
        public void setLoggedInUserName(String userName) {
            mApplication.loggedInUserName = userName;
        }
        public String getSavePathDir() {
            return mApplication.getSavePathDir();
        }

        public String getTempPathDir() {
            return mApplication.getSavePathDir();
        }
        

    }

    public static class Fragment extends androidx.fragment.app.Fragment {
        private DocManagerApplication mApplication;
        private Activity mActivity;

        @Override
        public void onAttach(@NonNull @NotNull Context context) {
            super.onAttach(context);
            mActivity = (Activity)getActivity();
            mApplication = mActivity.mApplication;
        }

        public final String getLoggedInUserName() {
            return mApplication.loggedInUserName;
        }
        public void setLoggedInUserName(String userName) {
            mApplication.loggedInUserName = userName;
        }

        public final Activity getMActivity() {
            return mActivity;
        }
        public final Application getMApplication() {
            return mApplication;
        }
    }
}
