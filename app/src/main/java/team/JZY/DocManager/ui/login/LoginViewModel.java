package team.JZY.DocManager.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import team.JZY.DocManager.data.UserRepository;
import team.JZY.DocManager.model.User;

public class LoginViewModel extends ViewModel {
    private UserRepository userRepository=UserRepository.getInstance();

    public static final String SAVE_LOGGED_IN_STATE_KEY = "SavedLoggedInStateKey";
    private MutableLiveData<String> loggedInUserName;


    public LiveData<String> getLoggedInUserName() {
        if(loggedInUserName == null) {
            loggedInUserName = new MutableLiveData<String>(null);
        }
        return loggedInUserName;
    }
    public void setLoggedInUserName(String name) {
        if(loggedInUserName == null) {
            loggedInUserName = new MutableLiveData<String>(name);
        }
        else {
            loggedInUserName.setValue(name);
        }
    }

    public boolean login(String name,String password) {
//        String tips=new String();
//        userRepository.setStringListener(tips->RunO)
    }

}