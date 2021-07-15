package team.JZY.DocManager.data;

import android.content.Context;

import team.JZY.DocManager.interfaces.UserIdentification;
import team.JZY.DocManager.model.User;

public class UserRepository implements UserIdentification {
    private UserDao userDao;
    private static UserRepository INSTANCE;
    private onUserReceivedListener mListener;
    private booleanListener b_login;


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

    public UserRepository setonRecordReceivedListener(onUserReceivedListener listener) {
        mListener = listener;
        return this;
    }
    public UserRepository setBooleanListener(booleanListener listener){
        b_login=listener;
        return this;
    }
    @Override
    public booleanListener login(String name, String password) {
        new Thread(()->{
            User user=userDao.request(name, password);
            if(mListener!=null){
                if(user!=null){
                    b_login.receive(true);
                }
            }else{
                b_login.receive(false);
            }
            mListener=null;
        }).start();
        return b_login;
    }

    @Override
    public void register(String name, String password) {
        new Thread(()->{User user=new User(name);
        for
            user.setPassword(password);
            if(mListener==null)return;
            userDao.insert(user);
            mListener=null;
        }).start();

    }

    public interface onUserReceivedListener {
        public void receive();
    }
    public interface booleanListener{
        public void receive(boolean b);


    }
}
