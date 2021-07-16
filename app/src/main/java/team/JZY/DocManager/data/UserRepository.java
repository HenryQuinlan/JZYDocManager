package team.JZY.DocManager.data;

import android.content.Context;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import team.JZY.DocManager.interfaces.UserIdentification;
import team.JZY.DocManager.model.User;

public class UserRepository implements UserIdentification {
    private UserDao userDao;
    private static UserRepository INSTANCE;
    private stringListener mListener;


    private UserRepository(){

    };
    public static UserRepository getInstance(Context context){
        if(INSTANCE == null) {
            INSTANCE = new UserRepository();
            DocManagerDataBase docManagerDataBase = DocManagerDataBase.getInstance(context);
            INSTANCE.userDao = docManagerDataBase.getUserDao();
        }
        return INSTANCE;
    }

    public UserRepository setStringListener(stringListener listener){
        mListener=listener;
        return this;
    }
    @Override
    public void login(String name, String password) {
        new Thread(()->{
            if(name.isEmpty()||password.isEmpty()){
                mListener.receive("用户名或密码为空");
            }else{
                if(userDao.request(name).isEmpty()) {
                    mListener.receive("用户名不存在");
                }else if(userDao.request(name, password).isEmpty()) {
                    mListener.receive("用户名或密码错误");
                }else {
                    mListener.receive("登录成功");
                }
            }

        }).start();

    }


    public void register(String name, String password,String c_password) {

        new Thread(()->{
            String tips = null;
            if(name.isEmpty()||password.isEmpty()){
                tips="用户名或密码不能为空";
            }else if(!(password.equals(c_password))){
                tips="输入密码不一致";
            }else if(name.length()<6||password.length()<6){
                tips="用户名和密码不能小于六位";
            }else if(userDao.request(name).size()>0) {
                tips="用户名已被注册";
            }else {
                String illegal = " ~!@#$%^&*()_+|`-=\\{}[]:\";'<>/";
                for(int i=0;i<name.length();i++) {
                    if(illegal.indexOf(name.charAt(i)) >= 0) {
                        mListener.receive("用户名含非法字符");
                        mListener = null;
                        return;
                    }
                }
                User user=new User(name);
                user.setPassword(password);
                try {
                    userDao.insert(user);
                    tips = "注册成功";
                } catch (Exception e) {
                    e.printStackTrace();
                    mListener.receive("出错！");
                    return;
                }
            }
            mListener.receive(tips);
            mListener=null;
        }).start();
    }

    public interface stringListener{
        public void receive(String str);

    }
}
