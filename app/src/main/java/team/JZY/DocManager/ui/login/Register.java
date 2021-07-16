package team.JZY.DocManager.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import team.JZY.DocManager.R;
import team.JZY.DocManager.data.UserRepository;
import team.JZY.DocManager.model.User;

public class Register extends AppCompatActivity {

    UserRepository userRepository= UserRepository.getInstance(getApplicationContext());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText editName=(EditText)findViewById(R.id.text_name);
        String name=editName.getText().toString();
        EditText editPwd=(EditText)findViewById(R.id.text_password);
        String pwd=editPwd.getText().toString();
        EditText editC_pwd=(EditText)findViewById(R.id.text_confirm_password);
        String c_pwd=editC_pwd.getText().toString();
        Button buttonRegister=(Button)findViewById(R.id.button_register1);
        String tips = new String();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userRepository.setStringListener(tips -> runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Snackbar.make(v, tips, Snackbar.LENGTH_SHORT).show();
                    }
                })).register(name, pwd, c_pwd);
                if(tips.equals("注册成功")){
                    Intent intent = new Intent(Register.this, LoginFragment.class);
                    startActivity(intent);
                }
            }
        });
    }
}