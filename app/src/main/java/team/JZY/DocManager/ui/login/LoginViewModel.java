package team.JZY.DocManager.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    // TODO: Implement the ViewModel
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
        if(name.equals("1") &&password.equals("1")) {
            loggedInUserName.setValue(name);
            return true;
        }
        return false;
    }
}