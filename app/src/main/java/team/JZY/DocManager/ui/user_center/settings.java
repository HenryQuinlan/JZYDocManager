package team.JZY.DocManager.ui.user_center;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import team.JZY.DocManager.LoginActivity;
import team.JZY.DocManager.R;
import team.JZY.DocManager.databinding.ActivitySettingsBinding;
import team.JZY.DocManager.ui.login.LoginViewModel;

public class settings extends AppCompatActivity {

    private ActivitySettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonLogOut.setOnClickListener(v->{
            SharedPreferences sharedPref = getSharedPreferences(getString(R.string.jzy_docManager_shared_preference_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(LoginViewModel.SAVE_LOGGED_IN_STATE_KEY, null);
            editor.apply();
            Intent intent = new Intent(settings.this,LoginActivity.class);
            startActivity(intent);
            finishAffinity();
        });


    }
}