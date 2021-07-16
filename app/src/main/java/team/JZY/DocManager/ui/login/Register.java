package team.JZY.DocManager.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import team.JZY.DocManager.R;
import team.JZY.DocManager.data.UserRepository;
import team.JZY.DocManager.model.User;

public class Register extends AppCompatActivity {

    UserRepository userRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userRepository= UserRepository.getInstance(getApplicationContext());
        setContentView(R.layout.activity_register);
        Button buttonRegister=(Button)findViewById(R.id.button_register1);
        String tips = new String();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName=(EditText)findViewById(R.id.text_name);
                String name=editName.getText().toString();
                EditText editPwd=(EditText)findViewById(R.id.text_password);
                String pwd=editPwd.getText().toString();
                EditText editC_pwd=(EditText)findViewById(R.id.text_confirm_password);
                String c_pwd=editC_pwd.getText().toString();
                userRepository.setStringListener(tips -> runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(Register.this,tips,Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                        if(tips.equals("注册成功")){
                            Register.this.finish();
                        }
                    }
                })).register(name, pwd, c_pwd);

            }
        });
    }
}