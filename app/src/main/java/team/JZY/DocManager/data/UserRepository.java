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
            if(name.isEmpty()&&password.isEmpty()){
                mListener.receive("未输入用户名或密码");
            }else{
                List<User> users=userDao.getAllUsers();
                for(int i=0;i<users.size();i++){
                    if(users.get(i).getName()==name){
                        if(users.get(i).getPassword()==password){
                            mListener.receive("登录成功");
                        }else mListener.receive("密码错误");
                    }
                }
                mListener.receive("用户名错误");
            }

        }).start();

    }


    public void register(String name, String password,String c_password) {

        if(password!=c_password)return;
        new Thread(()->{
            String tips;
            if(name.isEmpty()||password.isEmpty()||c_password.isEmpty()){
                tips="未输入用户名或密码";
                mListener.receive(tips);
                mListener=null;
            }else if(!(password.equals(c_password))){
                tips="确认密码错误";
                mListener.receive(tips);
                mListener=null;
            }else{
                List<User> users=userDao.getAllUsers();
                for(int i=0;i<users.size();i++){
                    if(users.get(i).getName()==name){
                        tips="用户名重复";
                        mListener.receive(tips);
                        mListener=null;
                    }
                }
                User user=new User(name);
                user.setPassword(password);
                userDao.insert(user);
                tips="注册成功";
                mListener.receive(tips);
            }
//            User user=new User(name);
//            user.setPassword(password);
//            try {
//                userDao.insert(user);
//                booleanListener.receive(true);
//            } catch (Exception e) {
//                e.printStackTrace();
//                booleanListener.receive(false);
//            }
//            booleanListener=null;
        }).start();
    }

    public interface stringListener{
        public void receive(String str);

    }
}
