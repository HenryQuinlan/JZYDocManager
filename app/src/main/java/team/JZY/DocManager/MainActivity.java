package team.JZY.DocManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import team.JZY.DocManager.databinding.ActivityMainBinding;
import team.JZY.DocManager.model.User;
import team.JZY.DocManager.ui.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String LOGGED_IN_USER_NAME_KEY = "loggedInUserNameKey";
    private ActivityMainBinding binding;
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
    public boolean onMenuItemUploadClicked()
    {
        //上传
        return false;
    }

    public static void start(Context context,String loggedInUserName) {
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra(LOGGED_IN_USER_NAME_KEY,loggedInUserName);
        context.startActivity(intent);
    }
}